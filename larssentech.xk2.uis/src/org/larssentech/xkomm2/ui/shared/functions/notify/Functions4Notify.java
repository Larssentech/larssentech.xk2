/*
 * Copyright 2014-2024 Larssentech Developers
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
package org.larssentech.xkomm2.ui.shared.functions.notify;

import java.io.IOException;

public class Functions4Notify {

	public static void show(String string) {

		if (System.getProperty("os.version").startsWith("10.4") || System.getProperty("os.version").startsWith("10.5")) showLegacyNotification(string);

		// NOt sure yet why, but this overloads the system tray and f!cks up
		// receiving messages, so rolling back.
		if (System.getProperty("os.name").startsWith("Windows 10")) notify4Windows(string);
		if (System.getProperty("os.name").indexOf("Linux") != -1) notify4Linux(string);
		else showNotification(string);
	}

	private static void notify4Linux(String string) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method to run a 'plug-in' for Windows 10 only and do the SystemTray
	 * message Windows does when events take place and the user wants to know
	 * 
	 * @param message
	 */
	private static void notify4Windows(String message) {

		/*
		 * try { Class<?> clazz = Class.forName(
		 * "org.larssentech.xkomm2.ui.shared.win10.functions.notify" + "." +
		 * "Notify4Windows");
		 * 
		 * Method method = clazz.getMethod("showNotification", String.class);
		 * 
		 * method.invoke(null, message);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	private static void showLegacyNotification(String string) {

		Notify4Legacy.newMessageSound(string);
	}

	private static void showNotification(String string) {

		try {

			Runtime.getRuntime()
					.exec(new String[] { "osascript", "-e", "display notification \"Message from " + string + "\" with title \"XK2J\" subtitle \"You have messages\" sound name \"Funk\"" });
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}
}