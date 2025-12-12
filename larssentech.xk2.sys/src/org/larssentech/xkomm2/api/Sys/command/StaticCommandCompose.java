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

import org.larssentech.xkomm2.api.Sys.constants.SysConstants;
import org.larssentech.xkomm2.core.obj.version.Version4Xk2;

class StaticCommandCompose {

	static String version(String string) {

		return string + ": " + Version4Xk2.BASE_VERSION_STRING + " / " + Version4Xk2.J_VERSION;
	}

	static String sysInfo(String string) {

		return string + ": " + SysConstants.SYSTEM_INFO;
	}

}
