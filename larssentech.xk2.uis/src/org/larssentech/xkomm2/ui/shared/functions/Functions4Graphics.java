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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.TextField;
import java.io.File;

import org.larssentech.lib.awtlib.DisplayTool2;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.chat.ChatRoomMan;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class Functions4Graphics {

	private static void requestSetMainFrameSize(Frame frame) {

		Dimension dim = DisplayTool2.readDimension(Constants4Uis.MAIN_FRAME_DISPLAY);

		if (dim.width == 0 && dim.height == 0) frame.setSize(Constants4Uis.DEFAULT_WIDTH, Constants4Uis.DEFAULT_HEIGHT);

		else frame.setSize(dim);
	}

	public static void requestSaveMainFrame(Frame frame) {

		DisplayTool2.saveDimension(Constants4Uis.MAIN_FRAME_DISPLAY, frame.getSize());
		DisplayTool2.saveLocation(Constants4Uis.MAIN_FRAME_DISPLAY, frame.getLocation());
	}

	private static void requestLoadMainFrameLocation(Frame frame) {

		Point location = DisplayTool2.readLocation(Constants4Uis.MAIN_FRAME_DISPLAY);
		frame.setLocation(location);

	}

	public static void requestLoadSavedMainFrame(Frame frame) {

		Functions4Graphics.requestLoadMainFrameLocation(frame);
		Functions4Graphics.requestSetMainFrameSize(frame);

	}

	private static Point requestChatRoomLocation(String to) {

		if (!new File(Constants4Uis.DISPLAY_FOLDER).exists()) new File(Constants4Uis.DISPLAY_FOLDER).mkdir();

		Point location = DisplayTool2.readLocation(Constants4Uis.DISPLAY_FOLDER + Constants4Uis.SEP + to);
		return (null == location) ? new Point(Constants4Uis.DEFAULT_X_LOC, Constants4Uis.DEFAULT_Y_LOC) : location;
	}

	private static Dimension requestChatRoomDimension(final String to) {

		if (!new File(Constants4Uis.DISPLAY_FOLDER).exists()) new File(Constants4Uis.DISPLAY_FOLDER).mkdir();

		Dimension size = DisplayTool2.readDimension(Constants4Uis.DISPLAY_FOLDER + Constants4Uis.SEP + to);
		if (null == size || size.width == 0) return new Dimension(Constants4Uis.DEFAULT_WIDTH, Constants4Uis.DEFAULT_HEIGHT);

		return size;
	}

	public static void requestSaveChatFrame(Frame owner, String key) {

		DisplayTool2.saveLocation(Constants4Uis.DISPLAY_FOLDER + Constants4Uis.SEP + key, owner.getLocation());
		DisplayTool2.saveDimension(Constants4Uis.DISPLAY_FOLDER + Constants4Uis.SEP + key, owner.getSize());
	}

	public static void requestRecycleFrame(String key, Frame frame) {

		ChatRoomMan.recycle(frame, key);

	}

	public static void requestRefreshDisplay(final TextField toF) {

		if (!Xkomm2Api.apiIsContact(toF.getText())) toF.setForeground(Constants4Uis.DEF_GRAYEDOUT_COLOUR);

		else toF.setForeground(Xkomm2Api.apiIsOnline(toF.getText().toLowerCase()) ? Xkomm2Theme.getOnline() : Xkomm2Theme.getOffline());
	}

	public static void requestLoadSavedChatFrame(String to, Frame frame) {

		frame.setLocation(requestChatRoomLocation(to));
		frame.setSize(requestChatRoomDimension(to));

	}
}