/*
 * Copyright 2014-2025 Larssentech Developers
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.chat.ChatArea;
import org.larssentech.xkomm2.ui.shared.chat.MessageBox;
import org.larssentech.xkomm2.ui.shared.chat.StreamingZtatuzBar;
import org.larssentech.xkomm2.ui.shared.chat.TypingSendModule;
import org.larssentech.xkomm2.ui.shared.chat.TypingZtatuzBar;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Graphics;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

/**
 * VIEW
 * 
 * Class that builds the GUI for the chat room. Apart from that, this class does
 * not "do" anything else but provide ("get") objects to the Exec and other
 * controller classes that actually "do" things.
 * 
 * @author avanz.io
 *
 */
class ChatJFrame extends JFrame {

	private final ChatJExec chatJExec;
	private final String to;
	private final MessageBox messageBox;
	private final TypingZtatuzBar typingZtatuzBar;
	private final StreamingZtatuzBar streamingZtatuzBar;
	private final Panel onlineBar;
	private final ChatArea chatArea;
	private ChatJThread t;
	private final TypingSendModule typingSendModule;

	ChatJFrame(final String to) {

		// Initialise fields
		this.to = to;
		this.chatArea = new ChatArea();

		this.messageBox = new MessageBox();

		this.messageBox.setPreferredSize(new Dimension(this.getWidth(), 50));

		// Typing bar
		this.typingZtatuzBar = new TypingZtatuzBar("", SwingConstants.LEFT, this.to);
		this.typingZtatuzBar.setPreferredSize(new Dimension(100, 30));
		this.typingZtatuzBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		this.typingZtatuzBar.setForeground(Xkomm2Theme.getOnline());

		// Streaming bar
		this.streamingZtatuzBar = new StreamingZtatuzBar("", SwingConstants.RIGHT);
		this.streamingZtatuzBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.streamingZtatuzBar.setForeground(Xkomm2Theme.getOnline());

		// The onlineBar/offline bar
		this.onlineBar = new Panel();
		this.onlineBar.setPreferredSize(new Dimension(this.getWidth(), 4));
		this.onlineBar.setBackground(Xkomm2Theme.getGrayedOut());

		this.buildLayout();

		// Format the Frame
		this.getContentPane().setBackground(Xkomm2Theme.getBackground());
		this.setMinimumSize(new Dimension(300, 500));
		this.setMenuBar(new ChatJMenuBar(this));
		this.setTitle(this.getTo());

		// Functionality-- can we move this to exec?
		this.typingSendModule = new TypingSendModule();

		// Exec needed before thread
		this.chatJExec = new ChatJExec(this);
		this.startThread();
		this.addListeners();

		// Show!
		Functions4Graphics.requestLoadSavedChatFrame(this.to, this);
		this.setVisible(true);
	}

	private void addListeners() {

		this.addWindowListener(new ChatJListener(this));
		this.addMouseMotionListener(new ChatJListener(this));
		this.addComponentListener(new ChatJListener(this));
		this.chatArea.addMouseMotionListener(new ChatJListener(this));
		this.messageBox.getTextArea().addKeyListener(new ChatJListener(this));
	}

	private void startThread() {

		this.t = new ChatJThread(this);
		this.t.setName(GReg.CHAT_THREAD);
		this.t.start();
	}

	private void buildLayout() {

		this.setLayout(new BorderLayout(GReg.ZERO, GReg.ZERO));

		// Panel for main widgets
		final Panel centreP = new Panel();

		centreP.setBackground(Xkomm2Theme.getBackground());
		centreP.setLayout(new BorderLayout(GReg.ZERO, GReg.ZERO));

		centreP.add(GReg.NORTH, this.onlineBar);
		centreP.add(GReg.CENTER, this.chatArea);
		centreP.add(GReg.SOUTH, this.messageBox);

		// Panel for status bar(s)
		final Panel southP = new Panel();

		southP.setBackground(Xkomm2Theme.getBackground());
		southP.setLayout(new BorderLayout(GReg.ZERO, GReg.ZERO));

		southP.add(GReg.WEST, this.typingZtatuzBar);
		southP.add(GReg.CENTER, this.streamingZtatuzBar);

		this.add(GReg.CENTER, centreP);
		this.add(GReg.SOUTH, southP);

	}

	TypingZtatuzBar getTypingZtatuzBar() { return this.typingZtatuzBar; }

	StreamingZtatuzBar getStreamingZtatuzBar() { return this.streamingZtatuzBar; }

	MessageBox getMessageBox() { return this.messageBox; }

	ChatArea getChatArea() { return this.chatArea; }

	String getTo() { return this.to; }

	ChatJExec getExec4Chat() { return this.chatJExec; }

	ChatJThread getThread() { return this.t; }

	Panel getOnlineBar() { return this.onlineBar; }

	TypingSendModule getTypingSendModule() {

		return this.typingSendModule;
	}

}
//<-- Max is 200 lines!!!