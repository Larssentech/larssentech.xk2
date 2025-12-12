/*
 * Copyright 2014-2024 Larssentech Developers
 * 
 * This file is part of XKomm.
 * 
 * XKomm is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * XKomm is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * XKomm. If not, see <http://www.gnu.org/licenses/>.
 */
package org.larssentech.xkomm2.api.impl.stream;

import java.io.File;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.basiclib.io.file.StreamReader;
import org.larssentech.lib.basiclib.toolkit.ByteArrayToolkit;
import org.larssentech.xkomm.core.hub.net.NetMonitor;
import org.larssentech.xkomm.core.hub.req.Hub;
import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm.core.obj.constants.Constants4Stream;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm2.api.impl.crypto.CipherApiImpl;
import org.larssentech.xkomm2.api.impl.crypto.CtkApiImpl;
import org.larssentech.xkomm2.api.impl.message.MessageApiImpl;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.core.obj.model.StreamHeaderModel;
import org.larssentech.xkomm2.core.obj.model.StreamReportModel;
import org.larssentech.xkomm2.core.obj.objects.StreamSpec;
import org.larssentech.xkomm2.core.obj.objects.StreamerSpecContainer;

public class StreamUpApiImpl implements Constants4Stream {

	private static Stream2Server stream2Server;
	private static final StreamerSpecContainer upStreamSpecs = new StreamerSpecContainer();

	public static int canStream(String contactString, String fileName) {

		// Good file?
		boolean fileNameOK = fileName.length() > 0;
		boolean fileExists = new File(fileName).exists();
		if (!fileNameOK || !fileExists) return BAD_FILE;

		// Right conditions?
		boolean fileSizeOK = fileSizeOK(new File(fileName));
		boolean userOnline = Hub.hubGetContact4(contactString).isOnline();

		if (!userOnline && !fileSizeOK) return OFFLINE_BIG_FILE;
		if (!userOnline && fileSizeOK) return OFFLINE_SMALL_FILE;
		if (userOnline) return GOOD_FILE;
		return NO_IDEA;
	}

	private static boolean fileSizeOK(File file) {

		return (file.length() <= MAX_OFFLINE_FILE_SIZE);
	}

	private static boolean doCancelUpload(String contactString) {

		if (null != StreamUpApiImpl.upStreamSpecs.get(contactString)) {

			StreamUpApiImpl.upStreamSpecs.get(contactString).setCancelled(true);
			return true;
		}
		return false;
	}

	private static boolean replaceUpStreamSpec4(String contactString, StreamSpec thisStreamSpec) {

		StreamUpApiImpl.upStreamSpecs.remove(contactString);
		StreamUpApiImpl.upStreamSpecs.put(contactString, thisStreamSpec);
		return StreamUpApiImpl.upStreamSpecs.get(contactString) != null;
	}

	// MessageApiImpl calls this
	public static void acceptReceiverRejectionOfDownload(User contact) {

		StreamUpApiImpl.upStreamSpecs.get(contact.getLogin().getEmail()).setRejected(true);
	}

	// Xkomm2Api calls this
	public static void streamFromFileX(String contactString, PUK otherContactPuk, File file) {

		StreamUpApiImpl.stream2Server.streamFromFileX(contactString, otherContactPuk, file);

	}

	public static void init() {

		StreamUpApiImpl sx = new StreamUpApiImpl();
		stream2Server = sx.new Stream2Server();
	}

	private class Stream2Server implements Constants4Stream {

		private class Stream2ServerFunctions {

			private int arrayLength = 1024 * 4;
			private StreamReader reader;
			private long bytesDone = 0;
			private long fileLength = 0;

			private Stream2ServerFunctions(File file) {

				this.reader = new StreamReader(file);
				this.fileLength = file.length();

				// Set the desired packet HEADER_SIZE
				this.arrayLength = Constants4Stream.BLOCK_BYTES;
			}

			boolean done() {

				return this.bytesDone >= this.fileLength;
			}

			float progress() {

				return (100 * this.bytesDone / this.fileLength);
			}

			byte[] readNext(PUK contactPuk) {

				byte[] binaryBlock = new byte[this.arrayLength];
				byte[] base64Block = new byte[this.arrayLength];

				long usedLength = 0;

				// Read next binary block into byte array
				usedLength = this.reader.readBytes(binaryBlock);

				binaryBlock = CipherApiImpl.shrink(binaryBlock, usedLength, this.arrayLength);
				this.bytesDone = this.bytesDone + usedLength;

				try {

					byte[] encryptedBinaryBlock;

					encryptedBinaryBlock = CtkApiImpl.blowfishEncrypt(binaryBlock, usedLength, contactPuk);
					int encryptedLength = encryptedBinaryBlock.length;

					// Encode the block to Base 64, shrink first if needed
					base64Block = new Base64()
							.encode(CipherApiImpl.shrink(encryptedBinaryBlock, encryptedLength, this.arrayLength));
				} catch (EncoderException e) {

					e.printStackTrace();
				}

				return base64Block;
			}
		}

		private StreamReportModel streamReportModel = new StreamReportModel();

		/**
		 * This method uses the regular Exec4Send to stream blocks of the file passed as
		 * a parameter out to user passed in param to as a separate thread so that we
		 * can pause the process independently from any other thread. This pausing is
		 * performed here to provide a true serialisation of the stream to the server,
		 * without "clogging" the outbox with excessive data. Only when one packet has
		 * gone out correctly it makes sense to send the next one.
		 * 
		 * The process repeats as many times as blocks are. The blocks are sent as
		 * Base64 text. The process iterates within the method below, without having to
		 * be invoked again by any outside process.
		 * 
		 * This is different from the stream "receiving" method above or elsewhere. It
		 * is easy to read a file end to end and send chunks out but receiving chunks
		 * might not all be consecutive, messages may be interleaved and for this
		 * reason, something else will detect what the packets are and invoke the
		 * "receive" when needed.
		 * 
		 * This method also will not continue if the previous block has got stuck
		 * somewhere. But currently will hang there until cancelled
		 * 
		 */
		private void streamFromFileX(final String contactString, final PUK otherContactPuk, final File file) {

			/**
			 * Object to hold metadata about the stream and its related file
			 */
			final StreamSpec thisStreamSpec = new StreamSpec(contactString, file.getAbsoluteFile());

			final long totalBytes = file.length();
			StreamUpApiImpl.replaceUpStreamSpec4(contactString, thisStreamSpec);

			this.norifyRecipient(contactString, file);

			// Kubernetes!
			Thread k8t = new Thread() {

				@Override
				public void run() {

					// where do we attach the Secret Key, in the first block?
					final Stream2ServerFunctions s = new Stream2ServerFunctions(file);

					// ActivityCounter for blocks
					int blockNum = 0;

					// Add header and send it. This is block zero
					byte[] blockZero = StreamHeaderModel.createBlockHeader(file, blockNum);

					// Turn the zero block into a Message
					Message message = Hub.hubGenerateMessage(contactString, Message.DATA, blockZero);

					// Do not even try to send unless there is NETWORK
					Xkomm2Api.apiRequestNetworkSlot(); // Will wait

					// Remember the Uid for this block so we wait until sent
					NetMonitor.setLastDataId(message.getUid());

					// Put in outbox for sending
					String mGUID = MessageApiImpl.putInOutboxAndTrack(message);

					// Increase the block we are at now
					blockNum++;

					// For all blocks in the file to send, we iterate
					while (!s.done()) {

						// If we are cleared to continue to send blocks
						if (thisStreamSpec.isDone() || thisStreamSpec.isCancelled() || thisStreamSpec.isRejected())
							break;

						// While the previous block's Uid is still being sent,
						// wait
						while (NetMonitor.getLastDataId().equals(mGUID)) {

							try {

								Thread.sleep(Constants4API.STREAMX_2_SERVER);
							} catch (InterruptedException e) {

							}
						}

						// Now the queue for blocks is clear, get next block.
						// The block is returned already encrypted and in Base64
						byte[] b64 = s.readNext(otherContactPuk);

						// Create the header
						byte[] blockHeader = StreamHeaderModel.createBlockHeader(file, blockNum);

						// Add the header to the block
						byte[] blockWithHeader = ByteArrayToolkit.concatArrays(blockHeader, b64);

						// Turn the block into a Message
						message = Hub.hubGenerateMessage(contactString, Message.DATA, blockWithHeader);

						Xkomm2Api.apiRequestNetworkSlot(); // Will wait

						// Remember this block's Uid while in transit
						NetMonitor.setLastDataId(message.getUid());

						// Put in outbox for sending
						mGUID = MessageApiImpl.putInOutboxAndTrack(message);

						// Print progress
						Xkomm2Api.apiPl(Constants4Stream.TO_QUEUE + mGUID + Constants4Stream.SEND_PROGRESS_1
								+ (blockNum) + Constants4Stream.SEND_PROGRESS_2
								+ (1 + Math.round(totalBytes / Constants4Stream.BLOCK_BYTES))
								+ Constants4Stream.SEND_PROGRESS_3);

						// Update the queue for streaming logs
						Stream2Server.this.getUpStreamProgressLogManager().insertNextProgress2UploadLog4(contactString,
								Math.round(s.progress()) + "");

						// Update ?
						thisStreamSpec.setCompletedP100(Math.round(s.progress()));

						// Increase block number for next block in while loops
						blockNum++;
					}

					// rrako: when out of the loop
					s.reader.closeStream();

					if (thisStreamSpec.isCancelled())
						Stream2Server.this.getUpStreamProgressLogManager().insertNextProgress2UploadLog4(contactString,
								STREAMING_CANCELLED + blockNum + NEW_LINE);

					else
						this.notifyParties(blockNum);

				}

				private void notifyParties(int blockNum) {

					String body = Constants4Stream.DATA_SENT_1 + blockNum + Constants4Stream.DATA_SENT_2;

					Message message = Hub.hubGenerateMessage(contactString, Message.TEXT, body.getBytes());

					Xkomm2Api.apiSendMessage(message, true);
				}
			};

			k8t.start();
		}

		private void norifyRecipient(String contactString, File file) {

			// Send a warning message to recipient
			String msg = Xkomm2Api.apiGetMe().getLogin().getEmail() + Constants4Stream.RECEIVING_1 + file.getName()
					+ Constants4Stream.RECEIVING_2 + Math.round(file.length() / 1000) / 1000d
					+ Constants4Stream.RECEIVING_3;

			Message warning = Xkomm2Api.apiGenerateMessage(contactString, Message.TEXT, msg.getBytes());
			warning.setGood(true);

			Xkomm2Api.apiSendMessage(warning, true);

		}

		private StreamReportModel getUpStreamProgressLogManager() {

			return this.streamReportModel;
		}
	}

	// Xkomm2Api calls this
	public static String getNextUploadProgressFromLog4(String contactString) {

		return stream2Server.getUpStreamProgressLogManager().getNextUploadProgressFromLog4(contactString);
	}

	// Xkomm2Api calls this
	public static boolean cancelUpload(String contactString) {

		boolean b = StreamUpApiImpl.doCancelUpload(contactString);

		if (b) {

			Message message = Hub.hubGenerateMessage(contactString, Message.SYS,
					NetworkConstants.SS_CANCEL_UPLOAD.getBytes());

			Xkomm2Api.apiSendMessage(message, Message.SAVE_HISTORY_NO);

			message = Hub.hubGenerateMessage(contactString, Message.TEXT, Constants4Stream.SENDER_CANCELLED.getBytes());

			Xkomm2Api.apiSendMessage(message, Message.SAVE_HISTORY_NO);
		}

		return b;
	}

	// Xkomm2Api calls this
	public static StreamSpec getUpStreamSpec4(String to) {

		StreamSpec spec = StreamUpApiImpl.upStreamSpecs.get(to);

		if (null == spec) {

			spec = new StreamSpec(to, null);
			spec.setCompletedP100(Constants4Stream.HUNDRED);
			spec.setDone(true);
		}
		return spec;
	}
}
