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
package org.larssentech.xkomm2.ui.shared.functions;

import java.io.File;

import javax.swing.JTextField;

import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm.core.obj.constants.HistoryConstants;
import org.larssentech.xkomm.core.obj.model.XmlMessageModel;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.core.obj.objects.StreamSpec;
import org.larssentech.xkomm2.ui.shared.chat.ChatArea;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.util.UisUtil2;

public class Functions4Messaging extends Functions4Common implements HistoryConstants, Constants4API {

	public static XmlMessageModel[] requestLoadHistory(String contact, int lines) {

		return Xkomm2Api.apiGetHistory(contact, lines);

	}

	/* Send */
	public static Message requestSendTextMessage(String email, String msg) {

		// if (Xkomm2Api.apiHaveNetwork())
		if (UisUtil2.isMessage(msg)) {

			// The message to send, which will be updated downstream (encrypted)
			// so we need...
			Message message = Xkomm2Api.apiGenerateMessage(email, Message.TEXT, msg.getBytes());

			// So we need a backup one that is unencrypted and can display on
			// screen
			Message messagePlain = Xkomm2Api.apiGenerateMessage(email, Message.TEXT, msg.getBytes());

			if (Xkomm2Api.apiSendMessage(message, Message.SAVE_HISTORY_YES)) return messagePlain;
		}

		return new Message();
	}

	public static String requestSendStream(String to, String fileName) {

		if (UisUtil2.canSendStream(to, fileName)) {

			Xkomm2Api.apiSendFile(to, fileName);

			return

			"Starting encrypted stream transmission for file '" + new File(fileName).getName() + "'. " + "Size: "

					+ Math.round(new File(fileName).length() / 1000) / 1000d + " MB";
		}
		else return "";
	}

	public static Message requestCancelSendStream(String to) {

		// If we receiving a file, and reject worked, inform user (us)
		if (null != Xkomm2Api.apiGetUpStreamSpec4(to)) {

			boolean b = Xkomm2Api.apiCancelUpload(to);

			if (b) return UisUtil2.requestAnonMessage(Constants4API.NEW_LINE + Constants4Uis.SEND_CANCEL + Constants4API.NEW_LINE, Message.OUT);
		}
		return new Message();
	}

	public static Message requestRejectReceiveStream(String contactString) {

		// If we receiving a file, and reject worked, inform user (us)
		if (null != Xkomm2Api.apiGetDownStreamSpec4(contactString))

			if (Xkomm2Api.apiRejectStream(contactString))

				return UisUtil2.requestAnonMessage(Constants4API.NEW_LINE + Constants4Uis.REJECT_STREAM + Constants4API.NEW_LINE, Message.OUT);

		return new Message();
	}

	/* Receive */
	public static Message requestReceiveTextMessage(String to) {

		return Xkomm2Api.apiGetNextTextMessage(to, true);
	}

	public static Message requestdReceiveSys(String to) {

		return Xkomm2Api.apiGetNextSysMessage(to);
	}

	public static void requestReceiveData(String to) {

		Xkomm2Api.apiGetNextDataMessage(to);
	}

	public static void requestDeleteChat(final ChatArea chatArea, final JTextField msgf, String contactString) {

		chatArea.setText(Constants4Uis.EMPTY_STRING);
		msgf.setText(Constants4Uis.EMPTY_STRING);
		Xkomm2Api.apiDeleteHistory(contactString);
	}

	public static void requestDeleteChat(String contactString) {

		Xkomm2Api.apiDeleteHistory(contactString);
	}

	public static Message requestSendMessage(String to, String messageBody) {

		return Functions4Messaging.requestSendTextMessage(to, messageBody.replaceAll("\n", ""));
	}

	public static String requestNextUploadLog4(String to) {

		return Xkomm2Api.apiGetNextUploadLog(to);
	}

	public static String requestNextDownloadLog4(String to) {

		return Xkomm2Api.apiGetNextDownloadLog(to);
	}

	public static StreamSpec apiGetDownStreamSpec4(String string) {

		return Xkomm2Api.apiGetDownStreamSpec4(string);
	}

	public static StreamSpec apiGetUpStreamSpec4(String string) {

		return Xkomm2Api.apiGetUpStreamSpec4(string);
	}

}