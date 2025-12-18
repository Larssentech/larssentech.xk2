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

import java.io.File;

import org.larssentech.CTK.driver.EmbeddedApi;
import org.larssentech.CTK.settings.CTKSettings;
import org.larssentech.CTK.settings.RSAPathBundle;
import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.lib.basiclib.toolkit.StringManipulationToolkit;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class CtkApiImpl implements NetworkConstants, CTKSettings {

	private final EmbeddedApi ctk;

	public CtkApiImpl() {

		RSAPathBundle.setOwnPKPath(OWN_PRI_K_ABS_PATH);
		RSAPathBundle.setOwnPUKPath(OWN_PUB_K_ABS_PATH);
		RSAPathBundle.setOwnKeyPairPath(OWN_KEYPAIR_ABS_PATH);
		RSAPathBundle.setCipherString("RSA");

		// For out contact library
		new File(CTKSettings.HOME_DIR + CTKSettings.SEP + "nxrsa_pub_key_lib").mkdir();
		new File(CTKSettings.HOME_DIR + CTKSettings.SEP + CTKSettings.CTK_HOME).mkdir();
		new File(CTKSettings.HOME_DIR + CTKSettings.SEP + CTKSettings.OWN_RSA_DIR).mkdir();

		this.ctk = new EmbeddedApi();
	}

	public void decodeMessage(Message message) {

		if (message.isDelivered()) {

			message.setBodyBytes(this.decVer(message.getBodyBytes(), message.getFrom().getKeyPair().getPuk()));
			message.setBodyBytes(decode(message.getBodyBytes()));
			message.setGood(true);
		}
	}

	public void encodeMessage(User user, Message message) {

		message.setBodyBytes(encode(message.getBodyBytes()));
		message.setBodyBytes(this.encSign(message.getBodyBytes(), user.getKeyPair().getPuk()));

		message.setGood(true);
	}

	private static byte[] decode(byte[] body) {

		return StringManipulationToolkit.HTMLDecodeString(new String(body)).getBytes();
	}

	private static byte[] encode(byte[] body) {

		return StringManipulationToolkit.HTMLEncodeString(new String(body)).getBytes();
	}

	private byte[] encSign(byte[] plainText, PUK contactPuk) {

		return this.ctk.encryptSignMessage(plainText, contactPuk);
	}

	private byte[] decVer(byte[] cipherText, PUK contactPuk) {

		return this.ctk.decryptVerifyMessage(cipherText, contactPuk);
	}

	public byte[] blowfishEncrypt(byte[] plainBinaryBlock, long bytesDone, PUK contactPuk) {

		return this.ctk.encryptBlowfish(plainBinaryBlock, bytesDone, contactPuk);
	}

	public byte[] blowfishDecrypt(byte[] encryptedBytes, PUK puk) {

		return this.ctk.decryptBlowfish(encryptedBytes, puk);
	}

	public String encPass4Server(String plainPass) {

		return new String(this.encSign(plainPass.getBytes(), Xkomm2Api.apiGetServerPuk()));
	}

	public String encPass4Me(String plainPass) {

		return new String(this.encSign(plainPass.getBytes(), Xkomm2Api.apiLoadPuk4Me()));
	}

	public String decPass4Me(String encPass) {

		return new String(this.decVer(encPass.getBytes(), Xkomm2Api.apiLoadPuk4Me()));
	}
}