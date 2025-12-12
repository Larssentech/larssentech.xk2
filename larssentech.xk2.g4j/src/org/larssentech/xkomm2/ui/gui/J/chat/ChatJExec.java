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
package org.larssentech.xkomm2.ui.gui.J.chat;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.larssentech.lib.awtlib.FileSelector;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Contact;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Graphics;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Messaging;
import org.larssentech.xkomm2.ui.shared.functions.notify.Functions4Notify;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

/**
 * CONTROLLER (EXECUTOR)
 * 
 * Class to "do" things that the user requests either directly from the GUI, the
 * menu or automatically called by the thread the user started when launching
 * the chat room.
 * 
 * @author avanz.io
 * 
 */
class ChatJExec {

	private final ChatJFrame owner;

	ChatJExec(final ChatJFrame owner) {

		this.owner = owner;
		this.doLoadHistory();
	}

	void doRecycleChat() {

		this.owner.getThread().stopThreads();
		Functions4Graphics.requestRecycleFrame(this.owner.getTo(), this.owner);
	}

	boolean doProcessKeystrokes(int keystroke) {

		// TYPING: Sending -- Whatever the keystroke, we tell the ztatuzbar
		this.owner.getTypingSendModule().notifyKeystroke(keystroke, this.owner.getTo());

		if (keystroke == KeyEvent.VK_ENTER) {

			Message m = new Message();
			m = Functions4Messaging.requestSendMessage(this.owner.getTo(), this.owner.getMessageBox().getText());

			if (m.isGood()) {
				this.owner.getChatArea().appendMessage(m);
				this.owner.getMessageBox().setText(null);
				return true;
			}
		}
		return false;
	}

	void doRefresh() {

		this.doReceiveTextMessage();
		this.doReceiveSysMessage();
		this.doReceiveDataMessage();

		if (Functions4Contact.requestOnline4(this.owner.getTo()))
			this.doOnline(Xkomm2Theme.getOnline());
		else
			doOnline(Xkomm2Theme.getOffline());
	}

	private void doOnline(Color c) {

		this.owner.getOnlineBar().setBackground(c);
	}

	private void doReceiveDataMessage() {

		Functions4Messaging.requestReceiveData(this.owner.getTo());

		String s1 = Functions4Messaging.requestNextUploadLog4(this.owner.getTo());
		String s2 = Functions4Messaging.requestNextDownloadLog4(this.owner.getTo());

		if (s1.length() > 0)
			this.owner.getStreamingZtatuzBar().setText(s1 + GReg.PROGRESS_TOKEN + GReg.MSG_STREAM_SEND);
		else if (s2.length() > 0)
			this.owner.getStreamingZtatuzBar().setText(s2 + GReg.PROGRESS_TOKEN + GReg.MSG_STREAM_RECEIVE);
	}

	private void doReceiveSysMessage() {

		Message message = Functions4Messaging.requestdReceiveSys(this.owner.getTo());
		if (message.isGood()) this.owner.getTypingZtatuzBar().notifyReceiveMessage(message);
	}

	/**
	 * Special for GUI J so that Functions4Typing works
	 * 
	 * @return
	 */
	private void doReceiveTextMessage() {

		Message message = Functions4Messaging.requestReceiveTextMessage(this.owner.getTo());

		if (message.isGood()) {

			if (message.getType() == Message.TEXT) {
				this.owner.getTypingZtatuzBar().notifyReceiveMessage(message);
				Functions4Notify.show(this.owner.getTo());

				this.owner.getChatArea().appendMessage(message);

			}
		}
	}

	void doSendStream() {

		String f = FileSelector.getFile(this.owner);

		String messageBody = Functions4Messaging.requestSendStream(this.owner.getTo(), f);

		Message m = Functions4Messaging.requestSendMessage(this.owner.getTo(), messageBody);
		if (m.isGood()) this.owner.getChatArea().appendMessage(m);
	}

	void doClearChat() {

		this.owner.getChatArea().setText("");
		this.owner.getMessageBox().setText("");

		Functions4Messaging.requestDeleteChat(this.owner.getTo());

	}

	void doLoadHistory() {

		this.owner.getChatArea()
				.appendMessages(Functions4Messaging.requestLoadHistory(this.owner.getTo(), GReg.HISTORY_LINES));
	}
}