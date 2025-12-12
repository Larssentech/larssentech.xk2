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
package org.larssentech.xkomm2.ui.shared.util;

import java.awt.Color;

import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class Xkomm2Theme {

	private static Color background = Constants4Uis.DEF_BACKGROUND;
	private static Color foreground = Constants4Uis.DEF_FOREGROUND;

	private static Color msgIn = Constants4Uis.DEF_MSG_IN_COLOUR;
	private static Color msgOut = Constants4Uis.DEF_MSG_OUT_COLOUR;

	private static Color online = Constants4Uis.DEF_ONLINE;
	private static Color offline = Constants4Uis.DEF_OFFLINE;

	private static Color grayedOut = Constants4Uis.DEF_GRAYEDOUT_COLOUR;
	private static Color grayedOutText = Constants4Uis.DEF_GRAYED_OUT_TEXT;

	private static Color border = Constants4Uis.DEF_BORDER;
	private static Color selection = Constants4Uis.SELECTION;
	private static Color disabledColorText = Constants4Uis.DISABLED_COLOuR_TEXT;

	public static Color getBackground() {

		return background;
	}

	public static void setBackground(Color background) {

		Xkomm2Theme.background = background;
	}

	public static Color getForeground() {

		return foreground;
	}

	public static void setForeground(Color foreground) {

		Xkomm2Theme.foreground = foreground;
	}

	public static Color getMsgIn() {

		return msgIn;
	}

	public static void setMsgIn(Color msgIn) {

		Xkomm2Theme.msgIn = msgIn;
	}

	public static Color getMsgOut() {

		return msgOut;
	}

	public static void setMsgOut(Color msgOut) {

		Xkomm2Theme.msgOut = msgOut;
	}

	public static Color getGrayedOut() {

		return grayedOut;
	}

	public static void setGrayedOut(Color grayedOut) {

		Xkomm2Theme.grayedOut = grayedOut;
	}

	public static Color getOffline() {

		return offline;
	}

	public static void setOffline(Color offline) {

		Xkomm2Theme.offline = offline;
	}

	public static Color getOnline() {

		return online;
	}

	public static void setOnline(Color online) {

		Xkomm2Theme.online = online;
	}

	public static Color getBorder() {

		return border;
	}

	public static void setBorder(Color border) { Xkomm2Theme.border = border; }

	public static Color getGrayedOutText() {

		return grayedOutText;
	}

	public static Color getSelection() {

		return selection;
	}

	public static void setGrayedOutText(Color grayedOutText) { Xkomm2Theme.grayedOutText = grayedOutText; }

	public static void setSelection(Color selection) { Xkomm2Theme.selection = selection; }

	public static Color getDisabledColorText() {

		return disabledColorText;
	}

	public static void setDisabledColorText(Color disabledColorText) {

		Xkomm2Theme.disabledColorText = disabledColorText;
	}

	public static Color getLightBorder() {

		return grayedOutText;
	}

	public static Color getOfflineTemp() {
		
		return Color.orange;
	}
}
