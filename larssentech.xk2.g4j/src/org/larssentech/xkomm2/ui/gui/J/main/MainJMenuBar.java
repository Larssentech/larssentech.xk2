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

import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.larssentech.lib.log.Logg3r;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Inactivity;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Lock;

class MainJMenuBar extends MenuBar {

	protected MainJMenuBar(final MainJFrame owner) {

		this.add(new MainFrameMenuFile(owner));
		this.add(new MainFrameMenuContact(owner));
		this.add(new MainFrameMenuAccount(owner));
		this.add(new MainFrameMenuHelp(owner));
	}
}

class MainFrameMenuContact extends Menu {

	MainFrameMenuContact(final MainJFrame owner) {

		this.setName(GReg.MENU_CONTACT);
		this.setLabel(GReg.MENU_CONTACT);

		final MenuItem viewContactInfo = new MenuItem(GReg.MENU_VIEW_CONTACT_INFO);
		final MenuItem createContact = new MenuItem(GReg.MENU_CREATE_CONTACT);
		final MenuItem deleteContact = new MenuItem(GReg.MENU_DELETE_CONTACT);
		final MenuItem viewContactPuk = new MenuItem(GReg.MENU_VIEW_CONTACT_PUK);

		this.add(createContact);
		this.add(deleteContact);
		this.addSeparator();

		this.add(viewContactInfo);
		this.add(viewContactPuk);

		viewContactInfo.setActionCommand(GReg.ACTION_VIEW_CONT_INFO);
		createContact.setActionCommand(GReg.ACTION_CREATE_CONTACT);
		deleteContact.setActionCommand(GReg.ACTION_DELETE_CONTACT);
		viewContactPuk.setActionCommand(GReg.ACTION_DISPLAY_CONT_PUK);

		viewContactInfo.addActionListener(new MainJListener(owner));
		createContact.addActionListener(new MainJListener(owner));
		deleteContact.addActionListener(new MainJListener(owner));
		viewContactPuk.addActionListener(new MainJListener(owner));

		this.addActionListener(new MainJListener(owner));
	}
}

class MainFrameMenuAccount extends Menu {

	MainFrameMenuAccount(final MainJFrame owner) {

		this.setName(GReg.MENU_ACCOUNT);
		this.setLabel(GReg.MENU_ACCOUNT);

		final MenuItem viewEncPass = new MenuItem(GReg.MENU_VIEW_ENC_PASS);
		final MenuItem viewMyPuk = new MenuItem(GReg.MENU_VIEW_MY_PUK);
		final MenuItem lockScreen = new MenuItem(GReg.LOCK_SCREEN);

		final CheckboxMenuItem enableLockScreen = new CheckboxMenuItem(GReg.MENU_ENABLE_LOCK_SCREEN);

		enableLockScreen.setState(false);

		this.add(enableLockScreen);
		this.add(lockScreen);

		this.addSeparator();
		this.add(viewMyPuk);
		this.add(viewEncPass);

		viewEncPass.setActionCommand(GReg.ACTION_VIEW_ENC_PASS);
		viewEncPass.addActionListener(new MainJListener(owner));

		viewMyPuk.setActionCommand(GReg.ACTION_DISPLAY_MY_PUK);
		viewMyPuk.addActionListener(new MainJListener(owner));

		lockScreen.setActionCommand(GReg.ACTION_LOCK_SCREEN_NOW);
		lockScreen.addActionListener(new MainJListener(owner));

		enableLockScreen.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {

				Functions4Lock.setLockNeeded(!Functions4Lock.isScreenLockNeeded());

				Logg3r.log(GReg.TERM_LOCK_NEEDED + Functions4Lock.isScreenLockNeeded());
			}
		});
	}
}

class MainFrameMenuFile extends Menu {

	MainFrameMenuFile(MainJFrame owner) {

		this.setLabel(GReg.MENU_fILE);
		this.setName(GReg.MENU_fILE);

		final MenuItem exit = new MenuItem(GReg.MENU_EXIT);

		this.add(exit);

		exit.setActionCommand(GReg.ACTION_EXIT);
		exit.addActionListener(new MainJListener(owner));
	}
}

class MainFrameMenuHelp extends Menu {

	MainFrameMenuHelp(MainJFrame owner) {

		this.setLabel(GReg.MENU_HELP);
		this.setName(GReg.MENU_HELP);

		final MenuItem system = new MenuItem(GReg.MENU_SYSTEM_INFO);
		final MenuItem license = new MenuItem(GReg.MENU_LICENSE);
		final MenuItem about = new MenuItem(GReg.MENU_ABOUT);
		final CheckboxMenuItem suspend = new CheckboxMenuItem(GReg.MENU_SUSPEND);

		this.add(system);
		this.add(suspend);

		this.addSeparator();
		this.add(license);
		this.add(about);

		about.setActionCommand(GReg.ACTION_ABOUT);
		system.setActionCommand(GReg.ACTION_SYSTEM_INFO);
		license.setActionCommand(GReg.ACTION_LICENSE);
		suspend.setActionCommand(GReg.ACTION_SUSPEND);

		about.addActionListener(new MainJListener(owner));
		system.addActionListener(new MainJListener(owner));
		license.addActionListener(new MainJListener(owner));

		suspend.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				Functions4Inactivity.sh();

				MainJExec.doSuspend(suspend.getState());
			}
		});
	}
}