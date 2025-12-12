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
package org.larssentech.xkomm2.api.impl.crypto;

import org.apache.commons.codec.EncoderException;
import org.larssentech.CTK.settings.RSAPathBundle;
import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.basiclib.io.Base64ObjectCoder;
import org.larssentech.lib.basiclib.io.file.ReadBytesFromFile;
import org.larssentech.lib.basiclib.toolkit.ByteArrayToolkit;

public class CipherApiImpl {

	private static PUK serverPuk;

	private static String loadPUK4Me() {

		byte[] puK = ReadBytesFromFile.readBytesFromFile(RSAPathBundle.getOwnPUKPath());
		byte[] puKS = new byte[0];

		if (puK.length > 0)

			try {

				puKS = Base64ObjectCoder.encodeBytesAndChunk(puK);
			}
			catch (EncoderException e) {

				e.printStackTrace();
			}

		return new String(puKS);
	}

	public static PUK getPuk4Me() {

		return new PUK(loadPUK4Me());
	}

	public static PUK getPuk4Server() {

		return serverPuk;
	}

	public static void setServerPuk(PUK serverPuk) {

		CipherApiImpl.serverPuk = serverPuk;
	}

	public static byte[] shrink(byte[] bytes, long usedLength, int arrayLength) {

		// Last chunk will normally be smaller
		if (usedLength < arrayLength) return ByteArrayToolkit.shrinkArray(bytes, (int) usedLength);
		return bytes;
	}
}