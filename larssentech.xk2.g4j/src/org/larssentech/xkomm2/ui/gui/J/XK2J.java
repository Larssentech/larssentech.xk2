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

package org.larssentech.xkomm2.ui.gui.J;

import org.larssentech.lib.basiclib.console.Out;
import org.larssentech.xkomm2.core.obj.version.Version4Xk2;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.gui.J.main.Launcher4MainJ;
import org.larssentech.xkomm2.ui.gui.J.util.ThemeMaker;
import org.larssentech.xkomm2.ui.shared.functions.Functions4Login;
import org.larssentech.xkomm2.ui.shared.functions.Functions4System;
import org.larssentech.xkomm2.ui.shared.util.Starter2;

public class XK2J {

	public static void main(String[] args) {

		Functions4System.requestLoggingEnabled(true);

		// Set the Colour Theme
		ThemeMaker.makeTheme();

		// Print the copyright
		XK2J.print();

		// Start
		new XK2J();
	}

	XK2J() {

		// Checks for everything to be OK to start
		Starter2.verify();

		// Login and launch
		if (Functions4Login.requestLogin()) new Launcher4MainJ();
	}

	/**
	 * Method to print the basic (c) sh!t
	 */
	public static void print() {

		Out.pl("");
		Out.pl("------------------------------------------");
		Out.pl("Larssentech XKomm Version: " + Version4Xk2.BASE_VERSION_STRING);
		Out.pl("XK2J GUI " + GReg.J_VERSION);
		Out.pl("------------------------------------------");
		Out.pl("");
	}
}