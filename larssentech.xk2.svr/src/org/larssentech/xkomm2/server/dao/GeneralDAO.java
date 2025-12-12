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
package org.larssentech.xkomm2.server.dao;

import java.util.List;

import org.larssentech.lib.basiclib.dao.ConnectionPack;
import org.larssentech.lib.basiclib2.dao.QueryDAO;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;

public class GeneralDAO implements CommonServerConstants {

	static ConnectionPack cPack = new ConnectionPack(DRIVER_NAME, DB_HOST, Integer.parseInt(DB_PORT), USER_SCHEMA, USER_USER, USER_USER_PASSWORD);

	public static boolean genericSet(String query, String[] x) {

		return QueryDAO.setXinYwhereZ(cPack, query, x);
	}

	public static List<String[]> genericGet(String query, String[] x) {

		return QueryDAO.getXfromYwhereZ(cPack, query, x);
	}
}