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

import java.util.Date;

import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.awtlib.Alert;
import org.larssentech.lib.basiclib.toolkit.DateManipulationToolkit;
import org.larssentech.lib.basiclib.util.LoginChecker;
import org.larssentech.xkomm.core.obj.objects.AccountPack;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.contact.Frame4Contact;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class Functions4Contact {

	public static void deleteContact(String to) {

		final String me = Xkomm2Api.apiGetMe().getLogin().getEmail();

		// When opening for first time, no contact is selected
		if (null != to) {

			if (!me.equals(to)) Xkomm2Api.apiDeleteContact(to);
		}
	}

	public static User requestMe() {

		return Xkomm2Api.apiGetMe();
	}

	public static boolean createContact(String to) {

		// Check login is correct and user confirmation
		if (!Xkomm2Api.apiIsContact(to)) {
			Xkomm2Api.apiInviteUser(to);
			return true;
		}

		return false;
	}

	public static void requestCreateContact() {

		AccountPack p = new AccountPack();

		new Frame4Contact(p, Constants4Uis.LOGIN_COLS).makePretty(300, 150);

		// Check login is correct...
		if (LoginChecker.isGood(p.getLogin())) if (new Alert(Xkomm2Theme.getBackground(), Xkomm2Theme.getForeground()).showConfirm(Constants4Uis.ALERT_ADD_CONT_1 + p.getLogin() + Constants4Uis.ALERT_ADD_CONT_2)) {

			Functions4Contact.requestInviteUser(p.getLogin());
			new Alert(Xkomm2Theme.getBackground(), Xkomm2Theme.getForeground()).showMessage(Constants4Uis.TITLE_J, Constants4Uis.ALERT_INVITE_SENT);
		}
	}

	public static boolean requestDeleteContact(String contactString) {

		if (LoginChecker.isGood(contactString))

			return Xkomm2Api.apiDeleteContact(contactString);

		return false;
	}

	public static void requestContactDeletion(String contactString) {

		if (LoginChecker.isGood(contactString))

			if (new Alert(Xkomm2Theme.getBackground(), Xkomm2Theme.getForeground()).showConfirm(Constants4Uis.ALERT_DELETE_1 + contactString + Constants4Uis.QUESTION_MARK))

				Xkomm2Api.apiDeleteContact(contactString);
	}

	public static PUK requestPuk(String contactString) {

		return Xkomm2Api.apiGetContact4Email(contactString).getKeyPair().getPuk();
	}

	public static String requestIdForEmail(String contactString) {

		return Xkomm2Api.apiGetContact4Email(contactString).getId();
	}

	public static String requestInactiveMode4(String contactString) {

		return Xkomm2Api.apiGetContact4Email(contactString).getStatus();
	}

	public static Date requestLastSeen4(String contactString) {

		return Xkomm2Api.apiGetContact4Email(contactString).getLastSeen();
	}

	static void requestInviteUser(String contactString) {

		Xkomm2Api.apiInviteUser(contactString);
	}

	public static String requestMyEmail() {

		return Xkomm2Api.apiGetMe().getLogin().getEmail();
	}

	public static String requestEncryptedPassword() {

		return Xkomm2Api.apiGetEncryptedPassword();
	}

	private static String requestSinceLabel(String contactString) {

		Date lastSeenDate = Xkomm2Api.apiGetContact4Email(contactString).getLastSeen();

		String dateS = Constants4Uis.EMPTY_STRING;

		if (null == lastSeenDate || lastSeenDate.getTime() == 0) dateS = Constants4Uis.MSG_STATUS_NOT_AVALABLE;

		else dateS = DateManipulationToolkit.formatDate(lastSeenDate, Constants4Uis.DATE_PATTERN);

		return Constants4Uis.LBL_SINCE + dateS;
	}

	public static PUK requestPuk4(String to) {

		return Xkomm2Api.apiGetContact4Email(to).getKeyPair().getPuk();
	}

	public static boolean[] requestContactStatuses() {

		return Xkomm2Api.apiGetContactStatuses();
	}

	public static String[] requestContactEmails() {

		return Xkomm2Api.apiGetContactEmails();
	}

	public static boolean requestOnline4(String contactString) {

		return Xkomm2Api.apiIsOnline(contactString);
	}

	private static String requestInactiveLabel(String contactString) {

		if (contactString.length() > 0) {

			long inactive4 = Xkomm2Api.apiGetContact4Email(contactString).getSecondsInactive() / 60;

			if (inactive4 >= 2) return Constants4Uis.LBL_INACTIVE_4 + inactive4 + Constants4Uis.LBL_MINUTES;

			else return Constants4Uis.EMPTY_STRING;
		}

		return Constants4Uis.EMPTY_STRING;
	}

	public static String requestActivityLabel(String contactString) {

		return Xkomm2Api.apiIsOnline(contactString) ?

				Constants4Uis.LBL_ONLINE + Functions4Contact.requestInactiveLabel(contactString) :

				Constants4Uis.LBL_OFFLINE + requestSinceLabel(contactString);
	}

	public static boolean requestContactsHaveChanged() {

		return Xkomm2Api.apiGetContactsHaveChanged();
	}
}
