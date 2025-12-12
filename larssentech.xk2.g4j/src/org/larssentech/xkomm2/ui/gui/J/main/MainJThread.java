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

import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Lock;
import org.larssentech.xkomm2.ui.shared.functions.Functions4System;

class MainJThread extends Thread {

	private final MainJFrame owner;

	MainJThread(MainJFrame owner) {

		this.owner = owner;
		this.setName(GReg.MAIN_THREAD);
	}

	@Override
	public void run() {

		while (true) {

			try {

				// Main GUI refresh call
				MainJExec.doMainJRefresh();

				// Timeout locking of the GUI for privacy and security
				if (Functions4Lock.updateLockNeeded()) Functions4System.requestLock(this.owner);

				// And sleep a bit
				Thread.sleep(GReg.FRAME4MAIN_SLEEP_FAST);
			} catch (Exception e) {

				// Log errors
				e.printStackTrace();
				Functions4System.requestLogError(e);
			}

			// If an update has been found
			if (Functions4System.requestUpdateFound()) {

				// Deal with the update
				Functions4System.requestFirestarter();
				Functions4System.requestRestart();
			}
		}
	}
}