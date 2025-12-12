/*
 * Copyright 2014-2023 Larssentech Developers
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
package org.larssentech.xkomm2.server.service;

import java.io.IOException;

import org.larssentech.lib.basiclib.net.SocketBundle;
import org.larssentech.xkomm.core.obj.objects.RequestBundle;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.crypto.CryptoFunctions;

public class LoginProcessor implements CommonServerConstants {

	static void processLogin(RequestBundle requestBundle, SocketBundle sb) throws ClassNotFoundException, IOException {

		if (requestBundle.isComplete()) {

			AccountFunctions.getId4User(requestBundle);

			if (requestBundle.getUserId().equals(BAD_ID)) new Processor4Request(sb).new Processor4Account().processCandidate(requestBundle);

			// TO MIGRATE OLDER PASSWORDS
			AccountFunctions.getPassword4User(requestBundle);

			if (requestBundle.getDbEncPass().length() < 100) AccountFunctions.setPassword4User(requestBundle);

			// Decrypt the password with server PK and verify with client PUK
			CryptoFunctions.decryptPassword(requestBundle);

			// This password is encrypted for us, the server, and signed with
			// the PK the client used at that time.
			CryptoFunctions.decryptDBPassword(requestBundle);

			// If both passwords match
			if (requestBundle.getDbPlainPass().equals(requestBundle.getPlainPass())) {

				String userId = AccountFunctions.loginSetOnline(requestBundle);

				requestBundle.setUserId(userId);

				/**
				 * This is where we jump to the RequestParser
				 */
				if ((null != userId && userId.length() > 0)) new RequestParser(sb, requestBundle).parseRequest();
			}

			else {
				Logger.pl("__USER_BLOCKED__: User " + requestBundle.getUserId() + " is known, but RSA keys are wrong");
				sb.close();
			}

		}

	}

}
