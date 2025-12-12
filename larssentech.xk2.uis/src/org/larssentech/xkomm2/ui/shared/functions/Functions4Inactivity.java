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

import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

/**
 * Simple class to keep a 1 second counter for when the user "me" is not
 * interacting with XK2's user interface. This counter can be reset by invoking
 * sh(), typically invoked by the Listeners associated with the user
 * interface(s)
 * 
 * @author Jeff Cerasuolo
 *
 */
public class Functions4Inactivity extends Thread {

	private static long inactiveSeconds = 0;

	@Override
	public void run() {

		this.setName(Constants4Uis.INACTIVITY_THREAD);

		while (true) {

			Functions4Inactivity.setInactiveSeconds(Functions4Inactivity.getInactiveSeconds() + 1);

			Xkomm2Api.apiSetMyInactiveSeconds(Functions4Inactivity.getInactiveSeconds());

			try {

				Thread.sleep(Constants4Uis.INACTIVITY_1_SECOND);
			} catch (InterruptedException ignored) {

			}
		}
	}

	public static void sh() {

		Functions4Inactivity.setInactiveSeconds(0);
	}

	private static long getInactiveSeconds() {

		return inactiveSeconds;
	}

	private static void setInactiveSeconds(long inactiveSeconds) {

		Functions4Inactivity.inactiveSeconds = inactiveSeconds;
	}
}