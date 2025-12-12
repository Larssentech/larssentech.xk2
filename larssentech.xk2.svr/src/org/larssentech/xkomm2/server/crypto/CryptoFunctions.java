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

package org.larssentech.xkomm2.server.crypto;

import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.larssentech.CTK.driver.EmbeddedApi;
import org.larssentech.CTK.settings.RSAPathBundle;
import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.basiclib.io.Base64ObjectCoder;
import org.larssentech.lib.basiclib.io.file.ReadBytesFromFile;
import org.larssentech.xkomm.core.obj.objects.RequestBundle;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.dao.GeneralDAO;
import org.larssentech.xkomm2.server.settings.SQLStatements;

public class CryptoFunctions implements CommonServerConstants {

	public static String decryptVerifyPassword(RequestBundle requestBundle) {

		String base64Puk = ((String[]) GeneralDAO.genericGet(SQLStatements.getUserPubKey, new String[] { requestBundle.getUserId() }).get(0))[0];

		// String altKey = requestBundle.getPuk();

		PUK puk = new PUK(new String(base64Puk.getBytes()));

		return new String(EmbeddedApi.decryptVerifyMessage(requestBundle.getEncPass().getBytes(), puk));
	}

	public static void decryptVerifyEncPassword(RequestBundle requestBundle) {

		String base64Puk = requestBundle.getPuk();

		PUK puk = new PUK(new String(base64Puk.getBytes()));

		requestBundle.setPlainPass(new String(EmbeddedApi.decryptVerifyMessage(requestBundle.getEncPass().getBytes(), puk)));
	}

	public static String getServerPUKValueB64() {

		String puk = "FAILED TO RETRIEVE ADMIN_SERVER HEADER_KEY_PUK";

		try {

			List<String[]> list = GeneralDAO.genericGet(SQLStatements.getUserPubKey, new String[] { "184" });

			puk = list.get(0)[0];

		}
		catch (Exception e) {

			e.printStackTrace();
		}
		return puk;
	}

	public static void startServerCtk() {

		EmbeddedApi.init(true);

		String puk = readServerPuk(RSAPathBundle.getOwnPUKPath());

		Logger.p("Crypto:" + "\t\t");
		Logger.pl("Auto updating server HEADER_KEY_PUK (" + SERVER_ID + ")");

		GeneralDAO.genericSet(SQLStatements.updateUserPubKey, new String[] { puk, SERVER_ID });
	}

	private static String readServerPuk(String ownPUKPath) {

		byte[] puKB = ReadBytesFromFile.readBytesFromFile(ownPUKPath);

		byte[] puK64 = new byte[0];

		if (puKB.length > 0)

			try {

				puK64 = Base64ObjectCoder.encodeBytesAndChunk(puKB);

			}
			catch (EncoderException e) {

				e.printStackTrace();
			}

		return new String(puK64);
	}

	public static void decryptPassword(RequestBundle requestBundle) {

		String decPass = "";

		try {

			decPass = CryptoFunctions.decryptVerifyPassword(requestBundle);
			requestBundle.setPlainPass(decPass);
		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void decryptEncPassword(RequestBundle requestBundle) {

		try {

			CryptoFunctions.decryptVerifyEncPassword(requestBundle);

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void decryptDBPassword(RequestBundle requestBundle) {

		String base64Puk = ((String[]) GeneralDAO.genericGet(SQLStatements.getUserPubKey, new String[] { requestBundle.getUserId() }).get(0))[0];

		PUK puk = new PUK(new String(base64Puk.getBytes()));

		requestBundle.setDBPlainPass(new String(EmbeddedApi.decryptVerifyMessage(requestBundle.getDbEncPass().getBytes(), puk)));
	}
}