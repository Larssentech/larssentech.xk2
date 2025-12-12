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
package org.larssentech.xkomm2.ui.shared.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.larssentech.lib.basiclib.toolkit.StringManipulationToolkit;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class InformationPanels {

	public static void displayPukBase64(String who, String pukS) {

		String[] pukA = StringManipulationToolkit.chunkString(pukS, 58);
		String pukChop = "";

		for (int i = 0; i < pukA.length; i++)
			pukChop += pukA[i] + "\n";

		new JInfoPanel(true, 500, 250).showInfo("Public Key for " + who, pukChop);
	}

	public static void displayContactProperties(String login, String id, String mode, String lastSeen) {

		String message = "";

		message += "User login: " + Constants4Uis.NEW_LINE;
		message += login + Constants4Uis.NEW_LINE;
		message += "User ID: ";
		message += id + Constants4Uis.NEW_LINE;
		message += "User mode: ";
		message += mode + Constants4Uis.NEW_LINE;
		message += "User last seen: " + Constants4Uis.NEW_LINE;
		message += lastSeen + Constants4Uis.NEW_LINE;

		message += Constants4Uis.NEW_LINE;

		new JInfoPanel(true, 500, 250).showInfo(Constants4Uis.TITLE_J + " Contact Info", message);

	}

	public static void messageShowSystemInfo() {

		new JInfoPanel(true, 500, 250).showInfo(Constants4Uis.TITLE_J + " - System Properties",
				Constants4Uis.SYSTEM_INFO);
	}

	public void displayLicense() {

		try {

			InputStream in = getClass().getResourceAsStream(Constants4Uis.LICENSE_FILE);

			Reader fr = new InputStreamReader(in, "utf-8");

			BufferedReader buf = new BufferedReader(fr);

			new JInfoPanel(true, 500, 500).showInfo("XKomm License", buf);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
