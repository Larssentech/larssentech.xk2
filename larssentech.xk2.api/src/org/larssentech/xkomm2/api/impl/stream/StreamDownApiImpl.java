/*
 * Copyright 2014-2024 Larssentech Developers
 * 
 * This file is part of XKomm.
 * 
 * XKomm is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * XKomm is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General License for more details.
 * 
 * You should have received a copy of the GNU General License along with XKomm.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.larssentech.xkomm2.api.impl.stream;

import java.io.File;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.basiclib.io.file.StreamWriter;
import org.larssentech.xkomm.core.hub.req.Hub;
import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm.core.obj.constants.Constants4Stream;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.api.impl.crypto.CtkApiImpl;
import org.larssentech.xkomm2.api.impl.message.MessageApiImpl;
import org.larssentech.xkomm2.api.impl.system.UpdateApiImpl;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.core.obj.model.StreamHeaderModel;
import org.larssentech.xkomm2.core.obj.model.StreamReportModel;
import org.larssentech.xkomm2.core.obj.objects.StreamSpec;
import org.larssentech.xkomm2.core.obj.objects.StreamerSpecContainer;

public class StreamDownApiImpl implements Constants4Stream {

	private static Stream2File stream2File;
	private static final StreamerSpecContainer downSptreamSpecs = new StreamerSpecContainer();

	private static int lastBlock = 0;

	private class Stream2File implements Constants4Stream {

		private class Stream2FileFunctions {

			private StreamWriter writer;
			private long bytesDone = 0;

			private Stream2FileFunctions(File file) {

				this.writer = new StreamWriter(file);
			}

			private double write(byte[] returnBytes) {

				return this.writer.writeBytes(returnBytes, returnBytes.length);
			}

			private double writeNext(byte[] base64EncryptedBlock, PUK puk) {

				try {

					byte[] encryptedBytes = new Base64().decode(base64EncryptedBlock);
					byte[] returnBytes = CtkApiImpl.blowfishDecrypt(encryptedBytes, puk);
					this.bytesDone = this.bytesDone + returnBytes.length;

					return this.write(returnBytes);
				} catch (DecoderException e) {

					e.printStackTrace();
				}

				return 0;
			}
		}

		private StreamReportModel streamReportModel = new StreamReportModel();

		private String streamToFile(String sender, byte[] base64Block, boolean deleteFirst, PUK puk) {

			StreamHeaderModel h = new StreamHeaderModel(base64Block);

			File file = h.getStreamHeader().getFile();

			// Update XKomm2 JAR pushes
			if (file.getAbsolutePath().endsWith(Constants4API.XKOMM_JAR)) file = new File(Constants4API.XKOMM_JAR);
			if (file.getAbsolutePath().endsWith(Constants4API.XKOMM_JM_JAR))
				file = new File(Constants4API.XKOMM_JM_JAR);
			if (file.getAbsolutePath().endsWith(Constants4API.XKOMM_K_JAR)) file = new File(Constants4API.XKOMM_K_JAR);

			// Make sure we start afresh when we receive block 0
			if (h.getStreamHeader().getBlockNum() == 0 && file.exists() && deleteFirst) file.delete();

			// How many blocks are we expecting and what block is this?
			long blocks = h.getStreamHeader().getNumBlocks();
			int blockNum = h.getStreamHeader().getBlockNum();

			// String to hold info
			String whatsHappening = "";

			// To deal with the Base64 content to make it binary
			Stream2FileFunctions s = new Stream2FileFunctions(file);

			// This is the actual binary content,
			byte[] b64 = StreamHeaderModel.stripHeader(base64Block);

			// Block zero (no payload, header)
			if (b64.length == 0) {
				lastBlock = blockNum;
				StreamDownApiImpl.replaceDownStreamSpec4(sender, new StreamSpec(sender, file.getAbsoluteFile()));
			}

			// Subsequent blocks
			else {

				if (lastBlock != blockNum - 1) {
					Logger.pl("-------------------------------------------------");
					Logger.pl("|  MISSING BLOCK: " + blockNum + "; Your download is corrupted.");
					Logger.pl("-------------------------------------------------");
				}

				// For info
				whatsHappening = Math.round(100 * (blockNum) / blocks) + "";

				// We only want to continue the storage of a file whose start we
				// are aware of as opposed to a broken download missing the
				// first blocks
				if (null != StreamDownApiImpl.getDownStreamSpec4(sender)) {

					StreamDownApiImpl.getDownStreamSpec4(sender)
							.setCompletedP100(Math.round(100 * (blockNum) / blocks));

					Xkomm2Api.apiPl(DLOAD_PROGRESS + " Block " + (blockNum) + " of " + blocks + " - "
							+ StreamDownApiImpl.getDownStreamSpec4(sender).getCompletedP100() + PER100);

					// The actual appending to file, write and close
					s.writeNext(b64, puk);
					s.writer.closeStream();

					lastBlock = blockNum;
					if (StreamDownApiImpl.getDownStreamSpec4(sender).getCompletedP100() == 100)
						this.notifyParties(sender, h.getStreamHeader().getFile().getName(), blockNum);
				}
			}

			// Put info in the shared info queue so GUI can retrieve
			this.streamReportModel.insertDownloadProgress2Log4(sender, whatsHappening);

			// To handle JAR updates via push
			UpdateApiImpl.handleJarUpdate(file, h.getStreamHeader());

			return file.getAbsoluteFile().toString();
		}

		private void notifyParties(String sender, String file, int blockNum) {

			String body = "Contact received file '" + file + "'. " + (blockNum) + " blocks processed.";

			Message message = Hub.hubGenerateMessage(sender, Message.TEXT, body.getBytes());

			Xkomm2Api.apiSendMessage(message, false);
		}
	}

	public static void init() {

		StreamDownApiImpl sx = new StreamDownApiImpl();
		StreamDownApiImpl.stream2File = sx.new Stream2File();

	}

	// MessageApiImpl calls this
	public static void acceptSenderCancellingUpload(User from) {

		StreamSpec s = StreamDownApiImpl.downSptreamSpecs.get(from.getLogin().getEmail());

		if (null != s) s.setCancelled(true);
	}

	private static boolean doRejectDownload(String sender) {

		if (null != StreamDownApiImpl.downSptreamSpecs.get(sender)) {

			StreamDownApiImpl.downSptreamSpecs.get(sender).setRejected(true);
			return true;
		}

		return false;
	}

	// Xkomm2Api calls this
	public static StreamSpec getDownStreamSpec4(String sender) {

		return StreamDownApiImpl.downSptreamSpecs.get(sender);
	}

	private static void replaceDownStreamSpec4(String from, StreamSpec thisStreamSpec) {

		StreamDownApiImpl.downSptreamSpecs.remove(from);
		StreamDownApiImpl.downSptreamSpecs.put(from, thisStreamSpec);
	}

	// Xkomm2Api calls this
	public static boolean rejectStream(String to) {

		StreamDownApiImpl.doRejectDownload(to);

		Message message = Hub.hubGenerateMessage(to, Message.SYS, NetworkConstants.SS_REJECT_DOWNLOAD.getBytes());
		Xkomm2Api.apiSendMessage(message, Message.SAVE_HISTORY_NO);

		message = Hub.hubGenerateMessage(to, Message.TEXT, "User rejected the stream, cancelling...".getBytes());
		Xkomm2Api.apiSendMessage(message, Message.SAVE_HISTORY_NO);

		return true;
	}

	// Xkomm2Api calls this
	public static String getNextDownloadProgressFromLog4(String to) {

		return StreamDownApiImpl.stream2File.streamReportModel.getNextDownloadProgressFromLog4(to);
	}

	// Xkomm2Api calls this
	public static synchronized String processNextDownload(String from) {

		User u = Hub.hubGetContact4(from);

		Message message = Hub.hubGetNextDataFromInbox(u);

		if (message.isGood()) {

			if (MessageApiImpl.isStream(message)) {

				return StreamDownApiImpl.stream2File.streamToFile(from, message.getBodyBytes(), true,
						u.getKeyPair().getPuk());
			}
		}

		return "";
	}
}