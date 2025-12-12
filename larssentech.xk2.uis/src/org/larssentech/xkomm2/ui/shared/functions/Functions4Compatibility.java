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
package org.larssentech.xkomm2.ui.shared.functions;

import org.larssentech.lib.awtlib.Alert;
import org.larssentech.lib.basiclib.util.LoginChecker;
import org.larssentech.xkomm.core.obj.model.XmlMessageModel;
import org.larssentech.xkomm.core.obj.objects.AccountPack;
import org.larssentech.xkomm.ui.shared.contact.BasicGui4Contact;
import org.larssentech.xkomm2.api.impl.login.AccountPackEncoder;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class Functions4Compatibility {

	public static XmlMessageModel[] loadHistory(String to, int lines) {

		return Xkomm2Api.apiGetHistory(to, lines);
	}

	public static boolean requestStartXk2() {

		return Xkomm2Api.apiStartXK2();
	}

	public static boolean requestInitialLogin(AccountPackEncoder accountPack) {

		return Xkomm2Api.apiInitialLogin(accountPack);
	}

	public static void requestCreateContact() {

		AccountPack p = new AccountPack();

		new BasicGui4Contact(p, Constants4Uis.LOGIN_COLS).makePretty(300, 150);
		// Check login is correct...
		if (LoginChecker.isGood(p.getLogin())) if (new Alert().showConfirm(Constants4Uis.ALERT_ADD_CONT_1 + p.getLogin() + Constants4Uis.ALERT_ADD_CONT_2)) {

			Functions4Contact.requestInviteUser(p.getLogin());
			new Alert().showMessage(Constants4Uis.TITLE_J, Constants4Uis.ALERT_INVITE_SENT);
		}
	}

	public static boolean requestSendTyping(String to) {

		return Xkomm2Api.apiSendTyping(to);
	}

	public static boolean requestIsContact(String to) {

		return Xkomm2Api.apiIsContact(to);
	}

}