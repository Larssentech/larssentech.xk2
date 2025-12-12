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

package org.larssentech.xkomm2.api.impl.crypto;

import org.larssentech.CTK.driver.EmbeddedApi;
import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.basiclib.toolkit.StringManipulationToolkit;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class CtkApiImpl implements NetworkConstants {

	public CtkApiImpl() {

		new EmbeddedApi();
	}

	public static void decodeMessage(Message message) {

		if (message.isDelivered()) {

			message.setBodyBytes(CtkApiImpl.decVer(message.getBodyBytes(), message.getFrom().getKeyPair().getPuk()));
			message.setBodyBytes(decode(message.getBodyBytes()));
			message.setGood(true);
		}
	}

	public static void encodeMessage(User user, Message message) {

		message.setBodyBytes(encode(message.getBodyBytes()));
		message.setBodyBytes(CtkApiImpl.encSign(message.getBodyBytes(), user.getKeyPair().getPuk()));

		message.setGood(true);
	}

	private static byte[] decode(byte[] body) {

		return StringManipulationToolkit.HTMLDecodeString(new String(body)).getBytes();
	}

	private static byte[] encode(byte[] body) {

		return StringManipulationToolkit.HTMLEncodeString(new String(body)).getBytes();
	}

	private static byte[] encSign(byte[] plainText, PUK contactPuk) {

		return EmbeddedApi.encryptSignMessage(plainText, contactPuk);
	}

	private static byte[] decVer(byte[] cipherText, PUK contactPuk) {

		return EmbeddedApi.decryptVerifyMessage(cipherText, contactPuk);
	}

	public static byte[] blowfishEncrypt(byte[] plainBinaryBlock, long bytesDone, PUK contactPuk) {

		return EmbeddedApi.encryptBlowfish(plainBinaryBlock, bytesDone, contactPuk);
	}

	public static byte[] blowfishDecrypt(byte[] encryptedBytes, PUK puk) {

		return EmbeddedApi.decryptBlowfish(encryptedBytes, puk);
	}

	public static String encPass4Server(String plainPass) {

		return new String(CtkApiImpl.encSign(plainPass.getBytes(), Xkomm2Api.apiGetServerPuk()));
	}

	public static String encPass4Me(String plainPass) {

		return new String(CtkApiImpl.encSign(plainPass.getBytes(), Xkomm2Api.apiLoadPuk4Me()));
	}

	public static String decPass4Me(String encPass) {

		return new String(CtkApiImpl.decVer(encPass.getBytes(), Xkomm2Api.apiLoadPuk4Me()));
	}
}