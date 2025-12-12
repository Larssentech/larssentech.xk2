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
package org.larssentech.xkomm2.core.obj.model;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Hashtable for multiple logs, each is is keyed on the sender/receiver String
 * mapped to a Vector. Each Vector contains String entries with progress for the
 * streaming activity.
 * 
 * @author jcer
 * 
 */
public class StreamProgressLogContainer extends Hashtable<String, Vector<String>> {

}
