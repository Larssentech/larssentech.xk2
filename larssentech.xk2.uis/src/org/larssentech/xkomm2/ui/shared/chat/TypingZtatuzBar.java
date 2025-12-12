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
package org.larssentech.xkomm2.ui.shared.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;

import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class TypingZtatuzBar extends JLabel {

	private TypingDisplayModule typingDisplayModule;
	private final int STATUS_TYPING_LENGTH = 2000;
	private final String TYPING = " Typing...";
	private final String EMPTY_STRING = "";
	private final int STATUS_BLANK_LENGTH = 2000;
	private final String contactString;

	public TypingZtatuzBar(String initialText, int alignment, String contactString) {

		super(initialText, alignment);

		this.contactString = contactString;
		this.typingDisplayModule = new TypingDisplayModule();
		this.typingDisplayModule.start();

	}

	/**
	 * Method to notify of a received message. This will add it to the queue if
	 * it 'typing' and arrived after the latest TEXT, as this means it is
	 * 'typing' for the 'next' TEXT. If it is TEXT, then cler the queue.
	 * 
	 */
	public void notifyReceiveMessage(Message m) {

		this.typingDisplayModule.processReceiveMessage(m);
	}

	private class TypingDisplayModule extends Thread {

		private Date latest = new Date(1674828401498L);

		/**
		 * List for the queue of all received 'typing' messages' Long time
		 */
		private final List<Long> typingList = new ArrayList<Long>();

		/**
		 * Internal loop to evaluate whether the 'xxxxx' message has been shown
		 * long enough, 2 seconds
		 */
		@Override
		public void run() {

			while (true) {

				// Typing
				{
					// Fetches oldest SYS message Long time
					Long next = this.typingList.size() > 0 ? this.typingList.remove(0) : 0;

					// Compares if this 'typing' Long time is more recent than
					// the latest TEXT was received
					if (next > (this.latest.getTime() + TypingZtatuzBar.this.STATUS_TYPING_LENGTH))

						if (Xkomm2Api.apiIsOnline(TypingZtatuzBar.this.contactString))

							// The actual display of "typing"
							TypingZtatuzBar.this.setText(TypingZtatuzBar.this.TYPING);
				}

				try {
					// Sleeps 2 seconds of solid display for 'typing' or other
					// text
					Thread.sleep(TypingZtatuzBar.this.STATUS_TYPING_LENGTH);

					// Clears 'typing'
					TypingZtatuzBar.this.setText(TypingZtatuzBar.this.EMPTY_STRING);

					// Stays clear 1 second
					Thread.sleep(TypingZtatuzBar.this.STATUS_BLANK_LENGTH);

				}
				catch (InterruptedException ignored) {}
			}
		}

		/**
		 * Method to ensure not show any more 'typing' that exist in the queue
		 * from before a message arrived (i.e.: right now).
		 */
		private void processReceiveText() {

			this.typingList.clear();
			TypingZtatuzBar.this.setText(TypingZtatuzBar.this.EMPTY_STRING);
			this.latest = new Date();
		}

		/**
		 * Method for adding 'typing' messages to the queue. These are only
		 * added if they arrive into the queue after the 'latest' TEXT message
		 * has been received (that is, for the next message)
		 * 
		 * @param body
		 */
		private void processReceivedTyping() {

			if (new Date().getTime() > (this.latest.getTime() + TypingZtatuzBar.this.STATUS_TYPING_LENGTH))

				this.typingList.add(new Date().getTime());
		}

		/**
		 * Method to notify of a received message. This will add it to the queue
		 * if it 'typing' and arrived after the latest TEXT, as this means it is
		 * 'typing' for the 'next' TEXT. If it is TEXT, then clear the queue.
		 * 
		 */
		private void processReceiveMessage(Message m) {

			if (m.getType() == Message.TEXT) this.processReceiveText();
			if (m.getType() == Message.SYS && m.getBody().equals(TypingZtatuzBar.this.TYPING)) this.processReceivedTyping();
		}
	}
}