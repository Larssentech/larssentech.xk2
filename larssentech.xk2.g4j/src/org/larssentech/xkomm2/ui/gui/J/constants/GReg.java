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

package org.larssentech.xkomm2.ui.gui.J.constants;

import java.awt.Color;

import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm2.core.obj.version.Version4Xk2;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public interface GReg extends Constants4Uis, Version4Xk2 {
	int CHAT_THREAD_SLEEP = 500;
	String TITLE_J = "XK2J";

	String FRAME4MAIN_TITLE_VERSION = TITLE_J + " / v" + BASE_VERSION_STRING + " / " + J_VERSION;

	public String MAIN_FRAME_DISPLAY = Constants4API.USER_HOME + Constants4API.SEP + "display.data";
	public String DISPLAY_FOLDER = Constants4API.USER_HOME + Constants4API.SEP + "display";

	long FRAME4MAIN_SLEEP_FAST = 1000;

	Color BACKGROUND_COLOR = new Color(48, 47, 47);
	Color FOREGROUND_COLOR = Color.white;

	Color ONLINE_COLOR = Color.green;
	Color OFFLINE_COLOR = Color.red;
	Color DISABLED_COLOR_TEXT = Color.GRAY;

	Color GRAYED_OUT_TEXT = Color.lightGray;
	Color GRAYEDOUT_COLOUR = Color.GRAY;

	Color SELECTION = Color.DARK_GRAY;
	Color BORDER_COLOR = Color.DARK_GRAY;

	int HISTORY_LINES = 50;

	String PROGRESS_TOKEN = "%";
	String MSG_STREAM_SEND = " (Sending)";
	String MSG_STREAM_RECEIVE = " (Receiving)";
	String SUSPENDED = "Suspended...";
	int OFFLINES_MAX = 10;
	String MAIN_THREAD = "xkomm.thread.mainframe";
	String MENU_LICENSE = "License";
	String ACTION_LICENSE = "ACTION_LICENSE";

}