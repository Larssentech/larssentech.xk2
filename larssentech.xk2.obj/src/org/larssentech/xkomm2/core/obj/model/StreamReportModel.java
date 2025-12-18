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

import java.util.Vector;

import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.core.obj.objects.StreamProgressLogContainer;

public class StreamReportModel {

	private final StreamProgressLogContainer downloadProgressLogs = new StreamProgressLogContainer();
	private final StreamProgressLogContainer uploadProgressLogs = new StreamProgressLogContainer();

	public void insertNextProgress2UploadLog4(String to, String logText) {

		/*
		 * If the receiver of the upload already has a vector (i.e. no the first
		 * packet), retrieve it and add the new message
		 */
		if (this.uploadProgressLogs.containsKey(to)) this.uploadProgressLogs.get(to).addElement(logText);

		/*
		 * If this is the first packet, then create the vector for the contact to whom
		 * the upload is intended.
		 */
		else {

			Vector<String> v = new Vector<String>();
			v.addElement(logText);
			this.uploadProgressLogs.put(to, v);
		}

		/*
		 * Print to terminal, just to be sure
		 */
		Logger.log(logText + " ");
	}

	public String getNextUploadProgressFromLog4(String to) {

		String returnString = "";

		if (this.uploadProgressLogs.containsKey(to)) {

			Vector<String> v = this.uploadProgressLogs.get(to);

			if (v.size() > 0) {

				returnString = v.elementAt(0).toString();
				v.removeElementAt(0);
			}
		}
		return returnString;
	}

	public void insertDownloadProgress2Log4(String to, String logText) {

		if (this.downloadProgressLogs.containsKey(to)) this.downloadProgressLogs.get(to).addElement(logText);

		else {

			Vector<String> v = new Vector<String>();
			v.addElement(logText);
			this.downloadProgressLogs.put(to, v);
		}

		Logger.log(logText + " ");
	}

	public String getNextDownloadProgressFromLog4(String to) {

		String returnString = "";

		if (this.downloadProgressLogs.containsKey(to)) {

			Vector<String> v = this.downloadProgressLogs.get(to);

			if (v.size() > 0) {

				returnString = v.elementAt(0).toString();
				v.removeElementAt(0);
			}
		}
		return returnString;
	}
}