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

import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class Functions4Common {

	public static int requestInboxCountFrom(String string) {

		return Xkomm2Api.apiGetInboxCountFrom(string);
	}

	@Deprecated
	public static boolean requestSpotCheckNetworkAvailable() {

		return Xkomm2Api.apiHaveNetwork();
	}

	public static String requestMeEmail() {

		return Xkomm2Api.apiGetMe().getLogin().getEmail();
	}
}