/*
 * Copyright 2014-2022 Larssentech Developers
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
package org.larssentech.xkomm2.ui.shared.chat;

import java.awt.Frame;
import java.util.Enumeration;

public class ChatRoomMan extends org.larssentech.xkomm.ui.shared.chat.ChatRoomMan {

	public static void unhideAllRegisteredFrames() {

		@SuppressWarnings("unchecked")
		Enumeration<Frame> enu = FrameStore.getStore().elements();
		while (enu.hasMoreElements()) enu.nextElement().setVisible(true);
	}

	public static void hideAllRegisteredFrames() {

		@SuppressWarnings("unchecked")
		Enumeration<Frame> enu = FrameStore.getStore().elements();
		while (enu.hasMoreElements()) enu.nextElement().setVisible(false);
	}
}