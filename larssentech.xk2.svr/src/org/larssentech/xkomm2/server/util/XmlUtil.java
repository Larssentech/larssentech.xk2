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
package org.larssentech.xkomm2.server.util;

import org.larssentech.lib.basiclib.io.parser.XMLParser;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;

public class XmlUtil {

	public static String getRequestName(String xml) {

		String requestName = "";

		requestName = XMLParser.parseValueForTag(xml, NetworkConstants.XML_REQUEST_NAME_O);

		return requestName;
	}

}
