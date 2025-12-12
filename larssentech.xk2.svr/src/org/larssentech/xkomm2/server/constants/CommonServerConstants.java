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

package org.larssentech.xkomm2.server.constants;

import org.larssentech.lib.basiclib.settings.SettingsExtractor;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;

public interface CommonServerConstants extends NetworkConstants {

	String VERSION = "v0.11.0.2";
	String USER_SERVER_COPY = "XK2S ";

	// COMMON
	String LINER = "-------------------------------";
	String OWN_PRI_K_NAME = "NX_RSA_PRI_KEY";
	String OWN_PUB_K_NAME = "NX_RSA_PUB_KEY";
	String HOME_DIR = System.getProperty("user.home");
	String SEP = System.getProperty("file.separator");

	// DB Settings
	String DB_HOST = SettingsExtractor.extractThis4("server.data", "XKomm.server");
	String DRIVER_NAME = SettingsExtractor.extractThis4("server.data", "XKomm.driverName");
	String DB_PORT = SettingsExtractor.extractThis4("server.data", "XKomm.port");

	// USER SERVER
	String USER_OWN_RSA_DIR = ".xkserver_rsa";
	String USER_OWN_KEYPAIR_ABS_PATH = HOME_DIR + SEP + USER_OWN_RSA_DIR + SEP;
	String USER_OWN_PRI_K_ABS_PATH = HOME_DIR + SEP + USER_OWN_RSA_DIR + SEP + OWN_PRI_K_NAME;
	String USER_OWN_PUB_K_ABS_PATH = HOME_DIR + SEP + USER_OWN_RSA_DIR + SEP + OWN_PUB_K_NAME;

	String USER_SCHEMA = "xkomm";
	String USER_USER = "xkomm";
	String USER_USER_PASSWORD = "7q+P#mnL;#3^";

	int SO_TIMEOUT = 15000;

	// SERVER FILE
	String SERVER_DATA = "server.data";
	String PORT = "XKomm.netport";

	// CONFIG AND STRING VALUES
	String NO_PUBKEY = "NO_PUBKEY";
	String SETTINGS_BUNDLE = "org.larssentech.xkomm2.server.settings.xksettings";
	String SERVER_ID = "184";

	String OK = "OK";
	String ERROR = "ERROR";
	String INVITATION = "INVITATION: ";
	String DOT_1 = ".";
	String DOT_2 = "..";
	String LINE_1_OUT = "Larssentech XK2S";
	String EMPTY_STRING = "";
	String CLIENT_LINE_ENDER = "<end>";

	String INVITATION_MSG = " invited you to be their contact. " + "To accept, send them an invite using the menus above. " + "Make sure you type their login correctly!";
}