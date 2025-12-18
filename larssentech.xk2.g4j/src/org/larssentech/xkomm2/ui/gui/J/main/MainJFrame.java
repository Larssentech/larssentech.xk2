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

package org.larssentech.xkomm2.ui.gui.J.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.JFrame;

import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Contact;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Graphics;
import org.larssentech.xkomm2.ui.shared.functions.Functions4System;
import org.larssentech.xkomm2.ui.shared.main.ContactContainer;
import org.larssentech.xkomm2.ui.shared.main.MainZtatuzBar;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

/**
 * Simple GUI class (1) for XK2. This class extends AWT Frame and has 3
 * components: the "me" field, which displays our account email address, the
 * list of contacts, which displays their account email addresses and whether
 * they are onlineBar or not, and a status bar at the bottom, whose main job is
 * to display onlineBar/offline and inactive for "X" minutes when a contact is
 * selected on the list. This class also features a menu bar (2) with menus
 * containing menu items. These provide additional functionality, such as
 * creating a new contact or viewing account information for the user or
 * contacts.
 * 
 * This class uses a thread (3) to refresh itself and associated with listeners
 * (4) to process user actions. Finally, there is an "exec" (delegate) (5) that
 * interacts with the XK2 UIS API.
 */
class MainJFrame extends JFrame {

	private static final ContactContainer contactContainer = new ContactContainer();
	private static final MainZtatuzBar mainZtatuzBar = new MainZtatuzBar(GReg.FRAME4MAIN_TITLE_VERSION);
	private static final Panel onlineBar = new Panel();

	MainJFrame() {

		Functions4System.requestInitialiseBundle();

		// Listeners
		MainJFrame.contactContainer.getContactLizt().addMouseListener(new MainJListener(this));
		MainJFrame.contactContainer.getContactLizt().addKeyListener(new MainJListener(this));
		MainJFrame.contactContainer.getContactLizt().addMouseMotionListener(new MainJListener(this));
		MainJFrame.onlineBar.setPreferredSize(new Dimension(this.getWidth(), 4));
		MainJFrame.onlineBar.setBackground(Xkomm2Theme.getGrayedOut());

		// Build the graphical widgets on the Frame
		this.build(Functions4Contact.requestMyEmail());

		// Start the Thread to keep it all orchestrated
		MainJThread m = new MainJThread(this);
		m.start();
	}

	private void build(String login) {

		this.setTitle(GReg.FRAME4MAIN_TITLE_VERSION);
		this.getContentPane().setBackground(Xkomm2Theme.getBackground());

		this.setLayout(new BorderLayout(GReg.ZERO, GReg.ZERO));

		this.addWindowListener(new MainJListener(this));

		this.add(GReg.NORTH, MainJFrame.onlineBar);
		this.add(GReg.CENTER, MainJFrame.contactContainer);
		this.add(GReg.SOUTH, MainJFrame.mainZtatuzBar);

		this.setMenuBar(new MainJMenuBar(this));

		this.pack();

		Functions4Graphics.requestLoadSavedMainFrame(this);

		this.addComponentListener(new MainJListener(this));

		this.setVisible(true);
	}

	static ContactContainer getContactContainer() {
		return MainJFrame.contactContainer;
	}

	static MainZtatuzBar getMainZtatuzBar() {
		return MainJFrame.mainZtatuzBar;
	}

	static Panel getOnlineBar() {
		return MainJFrame.onlineBar;
	}
}
// <-- Max 100 lines!