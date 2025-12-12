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

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.larssentech.xkomm2.api.impl.crypto.CtkApiImpl;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.chat.ChatRoomMan;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class Functions4Lock {

	private static int timer;
	private static int limit;
	private static boolean lockNeeded = false;

	Functions4Lock(int i) {

		Functions4Lock.limit = i;
	}

	private static boolean displayLockScreen(Frame frame) {

		Functions4Lock f = new Functions4Lock(Functions4Lock.limit);
		f.new LockScreen(frame).showPasswordInput(Constants4Uis.LBL_ENTER_PASSWORD);

		return true;
	}

	public static boolean updateLockNeeded() {

		if (Functions4Lock.isLockNeeded()) {

			Functions4Lock.increase();

			if (Functions4Lock.timer > Functions4Lock.limit) return true;
		}

		return false;
	}

	private static void increase() {

		if (Functions4Lock.isLockNeeded() && Xkomm2Api.apiGetMe().isInactive()) {

			Functions4Lock.timer++;
		}

		else Functions4Lock.reset();
	}

	private static void reset() {

		timer = 0;
	}

	private static boolean isLockNeeded() {

		return lockNeeded;
	}

	public static void setLockNeeded(boolean lockNeeded) {

		Functions4Lock.lockNeeded = lockNeeded;
	}

	public static void lockNow(Frame frame) {

		frame.setVisible(false);

		ChatRoomMan.hideAllRegisteredFrames();

		boolean b = Functions4Lock.displayLockScreen(frame);

		if (b) {

			reset();

			frame.setVisible(true);

			ChatRoomMan.unhideAllRegisteredFrames();
		}
	}

	public static boolean isScreenLockNeeded() {

		return isLockNeeded();
	}

	private class LockScreen extends Dialog {

		// Top panel
		private final Panel topPanel = new Panel();
		private final Panel midPanel = new Panel();
		private final Panel bottomPanel = new Panel();
		private final Label messageLabel = new Label(Constants4Uis.EMPTY_STRING);
		private final TextField plainPassField = new TextField(20);
		private final Button okButton = new Button(Constants4Uis.LBL_YES);

		private LockScreen(Frame frame) {

			super(frame);

			this.setSize(frame.getSize());
			this.setLocation(frame.getLocation());

			this.setBackground(Xkomm2Theme.getBackground());
			this.messageLabel.setForeground(Xkomm2Theme.getForeground());

			this.initialise();
			this.toFront();
		}

		private void initialise() {

			this.setModal(true);
			this.setResizable(false);

			this.setTitle(Constants4Uis.LBL_SCREEN_LOCKED);

			// We want 2 panels, CENTER and SOUTH
			this.setLayout(new BorderLayout(0, 0));

			this.add(getTopPanel(), BorderLayout.NORTH);
			this.add(getMidPanel(), BorderLayout.CENTER);
			this.add(getBottomPanel(), BorderLayout.SOUTH);

			getTopPanel().add(getMessageLabel());
			getMidPanel().add(getPasswordField());
			getBottomPanel().add(getYesButton());

			// Except the 2 panels
			getTopPanel().setVisible(true);
			getMidPanel().setVisible(true);
			getBottomPanel().setVisible(true);

			getPasswordField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {

						processPassword();

					}
				}
			});

			getYesButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// Yes
					if (e.getActionCommand().equals(Constants4Uis.LBL_YES)) { processPassword(); }
				}
			});
		}

		private void processPassword() {

			if (CtkApiImpl.decPass4Me(Functions4System.loadMyEncPassword()).equals(this.plainPassField.getText())) this.dispose();
		}

		private String showPasswordInput(String messageLabel) {

			// Set stuff visible
			this.getMessageLabel().setVisible(true);
			this.getPasswordField().setVisible(true);
			this.getYesButton().setVisible(true);

			// Set texts
			this.getMessageLabel().setText(messageLabel);
			this.getPasswordField().setText(Constants4Uis.EMPTY_STRING);
			this.getPasswordField().setEchoChar(Constants4Uis.ECHO_CHAR);

			this.getYesButton().setLabel(Constants4Uis.LBL_OK);
			this.getYesButton().setActionCommand(Constants4Uis.LBL_YES);

			// Show (Alert is modal so after show there is a wait)
			this.setVisible(true);

			// Return
			return this.getPasswordField().getText();
		}

		private Panel getTopPanel() {

			return this.topPanel;
		}

		private Panel getBottomPanel() {

			return this.bottomPanel;
		}

		private Label getMessageLabel() {

			return this.messageLabel;
		}

		private TextField getPasswordField() {

			return this.plainPassField;
		}

		private Button getYesButton() {

			return this.okButton;
		}

		private Panel getMidPanel() {

			return this.midPanel;
		}
	}
}