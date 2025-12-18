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

import java.awt.Color;

import org.larssentech.lib.log.Logg3r;
import org.larssentech.xkomm2.ui.gui.J.chat.Launcher4ChatJ;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Common;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Contact;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Fx;
import org.larssentech.xkomm2.ui.shared.functions.Functions4System;
import org.larssentech.xkomm2.ui.shared.util.InformationPanels;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

class MainJExec {

	private static boolean suspended;

	synchronized static void doLaunch(String contactString) {

		new Launcher4ChatJ(contactString);
	}

	static void doDisplayContactInfo(String contactString) {

		// Display the relevant info aboot' the contact
		InformationPanels.displayContactProperties(contactString, Functions4Contact.requestIdForEmail(contactString), Functions4Contact.requestInactiveMode4(contactString), Functions4Contact.requestLastSeen4(contactString).toString());
	}

	static void doSuspend(boolean b) {

		Functions4System.requestSuspend(b);

		MainJExec.suspended = b;
		Logg3r.log(GReg.MSG_SUSPEND + b);
	}

	/**
	 * This method runs every x seconds and it is the job of the exec invoked
	 * methods to update the GUI if any network issues
	 */
	static void doMainJRefresh() {

		final boolean online = Functions4System.requestHaveNetwork();

		MainJExec.WidgetRefresh.doRefreshContacts(online);
		MainJExec.WidgetRefresh.doRefreshOnline(online);
		MainJExec.WidgetRefresh.doRefreshStatuz(online);
		MainJExec.doCheck4Messages();

		/*- 20251215 - FX integration begins */
		// MainJExec.doMonitorFx(online);
	}

	private static void doMonitorFx(boolean online) {
		Functions4Fx.monitor(online);
	}

	/**
	 * Method to check for available messages in order to launch a chat frame for
	 * the contact whose messages are waiting
	 */
	private static void doCheck4Messages() {

		// ...only if we can
		if (!MainJExec.suspended) {

			// Get all contacts
			String[] contacts = MainJExec.getAllContacts();

			// And launch a chat room if a message is waiting
			for (int i = 0; i < contacts.length; i++) if (Functions4Common.requestInboxCountFrom(contacts[i]) > 0) doLaunch(contacts[i]);
		}
	}

	static boolean goodSelection() {

		return MainJFrame.getContactContainer().getSelectedUserEntry() != null && MainJFrame.getContactContainer().getSelectedUserEntry().length() > 0;
	}

	static String getSelectedContact() {
		return MainJFrame.getContactContainer().getSelectedUserEntry();
	}

	private static String[] getAllContacts() {
		return MainJFrame.getContactContainer().getAllUserEntries();
	}

	/**
	 * Internal class to encapsulate the actual changes to the graphical components.
	 * This class has methods for each of the graphical components and within each
	 * we consider the multiple scenarios the app can be in. This allows for each of
	 * the graphical components to be independently updated.
	 * 
	 * @author Jeff Cerasuolo
	 *
	 */
	static class WidgetRefresh {

		private static int offlines;

		static void doRefreshStatuz(boolean online) {

			// Suspended
			if (MainJExec.suspended) updateStatus(GReg.SUSPENDED, Xkomm2Theme.getDisabledColorText());

			// We offline
			// else if (!online) updateStatus(GReg.LBL_OFFLINE, Color.orange);
			// // Xkomm2Theme.getDisabledColorText());

			// Normal, good selection, user online or offline
			else if (goodSelection()) updateStatus(Functions4Contact.requestActivityLabel(MainJExec.getSelectedContact()), Functions4Contact.requestOnline4(MainJExec.getSelectedContact()) ? Xkomm2Theme.getOnline() : Xkomm2Theme.getGrayedOutText());

			// If we are online, but no user appears online, connection problems
			// else if (Functions4System.requestAllAreOffline() &&
			// !Functions4System.requestTestNetwork(false, false))
			// updateStatus("Connection is unstable...", Color.orange);

			// Normal or no selection
			else updateStatus(GReg.FRAME4MAIN_TITLE_VERSION, Xkomm2Theme.getGrayedOutText());
		}

		private static void updateStatus(String s, Color c) {

			MainJFrame.getMainZtatuzBar().setForeground(c);
			MainJFrame.getMainZtatuzBar().setText(s);
		}

		private static void doRefreshOnline(boolean online) {

			// Suspended if (MainJExec.suspended)
			if (suspended) MainJFrame.getOnlineBar().setBackground(Xkomm2Theme.getOffline());

			// We offline else if (!online)
			else if (!online) {
				offlines++;
				if (offlines <= GReg.OFFLINES_MAX) MainJFrame.getOnlineBar().setBackground(Xkomm2Theme.getOfflineTemp());
				else MainJFrame.getOnlineBar().setBackground(Xkomm2Theme.getOffline());
			}

			// Normal else
			else {
				MainJFrame.getOnlineBar().setBackground(Xkomm2Theme.getOnline());
				offlines = 0;
			}

		}

		private static void doRefreshContacts(boolean online) {

			// If we are offline avoid flickering
			if (!online) return;

			// Suspended
			if (MainJExec.suspended) MainJFrame.getContactContainer().setEnabled(false);

			// We offline
			// else if (!online)
			// MainJFrame.getContactContainer().setEnabled(false);

			// Normal
			else {

				MainJFrame.getContactContainer().setEnabled(true);
				MainJFrame.getContactContainer().doRefreshLizt(Functions4Contact.requestContactStatuses(), Functions4Contact.requestContactEmails());
			}
		}
	}

	public static void doExit() {

		Functions4System.requestExit(3);
	}
}
//<-- Max 100 lines>