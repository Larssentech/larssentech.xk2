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

package org.larssentech.xkomm2.ui.shared.util;

import java.io.File;

import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class Alerter2 extends org.larssentech.xkomm.ui.shared.util.Alerter {

	public Alerter2() {

		super(Xkomm2Theme.getBackground(), Xkomm2Theme.getForeground());
	}

	public boolean confirmStreamFile(String fileName) {

		return this.showConfirm(Constants4Uis.TITLE_J + " File Streaming...", "You will be sending file: " + fileName + " (" + new File(fileName).length() / 1000 + "KB)" + "; continue?");
	}

	public boolean confirmExit() {

		return this.showConfirm(Constants4Uis.ALERT_EXIT_CONFIRM);
	}

	public boolean confirmDeleteContact() {

		return this.showConfirm("Are you sure you want to delete this user from your contacts?");
	}

	public void messageProblem(String v) {

		this.showMessage("There is a problem...", v);
	}

	public void showCannotDeleteYourself() {

		this.showMessage("You cannot delete yourself as a contact...");
	}

	public void networkTestFail(boolean exit) {

		this.showMessage(Constants4Uis.TITLE_J, Constants4Uis.JERROR_CODE_001 + "Could not establish initial connection. Check network. Exiting.");
		if (exit) System.exit(-1);
	}

	public void serverPukFailed(boolean exit) {

		this.showMessage(Constants4Uis.TITLE_J, Constants4Uis.JERROR_CODE_002 + "Failed to retrieve the RSA public key for the server. Cannot continue. Exiting.");
		if (exit) System.exit(-1);
	}
}