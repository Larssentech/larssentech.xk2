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

import java.util.Vector;

/**
 * This is used for the data packet sending functions We want to always
 * serialise the streams, with each next packet only going out when the previous
 * one has been successfully transmitted
 * 
 * This locks sending data to a single user ID as a serial stream, multiple user
 * IDs can be streamed to in parallel. This is for keeping the right sequence of
 * messages as sent.
 * 
 * @author jeff.cerasuolo
 * 
 */
public abstract class ServerLock {

	private static Vector<String> v = new Vector<String>();

	public static boolean release(String id) {

		if (v.contains(id)) v.remove(id);
		return !v.contains(id);
	}

	public static boolean engage(String id) {

		if (!v.contains(id)) v.addElement(id);
		return v.contains(id);
	}

	public static boolean locked(String id) {

		return v.contains(id);
	}
}