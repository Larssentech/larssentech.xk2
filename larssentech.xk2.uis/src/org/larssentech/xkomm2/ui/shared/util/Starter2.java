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

import org.larssentech.lib.awtlib.Alert;
import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm2.api.util.ApiConfig;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class Starter2 implements Constants4Uis, Constants4API {

	public static void verify() {

		ApiConfig.setConfiguration();
		ApiConfig.setRSA();

		if (!Xkomm2Api.apiCheckStartupConditions(false)) Starter2.errorQuit(ERROR_BAD_SYSTEM);

	}

	private static void errorQuit(String string) {

		new Alert(Xkomm2Theme.getBackground(), Xkomm2Theme.getForeground()).showMessage(ERROR_S, string);
		System.exit(0);
	}
}