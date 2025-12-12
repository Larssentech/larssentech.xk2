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
package org.larssentech.xkomm2.api.impl.login;

import org.larssentech.lib.basiclib.settings.SettingsUpdater;
import org.larssentech.xkomm.core.obj.objects.AccountPack;
import org.larssentech.xkomm2.api.impl.crypto.CtkApiImpl;

public class AccountPackEncoder extends AccountPack {

	private String encPass = "Not Set";

	public AccountPackEncoder() {

		super();
	}

	@Override
	public AccountPackEncoder load(String accountFile) {

		super.load(accountFile);

		// Migrate plain text password to encrypted
		if (this.plainPass.length() > 0) {

			if (this.plainPass.length() < 100) this.encPass = CtkApiImpl.encPass4Me(this.plainPass);

			else {
				this.encPass = this.plainPass;
				this.plainPass = CtkApiImpl.decPass4Me(this.encPass);

			}
		}

		return this;
	}

	@Override
	public void persist(String accountFile) {

		String tempEncPass = CtkApiImpl.encPass4Me(this.plainPass);

		SettingsUpdater.updateLine(accountFile, "XKomm.login", this.login);
		SettingsUpdater.updateLine(accountFile, "XKomm.pass", tempEncPass);
	}

}
