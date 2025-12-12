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

package org.larssentech.xkomm2.api.Sys.constants;

public interface SysConstants {

	String SYS_COMMAND = "$";

	String G_SAY = "say ";

	// V_SYS COMMANDS
	String G_VERSION_NO = "version";

	String G_SPC = " ";

	String G_INV_CMD = "Invalid $ Command";
	String G_XTRA_INV_CMD = "Xtra command not understood...";
	String G_SYSTEM_INFO = "sys_info";
	String TAB = "\t";

	String SYSTEM_INFO = "System info:" + "\n" +

			"Java Vendor:" + TAB + System.getProperty("java.vendor") + "\n" +

			"Java Version:" + TAB + System.getProperty("java.version") + "\n" +

			"OS Arch.:" + TAB + System.getProperty("os.arch") + "\n" +

			"OS Name:" + TAB + System.getProperty("os.name") + "\n" +

			"OS Version:" + TAB + System.getProperty("os.version") + "\n";
	/*
	 * String SYSTEM_INFO = "System info:" + TAB + "Java Vendor:" + TAB +
	 * System.getProperty("java.vendor") + TAB + "Java Version:" + TAB +
	 * System.getProperty("java.version") + TAB + "OS Arch.:" + TAB +
	 * System.getProperty("os.arch") + TAB + "OS Name:" + TAB +
	 * System.getProperty("os.name") + TAB + "OS Version:" + TAB +
	 * System.getProperty("os.version") + TAB + "Server URL:" + TAB +
	 * SettingsExtractor.extractThis4("HOSTS.ini", "USER_SERVER") + TAB +
	 * "Server Port:" + TAB + SettingsExtractor.extractThis4("HOSTS.ini",
	 * "USER_PORT") + TAB;
	 */
	String XTRA_COMMAND = "@";

}
