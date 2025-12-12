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
package org.larssentech.xkomm2.ui.shared.constants;

import java.awt.Color;

import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.core.obj.version.Version4Xk2;

public interface Constants4Uis extends Constants4API, Version4Xk2 {

	String SEP = Constants4API.SEP;
	String DISPLAY_FOLDER = Constants4API.USER_HOME + Constants4API.SEP + "display";
	String USER_FILE_PATH = Constants4API.USER_HOME + Constants4API.SEP + Constants4API.USER_FILE2;
	String TITLE_J = "XK2J";
	String FIRESTARTER = "Firestarter...";
	String MSG_RESTART = "XK2J will update and restart now.";

	Color DEF_BACKGROUND = Color.WHITE;
	Color DEF_FOREGROUND = Color.BLACK;

	Color DEF_MSG_OUT_COLOUR = Color.WHITE;
	Color DEF_MSG_IN_COLOUR = Color.decode("#76ff76");

	Color DEF_ONLINE = Color.decode("#00FF00");
	Color DEF_OFFLINE = Color.decode("#FF0000");
	Color DISABLED_COLOuR_TEXT = Color.GRAY;

	Color DEF_GRAYED_OUT_TEXT = Color.lightGray;
	Color DEF_GRAYEDOUT_COLOUR = Color.GRAY;

	Color DEF_BORDER = Color.GRAY;
	Color SELECTION = Color.DARK_GRAY;

	String XK_SETTINGS = HOME + SEP + XKOMM_FOLDER + SEP + "xk.config";
	String LINE_END = "\n"; // Do not move escaped sh!t

	String SYSTEM_INFO = "System info:" + NEW_LINE + "Java Vendor:" + TAB + System.getProperty("java.vendor") + NEW_LINE
			+ "Java Version:" + TAB + System.getProperty("java.version") + NEW_LINE + "OS Arch.:" + TAB
			+ System.getProperty("os.arch") + NEW_LINE + "OS Name:" + TAB + System.getProperty("os.name") + NEW_LINE
			+ "OS Version:" + TAB + System.getProperty("os.version") + NEW_LINE + "Server URL:" + TAB
			+ Xkomm2Api.getServerUrl() + NEW_LINE + "Server Port:" + TAB + Xkomm2Api.getPort() + NEW_LINE;

	/*
	 * Sizes for stuff
	 */
	int ZERO = 0;
	int LOGIN_COLS = 32;
	int TFIELD_SIZE = 25;
	int CHAT_WIDTH = 250;
	int CHAT_HEIGHT = 380;
	int MAIN_WIDTH = 300;
	int MAIN_HEIGHT = 500;
	int INVITE_WIDTH = 280;
	int INVITE_HEIGHT = 130;
	int MEF_WIDTH = 25;
	int LIZT_HEIGHT = 0;
	long FRAME4MAIN_SLEEP_FAST = 1000;
	long MESSAGES_SLEEP = 500;

	/*
	 * Layouts
	 */
	String NORTH = "North";
	String CENTER = "Center";
	String SOUTH = "South";
	String EAST = "East";
	String WEST = "West";

	String CHAT_TITLE = "XK2 / RSA Encrypted";

	/*
	 * Internal Commands
	 */
	String INTERNAL_TOKEN = "$";
	String INTERNAL_CLEAR = "clear";
	String INTERNAL_CLOSE = "close";
	String DELETE_CONTACT = "delete";

	/*
	 * Action Commands
	 */

	String ACTION_CHAT_STREAM = "CHAT_ACTION_STREAM";
	String ACTION_CANCEL_UPLOAD = "CHAT_ACTION_CANCEL_UPLOAD";
	String ACTION_REJECT_DOWNLOAD = "CHAT_ACTION_STOP_DOWNLOAD";

	String MENU_SYSTEM_INFO = "System Info";
	String MENU_ABOUT = "About XKomm2";

	String MENU_EXIT = "Exit";
	String MENU_fILE = "File";

	String MENU_ACCOUNT = "Account";
	String MENU_HELP = "Help";
	String MENU_CONTACT = "Contact";
	String MENU_VIEW_ENC_PASS = "My Encrypted Password";
	String MENU_ENABLE_LOCK_SCREEN = "Enable Screen Locking When Away";
	String MENU_VIEW_CONTACT_INFO = "View Contact Information";
	String MENU_CREATE_CONTACT = "Create Contact";
	String MENU_DELETE_CONTACT = "Delete Contact";
	String MENU_VIEW_CONTACT_PUK = "View Public Key For Contact";
	String ACTION_VIEW_CONT_INFO = "MAIN_ACTION_INFO";
	String ACTION_CREATE_CONTACT = "MAIN_ACTION_CRE_CONT";
	String ACTION_DELETE_CONTACT = "MAIN_ACTION_DEL_CONT";
	String ACTION_DISPLAY_CONT_PUK = "MAIN_ACTION_PUK";
	String ACTION_VIEW_ENC_PASS = "MAIN_ACTION_ENC_PASS";

	String ACTION_EXIT = "MAIN_ACTION_EXIT";
	String ACTION_ABOUT = "MAIN_ACTION_ABOUT";

	String ACTION_DISPLAY_MY_PUK = "MAIN_ACTION_MY_PUK";
	String ACTION_SYSTEM_INFO = "MAIN_ACTION_SYSTEM";
	String ACTION_CHAT_CLOSE = "CHAT_ACTION_CLOSE";

	String TERM_LOCK_NEEDED = "Lock needed: ";

	String MENU_SUSPEND = "Suspend XAPI";
	String ACTION_SUSPEND = "MAIN_ACTION_SUSPEND";
	String LOCK_SCREEN = "Lock screen now";
	String ACTION_LOCK_SCREEN_NOW = "ACTION_LOCK_SCREEN_NOW";

	String ACTION_CHAT_STREAM_CANCEL_SEND = "CHAT_ACTION_CANCEL_UPLOAD";
	String ACTION_CHAT_STREAM_CANCEL_RECEIVE = "CHAT_ACTION_STOP_DOWNLOAD";
	String ACTION_CHAT_CLEAR = "CHAT_ACTION_CLEAR";
	String ACTION_CHAT_DISPLAY_PUK = "CHAT_ACTION_PUK";
	String MENU_CHAT_CHAT_NAME = "Chat";
	String MENU_CHAT_CHAT_ITEM_CLEAR = "Clear Chat";
	String MENU_CHAT_CHAT_ITEM_VIEW_PUK_4 = "View Public Key For Contact";

	String MENU_VIEW_MY_PUK = "My Public Key";
	String MENU_CHAT_FILE_NAME = "File";

	String MENU_CHAT_CONTACT_NAME = "Contact";

	String MENU_CHAT_FILE_ITEM_CLOSE = "Close";
	String MENU_CHAT_STREAM_NAME = "Stream";
	String MENU_CHAT_STREAM_ITEM_STREAM_SEND = "Stream File";
	String MENU_CHAT_STREAM_ITEM_STREAM_SEND_CANCEL = "Cancel Upload";
	String MENU_CHAT_STREAM_ITEM_STREAM_RECEIVE_REJECT = "Reject Download";
	String MENU_CHAT_HELP_NAME = "Help";
	String CHAT_THREAD = "THREAD_4_CHAT";
	String MENU_CHAT_HELP_ITEM_DISPLAY_SYSTEM_INFO = "System Info";
	String MSG_SUSPEND = "Suspending: ";

	String ERROR_BAD_SYSTEM = "Something prevented XK2 from starting (Java must be >=1.5, you must be online and Server must be accessible)";
	String ERROR_S = "Error";
	String ADD_CONTACT_TITLE_FULL = "XK2" + " " + "New Contact";
	String DIALOG_INVITE_CONTACT = "Enter the email to invite";
	String SEND_CANCEL = "Cancelling your stream...";
	String REJECT_STREAM = "Rejecting stream...";

	String SET_PASSWORD_FIELD = "XKomm.pass";
	String SET_SCREEN_LOCK_LIMIT = "XKomm.screenLockLimit";
	String DEF_SCREEN_LOCK_LIMIT = "100";

	int DEFAULT_WIDTH = 300;
	int DEFAULT_HEIGHT = 500;
	int DEFAULT_X_LOC = 200;
	int DEFAULT_Y_LOC = 200;

	String JERROR_CODE_001 = "[#J001] "; // Network test fail
	String JERROR_CODE_002 = "[#J002] "; // Bad account pack
	String JERROR_CODE_003 = "[#J003] "; // Login fail
	String JERROR_CODE_004 = "[#J004] "; // Server PUK fail
	String JERROR_CODE_005 = "[#J005] "; // XK2 did not start
	String JERROR_CODE_006 = "[#J006] "; // Java version fail

	String ALERT_SELECT_A_CONTACT = "Select a Contact to Delete...";
	String ALERT_EXIT_CONFIRM = "Exit XKomm2?";
	String COPYRIGHT = "(c) 2014-2026 Larssentech";
	String ALERT_SHOW_PUK = "User PUK";
	String XKOMM = "XK2";
	String DOT = ".";
	String SPACE = " ";
	String MSG_STATUS_NOT_AVALABLE = " ...not available";
	String LBL_ENTER_PASSWORD = "Enter Password:";
	String LBL_YES = "Yes";
	char ECHO_CHAR = '*';
	String LBL_OK = "  OK  ";
	String LBL_SCREEN_LOCKED = "Screen Locked";
	String LBL_OFFLINE = "Offline ";
	String LBL_ONLINE = "Online";
	String LBL_INACTIVE_4 = ", Inactive for ";
	String LBL_MINUTES = " minutes";
	String LBL_SINCE = "since ";
	String SET_LOCK_SCREEN_TIMEOUT = "XKomm.screenLockLimit";

	String MAIN_FRAME_DISPLAY = Constants4API.USER_HOME + Constants4API.SEP + "display.data";

	String ALERT_ADD_CONT_1 = "Invite ";
	String ALERT_ADD_CONT_2 = " as a contact?";
	String ALERT_INVITE_SENT = "Invitation sent. Your new contact will appear when they accept the request.";
	String ALERT_DELETE_1 = "Delete contact ";
	String QUESTION_MARK = "?";

	int INACTIVITY_1_SECOND = 1000;
	int MAX_WORD_LENGTH = 30;
	String INACTIVITY_THREAD = "xkomm.thread.inactivity";
	String LICENSE_FILE = "license.txt";

}