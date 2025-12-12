package org.larssentech.xkomm2.ui.shared.chat;

import java.awt.event.KeyEvent;

import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class TypingSendModule {

	private int TYPING_CHAR_NUM = 15;

	/**
	 * Variable to store the count of characters we have typed that will hit a
	 * threshold after which we send a single 'typing' ping to the contact and
	 * then reset the counter
	 */
	int sendTypingCounter = this.TYPING_CHAR_NUM - 1;

	/**
	 * Method to notify that a keystroke event has taken place.
	 * 
	 * @param keystroke
	 * @param to
	 */
	public void notifyKeystroke(int keystroke, String to) {

		this.processKeystroke(keystroke, to);
	}

	/**
	 * Method to process that a keystroke event has taken place. If this is an
	 * 'enter' event, then do something, else do something else
	 * 
	 * @param keystroke
	 * @param to
	 */
	private void processKeystroke(int keystroke, String to) {

		if (keystroke == KeyEvent.VK_ENTER) this.sendTypingCounter = this.TYPING_CHAR_NUM - 1;

		else this.processSendTyping(to);
	}

	/**
	 * Method that will be invoked for each keystroke by the user (except for
	 * 'enter' keystroke). This method will increase the counter for typed keys
	 * and then ckeck if the limit has been passed, in which case a single
	 * 'typing' ping is sent. If so, the counter is reset to start counting
	 * again.
	 * 
	 * @param to
	 */
	private void processSendTyping(String to) {

		this.sendTypingCounter++;

		if (this.sendTypingCounter >= this.TYPING_CHAR_NUM)

			// If we have typed 15+ characters, then send a single
			// 'ourTypingCounter' ping to contact
			if (Xkomm2Api.apiSendTyping(to)) this.sendTypingCounter = 0;
	}
}
