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
package org.larssentech.xkomm2.api.impl.system;

import java.io.File;

import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm.core.obj.constants.Constants4Stream;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.core.obj.objects.StreamHeader;

public class UpdateApiImpl {

	private static boolean update;

	public static boolean isUpdate() {

		return update;
	}

	public static void setUpdate(boolean b) {

		update = b;
	}

	public static void handleJarUpdate(File file, StreamHeader streamHeader) {

		if (file.getAbsolutePath().endsWith(Constants4API.XKOMM_JAR) || file.getAbsolutePath().endsWith(Constants4API.XKOMM_JM_JAR) || file.getAbsolutePath().endsWith(Constants4API.XKOMM_K_JAR)) {

			Logger.pl(Constants4Stream.DATA_BLOCKS + streamHeader.getBlockNum() + Constants4Stream.OF + streamHeader.getNumBlocks());

			if (streamHeader.getBlockNum() == streamHeader.getNumBlocks() - 1) {

				// Give the GUI time to catch up
				try {

					Thread.sleep(5000);
				}
				catch (InterruptedException e) {

				}

				Xkomm2Api.apiSetUpdateFound(true);
			}
		}

	}
}