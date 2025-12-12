package org.larssentech.xkomm2.server.uids;

import java.util.ArrayList;
import java.util.List;

public class MessageUids {

	private static final List<String> list = new ArrayList<String>();

	public static boolean containsUid(String uid) {

		return list.contains(uid);

	}

	public static void addUid(String uid) {

		list.add(uid);
	}

}
