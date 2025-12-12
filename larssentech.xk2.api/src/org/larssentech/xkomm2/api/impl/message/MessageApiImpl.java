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
package org.larssentech.xkomm2.api.impl.message;

import java.util.Date;

import org.larssentech.lib.basiclib.toolkit.StringManipulationToolkit;
import org.larssentech.xkomm.api.impl.Impl4History;
import org.larssentech.xkomm.core.hub.req.Hub;
import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm2.api.Sys.XAPI.ApiG;
import org.larssentech.xkomm2.api.Sys.constants.SysConstants;
import org.larssentech.xkomm2.api.impl.crypto.CtkApiImpl;
import org.larssentech.xkomm2.api.impl.stream.StreamDownApiImpl;
import org.larssentech.xkomm2.api.impl.stream.StreamUpApiImpl;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class MessageApiImpl {

	public static Message spotSys(Message message) {

		if (message.getBody().startsWith(SysConstants.SYS_COMMAND) || message.getBody().startsWith(SysConstants.XTRA_COMMAND)) {

			message.setType(Message.SYS);
		}

		return message;
	}

	private static boolean isAcceptReceiverRejectedDownload(Message m) {

		return m.getBody().equals(NetworkConstants.SS_REJECT_DOWNLOAD);
	}

	private static boolean isCancelUpload(Message m) {

		return m.getBody().equals(NetworkConstants.SS_CANCEL_UPLOAD);
	}

	private static boolean isSys(Message m) {

		return m.getBody().startsWith(SysConstants.SYS_COMMAND) || m.getBody().startsWith(SysConstants.XTRA_COMMAND);
	}

	private static boolean isData(Message m) {

		return m.getType() == Message.DATA;
	}

	public static boolean isStream(Message message) {

		return message.getBody().indexOf(Constants4API.STREAM_HEADER) != -1;
	}

	static void processSys(Message m) {

		if (m.isDelivered()) {

			if (MessageApiImpl.isAcceptReceiverRejectedDownload(m)) StreamUpApiImpl.acceptReceiverRejectionOfDownload(m.getFrom());

			if (MessageApiImpl.isCancelUpload(m)) StreamDownApiImpl.acceptSenderCancellingUpload(m.getFrom());

			else if (MessageApiImpl.isSys(m)) {

				MessageApiImpl.sendBunchOfMessages(createArray(ApiG.processMessage(m), m.getFrom().getLogin().getEmail()), Message.SAVE_HISTORY_NO);
			}
		}
	}

	public static Message getNextSysMessage(String from) {

		User user = Hub.hubGetContact4(from);

		Message message = Hub.hubGetNextSysFromInbox(user);

		CtkApiImpl.decodeMessage(message);

		processSys(message);

		return message;
	}

	public static boolean sendMessage(Message message, boolean history) {

		MessageApiImpl.spotSys(message);

		if (history) Impl4History.saveSent2History(message);

		CtkApiImpl.encodeMessage(message.getTo(), message);

		return putInOutboxAndTrack(message).length() > 0;
	}

	public static Message getNextTextMessage(String from, boolean save2History) {

		User user = Hub.hubGetContact4(from);

		Message message = Hub.hubGetNextTextFromInbox(user);

		CtkApiImpl.decodeMessage(message);

		if (save2History) Impl4History.saveReceived2History(message);

		return message;
	}

	public static String putInOutboxAndTrack(Message m) {

		// If data, we want to track what "we" sent as UID
		if (MessageApiImpl.isData(m)) {
			Hub.hubPut1InOutbox(m);
			return m.getUid();
		}
		else {
			Hub.hubPut1InOutbox(m);
			return m.getSid();
		}
	}

	public static void sendBunchOfMessages(Message[] bunch, boolean history) {

		for (int i = 0; i < bunch.length; i++) sendMessage(bunch[i], history);
	}

	public static Message getNextMessage(String from, boolean history) {

		Message m = Hub.hubGetNextTextFromInbox(Hub.hubGetContact4(from));

		if (m.isGood()) {

			CtkApiImpl.decodeMessage(m);

			if (m.getType() == Message.SYS) processSys(m);
			if (m.getType() == Message.TEXT) if (history) Impl4History.saveReceived2History(m);

		}

		return m;
	}

	static Message[] createArray(String longMessage, String to) {

		String[] chopped = StringManipulationToolkit.chunkString(longMessage, 200);
		Message[] bunch = new Message[chopped.length];

		for (int i = 0; i < chopped.length; i++) bunch[i] = Hub.hubGenerateMessage(to, Message.TEXT, chopped[i].getBytes());

		return bunch;
	}

	public static boolean sendTyping(String contactString) {

		if (Hub.hubHaveNetwork()) {

			Message message = Hub.hubGenerateMessage(contactString, Message.SYS, Constants4API.TYPING.getBytes());

			message.setSentDate(new Date());

			Xkomm2Api.apiSendMessage(message, Message.SAVE_HISTORY_NO);

			return true;
		}

		return false;
	}
}