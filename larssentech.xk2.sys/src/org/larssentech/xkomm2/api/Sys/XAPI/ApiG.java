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

package org.larssentech.xkomm2.api.Sys.XAPI;

import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm2.api.Sys.command.CommandManager;
import org.larssentech.xkomm2.api.Sys.constants.SysConstants;

public class ApiG {

	public static String processMessage(Message m) {

		if (m.getBody().startsWith(SysConstants.XTRA_COMMAND)) {

			if (m.isGood()) {

				// if (Hub.hubNetwork()) {

				String response = CommandManager.runXtraCommand(m.getBody());

				if (response.length() > 0) return response;

				// }
			}
		}
		return "";
	}
}