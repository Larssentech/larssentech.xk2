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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.larssentech.lib.awtlib.DisplayTool2;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.gui.J.util.AboutBox;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Common;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Contact;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Graphics;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Inactivity;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Lock;
import org.larssentech.xkomm2.ui.shared.functions.Functions4System;
import org.larssentech.xkomm2.ui.shared.util.Alerter2;
import org.larssentech.xkomm2.ui.shared.util.InformationPanels;

class MainJListener implements ComponentListener, ActionListener, WindowListener, ItemListener, KeyListener,
		MouseMotionListener, MouseListener {

	private final MainJFrame owner;

	MainJListener(final MainJFrame owner) {

		this.owner = owner;
	}

	public void componentResized(ComponentEvent arg0) {

		Functions4Graphics.requestSaveMainFrame(this.owner);
	}

	public void actionPerformed(ActionEvent e) {

		Functions4Inactivity.sh();

		if (e.getActionCommand().equals(GReg.ACTION_VIEW_ENC_PASS)) InformationPanels
				.displayPukBase64(Functions4Contact.requestMyEmail(), Functions4Contact.requestEncryptedPassword());
		if (e.getActionCommand().equals(GReg.ACTION_LICENSE)) new InformationPanels().displayLicense();

		if (e.getActionCommand().equals(GReg.ACTION_DELETE_CONTACT)) {

			if (MainJExec.goodSelection()) {

				if (!Functions4Common.requestMeEmail().equals(MainJExec.getSelectedContact()))
					Functions4Contact.requestContactDeletion(MainJExec.getSelectedContact());

				else
					new Alerter2().showCannotDeleteYourself();
			}
		}

		if (e.getActionCommand().equals(GReg.ACTION_LOCK_SCREEN_NOW)) Functions4Lock.lockNow(this.owner);

		if (e.getActionCommand().equals(GReg.ACTION_CREATE_CONTACT)) Functions4Contact.requestCreateContact();

		if (e.getActionCommand().equals(GReg.ACTION_VIEW_CONT_INFO))
			if (MainJExec.goodSelection()) MainJExec.doDisplayContactInfo(MainJExec.getSelectedContact());

		if (e.getActionCommand().equals(GReg.ACTION_ABOUT)) new AboutBox();

		if (e.getActionCommand().equals(GReg.ACTION_SYSTEM_INFO)) InformationPanels.messageShowSystemInfo();

		if (e.getActionCommand().equals(GReg.ACTION_EXIT)) MainJExec.doExit();

		if (e.getActionCommand().equals(GReg.ACTION_DISPLAY_CONT_PUK))
			if (MainJExec.goodSelection()) InformationPanels.displayPukBase64(MainJExec.getSelectedContact(),
					Functions4Contact.requestPuk(MainJExec.getSelectedContact()).getStringValue());

		if (e.getActionCommand().equals(GReg.ACTION_DISPLAY_MY_PUK))
			InformationPanels.displayPukBase64(Functions4Common.requestMeEmail(),
					Functions4Contact.requestPuk(Functions4Common.requestMeEmail()).getStringValue());
	}

	public void windowClosing(WindowEvent e) {

		Functions4Inactivity.sh();
		MainJExec.doExit();
	}

	public void windowDeactivated(WindowEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void windowDeiconified(WindowEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void windowActivated(WindowEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void windowIconified(WindowEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void componentHidden(ComponentEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void componentMoved(ComponentEvent arg0) {

		Functions4Inactivity.sh();
		DisplayTool2.saveLocation(GReg.MAIN_FRAME_DISPLAY, this.owner.getLocation());
	}

	public void componentShown(ComponentEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void windowOpened(WindowEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void windowClosed(WindowEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void itemStateChanged(ItemEvent arg0) {

		Functions4Inactivity.sh();
		if (MainJExec.goodSelection()) MainJExec.WidgetRefresh.doRefreshStatuz(Functions4System.requestHaveNetwork());
	}

	public void keyPressed(KeyEvent arg0) {

		Functions4Inactivity.sh();
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) MainJExec.doLaunch(MainJExec.getSelectedContact());
	}

	public void keyReleased(KeyEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void keyTyped(KeyEvent arg0) {

		Functions4Inactivity.sh();
	}

	public void mouseDragged(MouseEvent e) {

		Functions4Inactivity.sh();
	}

	public void mouseMoved(MouseEvent e) {

		Functions4Inactivity.sh();
	}

	public void mouseClicked(MouseEvent e) {

		Functions4Inactivity.sh();

		if (e.getClickCount() == 2) {

			if (MainJExec.goodSelection()) MainJExec.doLaunch(MainJExec.getSelectedContact());

		}

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}