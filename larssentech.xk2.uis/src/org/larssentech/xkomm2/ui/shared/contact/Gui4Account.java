/*
 * Copyright 1999-2023 Larssentech Developers
 *
 * This file is part of the Larssentech BasicLib2 project.
 *
 * The Larssentech BasicLib2 Library is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * XKomm is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * the source code. If not, see <http://www.gnu.org/licenses/>.
 */

package org.larssentech.xkomm2.ui.shared.contact;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.larssentech.lib.awtlib.GUITool;
import org.larssentech.lib.basiclib.util.LoginChecker;
import org.larssentech.xkomm.core.obj.objects.AccountPack;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

/**
 * @author jcer Class to implement a generic "account" credentials collection
 * from the user: Account4ApiRunner (login and plain pass in text form, either
 * plain text, encoded text or encrypted text).
 * 
 * This class contains the common objects for the various account-related
 * capabilities, text fields, buttons, etc.
 * 
 * - There are two buttons: OK and cancel. OK updates the AccountPack and after
 * that will check all is good. Cancel merely disposes. - There is also a label
 * that describes the intended usage of the Dialog. - There is a method to
 * pre-populate the text fields. - There is a method to validate that the login
 * has an acceptable syntax. - There is a method to validate that the plain pass
 * has an acceptable syntax by any sub-classes. - There is also a method to
 * request the plain pass be confirmed, some use cases require this. - There is
 * a pulic method to set "pretty" and display the Dialog.
 * 
 */
abstract class Gui4Account extends JDialog {

	protected JTextField emailField;

	public JTextField getEmailField() {

		return this.emailField;
	}

	public JTextField getPassField() {

		return this.passField;
	}

	protected JPasswordField passField;
	private final JButton okButton = new JButton("Send");
	private final JButton cancelButton = new JButton("Cancel");
	private final Panel buttonPanel = new Panel();
	protected final JLabel loginLabel = new JLabel();
	protected final JLabel plainPassLabel = new JLabel();

	protected AccountPack pack;

	/**
	 * A basic constructor that does the GUI and stores the AccountPack
	 * 
	 * @param pack
	 */
	Gui4Account(final AccountPack pack) {

		super(new Frame());

		this.getContentPane().setBackground(Xkomm2Theme.getBackground());

		this.emailField = new JTextField(20);
		this.emailField.setBorder(BorderFactory.createLineBorder(Xkomm2Theme.getBorder(), 1));
		this.passField = new JPasswordField(20);
		this.passField.setBorder(BorderFactory.createLineBorder(Xkomm2Theme.getBorder(), 1));

		this.loginLabel.setForeground(Xkomm2Theme.getForeground());
		this.plainPassLabel.setForeground(Xkomm2Theme.getForeground());

		this.okButton.setPreferredSize(new Dimension(80, 25));
		this.cancelButton.setPreferredSize(new Dimension(80, 25));

		this.buttonPanel.setBackground(Xkomm2Theme.getBackground());

		this.okButton.setBackground(Xkomm2Theme.getBackground());
		this.okButton.setForeground(Xkomm2Theme.getForeground());
		this.okButton.setBorder(BorderFactory.createLineBorder(Xkomm2Theme.getBorder(), 1));

		this.cancelButton.setBackground(Xkomm2Theme.getBackground());
		this.cancelButton.setForeground(Xkomm2Theme.getForeground());
		this.cancelButton.setBorder(BorderFactory.createLineBorder(Xkomm2Theme.getBorder(), 1));

		this.emailField.setHorizontalAlignment(JTextField.CENTER);
		this.passField.setHorizontalAlignment(JTextField.CENTER);
		this.emailField.setBackground(Xkomm2Theme.getBackground());
		this.emailField.setForeground(Xkomm2Theme.getForeground());

		this.passField.setBackground(Xkomm2Theme.getBackground());
		this.passField.setForeground(Xkomm2Theme.getForeground());

		this.pack = pack;
	}

	/**
	 * Method invoked externally to populate the text fields with the values
	 * given in the parameter Account4ApiRunner Pack.
	 * 
	 * @param pack
	 */
	protected void populate(AccountPack pack) {

		this.emailField.setText(pack.getLogin());
		this.passField.setText(pack.getPlainPass());

		if (this.emailField.getText().length() == 0) this.populateEmpty();
	}

	private void populateEmpty() {

		this.emailField.setText("");
		this.passField.setText("");
	}

	/**
	 * Public method to HEADER_SIZE and center the entire GUI and show it
	 * 
	 * @param width
	 * @param height
	 */
	public void makePretty(Font font, int width, int height) {

		this.loginLabel.setFont(font);
		this.plainPassLabel.setFont(font);
		this.emailField.setFont(font);
		this.passField.setFont(font);

		this.setSize(width, height);
		GUITool.center(this);
		this.setVisible(true);
	}

	public void makePretty(int width, int height) {

		this.setSize(width, height);
		GUITool.center(this);
		this.setVisible(true);
	}

	/**
	 * Invoked after the event of clicking OK. This method first will update the
	 * AccountPack with whatever the user entered and then will check the values
	 * for correctness and flag any issues.
	 * 
	 * This method can be invoked as super.doOK() and extended in subclasses to
	 * do other stuff more specific to the capabilities the subclasses may need
	 * to implement.
	 * 
	 */
	protected void doOK() {

		this.pack.update(this.emailField.getText(), new String(this.passField.getPassword()), false, false);
	}

	/**
	 * Invoked after the event of clicking cancel. This is the common and most
	 * basic functionality: check the syntax is correct. This method can be
	 * invoked as super.doCancel() and extended to do other stuff subclasses may
	 * need.
	 * 
	 */
	private void doCancel() {

		this.pack.setLogin("");
		this.dispose();
	}

	protected void build() {

		this.passField.setEchoChar(LoginChecker.ECHO);

		this.loginLabel.setText("           Login           ");
		this.plainPassLabel.setText("           Password           ");

		setModal(true);
		setResizable(false);

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 7));

		this.add(this.loginLabel);
		this.add(this.emailField);
		this.add(this.plainPassLabel);
		this.add(this.passField);

		this.buttonPanel.add(this.okButton);
		this.buttonPanel.add(this.cancelButton);
		this.add(this.buttonPanel);

		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				doOK();
			}
		});

		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				doCancel();
			}
		});

		this.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) doOK();
			}
		});

		this.emailField.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) doOK();
			}
		});

		this.passField.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) doOK();
			}
		});
	}
}
//<-- 100 lines max (200 with comments)