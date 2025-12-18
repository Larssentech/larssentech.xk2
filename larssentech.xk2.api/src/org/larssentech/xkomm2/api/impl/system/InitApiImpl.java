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
package org.larssentech.xkomm2.api.impl.system;

import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.xkomm.core.hub.req.Hub;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm2.api.impl.crypto.CipherApiImpl;
import org.larssentech.xkomm2.api.impl.crypto.CtkApiImpl;
import org.larssentech.xkomm2.api.impl.login.AccountPackEncoder;
import org.larssentech.xkomm2.api.impl.stream.StreamDownApiImpl;
import org.larssentech.xkomm2.api.impl.stream.StreamUpApiImpl;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class InitApiImpl extends org.larssentech.xkomm.api.impl.Impl4Init {

	private static boolean serverPukOK;

	public static boolean retrieveServerPuk() {

		if (InitApiImpl.networkTestOK) {

			new CtkApiImpl();

			PUK puk = Hub.hubGetServerPUK();

			if (puk.isGood()) {
				CipherApiImpl.setServerPuk(puk);
				InitApiImpl.serverPukOK = true;
			}

			else {
				InitApiImpl.serverPukOK = false;
			}
		}
		return InitApiImpl.serverPukOK;
	}

	public static boolean startXK2() {

		if (InitApiImpl.loginOK) {

			InitApiImpl.startXK2OK = Hub.hubStartXkomm();

			StreamUpApiImpl.init();
			StreamDownApiImpl.init();

			return InitApiImpl.startXK2OK;
		}

		else {

			Xkomm2Api.apiPl("XAPI failed to start cannot continue.");
			return false;
		}
	}

	public static boolean initialLogin(AccountPackEncoder pack) {

		// Read our PUK and encrypt our password and create the "me" user
		User me = new User(pack.getLogin(), Xkomm2Api.ctkApiImpl.encPass4Server(pack.getPlainPass()), CipherApiImpl.getPuk4Me());

		// Set "me"
		Hub.hubSetMe(me);

		// and try to log in and start sh*t, and store result
		InitApiImpl.loginOK = Hub.hubInitialLogin();

		// Return success or otherwise
		return loginOK;
	}
}
