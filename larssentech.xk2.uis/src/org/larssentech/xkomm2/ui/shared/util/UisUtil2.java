package org.larssentech.xkomm2.ui.shared.util;

import org.larssentech.xkomm.core.obj.constants.Constants4Stream;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;

public class UisUtil2 extends org.larssentech.xkomm.ui.shared.util.UisUtil {

	public static boolean canSendStream(String to, String fileName) {

		// Check with the Messaging API if we can stream it...
		int check = Xkomm2Api.apiCanStream(to, fileName);

		// If we can, call the streaming method, alert otherwise
		if (check == Constants4Stream.GOOD_FILE || check == Constants4Stream.OFFLINE_SMALL_FILE) return true;

		return false;

	}

}
