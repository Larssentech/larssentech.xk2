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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.larssentech.lib.awtlib.DisplayTool2;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Contact;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Graphics;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Inactivity;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Messaging;
import org.larssentech.xkomm2.ui.shared.util.InformationPanels;

/**
 * LISTENER (LISTENING FOR VIEW USER EVENTS)
 * 
 * @author avanz.io
 * 
 */
class ChatJListener implements ItemListener, WindowListener, ComponentListener, ActionListener, KeyListener, MouseMotionListener {

	private final ChatJFrame owner;

	public ChatJListener(final ChatJFrame chatJFrame) {

		this.owner = chatJFrame;
	}

	public void actionPerformed(ActionEvent e) {

		Functions4Inactivity.sh();

		if (e.getActionCommand().equals(GReg.ACTION_CHAT_CLOSE)) this.owner.getExec4Chat().doRecycleChat();
		if (e.getActionCommand().equals(GReg.ACTION_CHAT_STREAM)) this.owner.getExec4Chat().doSendStream();

		if (e.getActionCommand().equals(GReg.ACTION_CHAT_STREAM_CANCEL_SEND)) this.owner.getChatArea().appendMessage(Functions4Messaging.requestCancelSendStream(this.owner.getTo()));
		if (e.getActionCommand().equals(GReg.ACTION_CHAT_STREAM_CANCEL_RECEIVE)) this.owner.getChatArea().appendMessage(Functions4Messaging.requestRejectReceiveStream(this.owner.getTo()));

		if (e.getActionCommand().equals(GReg.ACTION_CHAT_CLEAR)) this.owner.getExec4Chat().doClearChat();
		if (e.getActionCommand().equals(GReg.ACTION_CHAT_DISPLAY_PUK)) InformationPanels.displayPukBase64(this.owner.getTo(), Functions4Contact.requestPuk4(this.owner.getTo()).getStringValue());
		if (e.getActionCommand().equals(GReg.ACTION_SYSTEM_INFO)) InformationPanels.messageShowSystemInfo();
	}

	public void componentResized(ComponentEvent arg0) {

		Functions4Inactivity.sh();
		DisplayTool2.saveDimension(GReg.DISPLAY_FOLDER + GReg.SEP + this.owner.getTo(), this.owner.getSize());
	}

	public void windowClosing(WindowEvent e) {

		this.owner.getExec4Chat().doRecycleChat();
	}

	public void windowDeactivated(WindowEvent e) {

		Functions4Inactivity.sh();
	}

	public void windowDeiconified(WindowEvent e) {

		Functions4Inactivity.sh();
	}

	public void itemStateChanged(ItemEvent e) {

		Functions4Inactivity.sh();
	}

	public void componentHidden(ComponentEvent e) {

		Functions4Inactivity.sh();
	}

	public void windowActivated(WindowEvent e) {

		Functions4Inactivity.sh();
	}

	public void windowIconified(WindowEvent e) {

		Functions4Inactivity.sh();
	}

	public void componentMoved(ComponentEvent e) {

		Functions4Inactivity.sh();
		Functions4Graphics.requestSaveChatFrame(this.owner, this.owner.getTo());
	}

	public void componentShown(ComponentEvent e) {

		Functions4Inactivity.sh();
	}

	public void windowClosed(WindowEvent e) {

		Functions4Inactivity.sh();
	}

	public void windowOpened(WindowEvent e) {

		Functions4Inactivity.sh();
	}

	public void keyPressed(KeyEvent e) {

		Functions4Inactivity.sh();
		if (this.owner.getMessageBox().getText().equals("\n")) this.owner.getMessageBox().setText(null);
		if (this.owner.getExec4Chat().doProcessKeystrokes(e.getKeyCode())) this.owner.getMessageBox().setText(null);
	}

	public void keyReleased(KeyEvent e) {

		Functions4Inactivity.sh();
		if (this.owner.getMessageBox().getText().equals("\n")) this.owner.getMessageBox().setText(null);
		if (this.owner.getExec4Chat().doProcessKeystrokes(e.getKeyCode())) this.owner.getMessageBox().setText(null);
	}

	public void keyTyped(KeyEvent e) {

		Functions4Inactivity.sh();
		if (this.owner.getMessageBox().getText().equals("\n")) this.owner.getMessageBox().setText(null);
		if (this.owner.getExec4Chat().doProcessKeystrokes(e.getKeyCode())) this.owner.getMessageBox().setText(null);

	}

	public void mouseDragged(MouseEvent e) {

		Functions4Inactivity.sh();
	}

	public void mouseMoved(MouseEvent e) {

		Functions4Inactivity.sh();
	}
}