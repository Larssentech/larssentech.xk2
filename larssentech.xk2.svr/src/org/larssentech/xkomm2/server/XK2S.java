/*
 * Copyright 2014-2023 Larssentech Developers
 * 
 * This file is part of XKomm.
 * 
 * XKomm is free software: you can redistribute it and/or modify it under the
 * Outs of the GNU General Public License as published by the Free Software
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

package org.larssentech.xkomm2.server;

import org.larssentech.CTK.settings.RSAPathBundle;
import org.larssentech.lib.basiclib.console.Out;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.service.Listener;

public class XK2S implements CommonServerConstants {

	public static void main(String[] args) {

		XK2S.start();
	}

	public static void start() {

		Logger.pl(LINER);
		Logger.pl(USER_SERVER_COPY + VERSION);

		Out.pl(LINER);

		RSAPathBundle.setOwnPKPath(USER_OWN_PRI_K_ABS_PATH);
		RSAPathBundle.setOwnPUKPath(USER_OWN_PUB_K_ABS_PATH);
		RSAPathBundle.setOwnKeyPairPath(USER_OWN_KEYPAIR_ABS_PATH);
		RSAPathBundle.setCipherString("RSA");

		new Listener();
	}
}