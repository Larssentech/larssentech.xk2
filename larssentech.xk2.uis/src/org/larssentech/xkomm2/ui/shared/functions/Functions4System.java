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

import java.awt.Frame;

import org.larssentech.lib.awtlib.Alert;
import org.larssentech.lib.basiclib.settings.SettingsExtractor;
import org.larssentech.lib.basiclib.settings.SettingsUpdater;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class Functions4System {

	public static void requestLogError(Exception e) {

		Xkomm2Api.apiLogError(e);
	}

	public static void requestInitialiseBundle() {

		new Functions4Inactivity().start();
		new Functions4Lock(Functions4System.getLockLimit());
	}

	public static boolean requestUpdateFound() {

		return Xkomm2Api.apiIsUpdateFound();
	}

	public static void requestLock(final Frame frame) {

		if (Xkomm2Api.apiGetMe().isInactive() && Functions4Lock.isScreenLockNeeded()) Functions4Lock.lockNow(frame);
	}

	public static void requestSuspend(boolean b) {

		Xkomm2Api.apiPauseMonitor(b);
	}

	public static void requestRestart() {

		Xkomm2Api.apiRestart();
	}

	public static void requestFirestarter() {

		new Alert().showMessage(Constants4Uis.FIRESTARTER, Constants4Uis.I_AM_THE_FIRESTARTER);
	}

	public static void requestExit(int i) {

		Xkomm2Api.apiExit(3);
	}

	public static boolean requestAllAreOffline() {

		return Xkomm2Api.apiAllOffine();
	}

	public static boolean requestTestNetwork(boolean print, boolean exit) {

		return Xkomm2Api.apiTestNetwork(print, exit);
	}

	public static boolean requestHaveNetwork() {

		return Xkomm2Api.apiHaveNetwork();
	}

	public static void requestLoggingEnabled(boolean b) {

		Xkomm2Api.apiSetLoggingEnabled(b);
	}

	static int getLockLimit() {

		if (SettingsExtractor.extractThis4(Constants4Uis.USER_FILE_PATH, Constants4Uis.SET_SCREEN_LOCK_LIMIT).length() == 0) SettingsUpdater.updateLine(Constants4Uis.USER_FILE_PATH, Constants4Uis.SET_SCREEN_LOCK_LIMIT, Constants4Uis.DEF_SCREEN_LOCK_LIMIT);

		return Integer.parseInt(SettingsExtractor.extractThis4(Constants4Uis.USER_FILE_PATH, Constants4Uis.SET_LOCK_SCREEN_TIMEOUT));
	}

	static String loadMyEncPassword() {

		// TODO Auto-generated method stub
		return SettingsExtractor.extractThis4(Constants4Uis.USER_FILE_PATH, Constants4Uis.SET_PASSWORD_FIELD);
	}
}