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

package org.larssentech.xkomm2.api.util;

import org.larssentech.xkomm.core.obj.constants.Constants4API;

public interface ApiReg extends Constants4API {

	String T_VERSION = "0.1.0";
	String DISPLAY_FOLDER = Constants4API.USER_HOME + Constants4API.SEP + "display";
	String APP_NAME = "XK2 - Larssentech XKomm2";
	String USER_FILE_PATH = Constants4API.USER_HOME + Constants4API.SEP + Constants4API.USER_FILE2;
}