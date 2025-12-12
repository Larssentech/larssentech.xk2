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

package org.larssentech.xkomm2.api.util;

import org.larssentech.CTK.settings.RSAPathBundle;

public class ApiConfig extends org.larssentech.xkomm.api.util.Config implements ApiReg {

	public static void setRSA() {

		RSAPathBundle.setOwnPKPath(OWN_PRI_K_ABS_PATH);
		RSAPathBundle.setOwnPUKPath(OWN_PUB_K_ABS_PATH);
		RSAPathBundle.setOwnKeyPairPath(OWN_KEYPAIR_ABS_PATH);
		RSAPathBundle.setCipherString("RSA");
	}
}