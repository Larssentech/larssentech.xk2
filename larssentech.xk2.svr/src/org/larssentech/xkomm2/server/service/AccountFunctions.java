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

import java.util.List;

import org.larssentech.xkomm.core.obj.objects.RequestBundle;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.dao.GeneralDAO;
import org.larssentech.xkomm2.server.settings.SQLStatements;

public class AccountFunctions implements CommonServerConstants {

	public static void getId4Candidate(RequestBundle requestBundle) {

		List<String[]> list = GeneralDAO.genericGet(SQLStatements.candidateQuery, new String[] { requestBundle.getEmail() });

		if (list.size() == 0) requestBundle.setUserId(BAD_ID);

		else requestBundle.setUserId(((String[]) list.get(0))[0].toString());
	}

	public static void getId4User(RequestBundle requestBundle) {

		List<String[]> list = GeneralDAO.genericGet(SQLStatements.retrieveIDForEmailQuery, new String[] { requestBundle.getEmail() });

		if (list.size() == 0) requestBundle.setUserId(BAD_ID);

		else requestBundle.setUserId(((String[]) list.get(0))[0].toString());
	}

	public static String loginSetOnline(RequestBundle requestBundle) {

		String id = ((String[]) GeneralDAO.genericGet(SQLStatements.loginQuery, new String[] { requestBundle.getEmail() }).get(0))[0].toString();

		GeneralDAO.genericSet(SQLStatements.setOnlineQuery, new String[] { requestBundle.getMode(), requestBundle.secondsInactive(), id });

		return id;
	}

	public static void createUser(RequestBundle requestBundle) {

		GeneralDAO.genericSet(SQLStatements.createNewUser, new String[] { requestBundle.getEmail(), requestBundle.getEncPass() });

		AccountFunctions.getId4User(requestBundle);
	}

	public static void getPassword4User(RequestBundle requestBundle) {

		List<String[]> list = GeneralDAO.genericGet(SQLStatements.getPasswordQuery, new String[] { requestBundle.getEmail() });

		String pass = ((String[]) list.get(0))[0].toString();

		requestBundle.setDBEncPassword(pass);

	}

	public static void setPassword4User(RequestBundle requestBundle) {

		GeneralDAO.genericSet(SQLStatements.updatePassword, new String[] { requestBundle.getEncPass(), requestBundle.getEmail() });

		AccountFunctions.getId4User(requestBundle);

	}
}