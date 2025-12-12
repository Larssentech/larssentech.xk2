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

package org.larssentech.xkomm2.api.Sys.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.larssentech.xkomm2.api.Sys.constants.SysConstants;

class UnixCommandRunner implements SysConstants {

	static String execUnixCommand(String string) {

		String s = G_INV_CMD;

		try {
			s = UnixCommandRunner.execUnix(string);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}

	/**
	 * Syntax: User sends unix command + space + argument
	 * 
	 * @param string
	 * @return
	 */
	private static String execUnix(String string) throws IOException {

		String command = "";
		String argument = "";

		if (string.contains(G_SPC)) {
			command = string.substring(0, string.indexOf(G_SPC));
			argument = string.substring(string.indexOf(G_SPC) + 1, string.length());
		}

		else command = string;

		String[] array;
		if (argument.length() == 0) array = new String[] { command };
		else array = new String[] { command, argument };

		Process p = Runtime.getRuntime().exec(array);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		String s = null;
		String returnS1 = "_@: ";

		while ((s = stdInput.readLine()) != null) returnS1 += s + "\n";

		while ((s = stdError.readLine()) != null) returnS1 += s + "\n";

		stdInput.close();
		stdError.close();

		return returnS1;
	}
}