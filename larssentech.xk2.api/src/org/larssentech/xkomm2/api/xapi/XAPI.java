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

package org.larssentech.xkomm2.api.xapi;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.larssentech.lib.CTK.objects.PUK;
import org.larssentech.xkomm.api.impl.Impl4Contacts;
import org.larssentech.xkomm.api.impl.Impl4History;
import org.larssentech.xkomm.api.impl.Impl4Init;
import org.larssentech.xkomm.api.impl.RestartApiImpl;
import org.larssentech.xkomm.core.hub.req.Hub;
import org.larssentech.xkomm.core.obj.constants.Constants4API;
import org.larssentech.xkomm.core.obj.constants.HistoryConstants;
import org.larssentech.xkomm.core.obj.model.XmlMessageModel;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.User;
import org.larssentech.xkomm.core.util.CoreUtil;
import org.larssentech.xkomm2.api.impl.crypto.CipherApiImpl;
import org.larssentech.xkomm2.api.impl.login.AccountPackEncoder;
import org.larssentech.xkomm2.api.impl.message.MessageApiImpl;
import org.larssentech.xkomm2.api.impl.stream.StreamDownApiImpl;
import org.larssentech.xkomm2.api.impl.stream.StreamUpApiImpl;
import org.larssentech.xkomm2.api.impl.system.InitApiImpl;
import org.larssentech.xkomm2.api.impl.system.UpdateApiImpl;
import org.larssentech.xkomm2.core.obj.objects.StreamSpec;

/**
 * Class to expose all the public methods available to the developer of any
 * application using XK2. This class is used mainly by GUI projects
 * 
 * @author jcer
 * 
 */
@Deprecated
public abstract class XAPI implements HistoryConstants {

	public static void apiSetMyInactiveSeconds(long seconds) {

		Impl4Contacts.setMyInactiveSeconds(seconds);
	}

	public static boolean apiSendTyping(String contactString) {

		if (XAPI.apiSpotCheckNetworkAvailable()) {

			Message message = XAPI.apiGenerateMessage(contactString, Message.SYS, Constants4API.TYPING.getBytes());

			message.setSentDate(new Date());

			XAPI.apiSendMessage(message, Message.SAVE_HISTORY_NO);

			return true;
		}

		return false;
	}

	public static boolean apiCheckStartupConditions(boolean quit) {

		if (!XAPI.apiTestNetwork(true, true)) return false;
		if (!XAPI.apiRetrieveServerPuk()) return false;

		return true;

	}

	public static boolean[] apiGetContactStatuses() {

		return Impl4Contacts.getStatuses();
	}

	/**
	 * Method to request a pause (suspend) the Monitors, and therefore all XK2
	 * activity until resumed
	 */
	public static void apiPauseMonitor() {

		Impl4Init.setPauseMonitors(true);
	}

	/**
	 * Method to resume the Monitors and therefore all XK2 activity
	 */
	public static void apiResumeMonitor() {

		Impl4Init.setPauseMonitors(false);
	}

	/**
	 * Method to request the initial login by the user who wants to connect to the
	 * XK2 network. If the credentials are correct after passing throught the RSA
	 * encryption cycle, returns true, else false.
	 * 
	 * @param email
	 * @param plainPass
	 * @return true or false
	 */
	public static boolean apiInitialLogin(AccountPackEncoder pack) {

		return InitApiImpl.initialLogin(pack);
	}

	/**
	 * Method to invoke the start of XK2 after the initial login has happened
	 * successfully.
	 * 
	 * @return true or false
	 */
	public static boolean apiStartXK2() {

		return InitApiImpl.startXK2();
	}

	/**
	 * Method to request an invitation be sent to another user via message.
	 * 
	 * @param contactString
	 */
	public static void apiInviteUser(String contactString) {

		Hub.hubInviteUser(contactString);
	}

	/**
	 * Method to determine if an update exists for XK2.
	 * 
	 * @return true or false
	 */
	public static boolean apiIsUpdateFound() {

		return UpdateApiImpl.isUpdate();
	}

	/**
	 * Method to determine if an update exists for XK2.
	 * 
	 * @param b true or false
	 */
	public static void apiSetUpdateFound(boolean b) {

		UpdateApiImpl.setUpdate(b);
	}

	/**
	 * Method to request text (a String) be logged into the log of choice.
	 * 
	 * 
	 * @param logKey
	 * @param string
	 * @Deprecated
	 */
	public static void apiLog(String logKey, String string) {

		// Logger.pl(logKey, string, true);
	}

	/**
	 * Method to retrieve the size of the entire inbox of messages.
	 * 
	 * @return
	 */
	public static int apiGetOutboxSize() {

		return Hub.hubGetOutboxSize();
	}

	/**
	 * Method to request the generation of an instance of a Message object based on
	 * the data sent by the user.
	 * 
	 * @param contactString
	 * @param type
	 * @param bodyBytes
	 * @return the message requested
	 */
	public static Message apiGenerateMessage(String contactString, int type, byte[] bodyBytes) {

		return Hub.hubGenerateMessage(contactString, type, bodyBytes);
	}

	/**
	 * Method to request a test of the network availability. This is a live test to
	 * connect to a URL.
	 * 
	 * @return true or false the URL is reachable
	 */
	public static boolean apiTestNetwork(boolean print, boolean exit) {

		return InitApiImpl.testNetwork(print, exit);
	}

	/**
	 * Method to request the retrieval of the PUK for the server, to use it, for
	 * example, to encrypt the login and password when loggin in.
	 * 
	 * @return true or false the PUK was retrieved for the server (and from the
	 *         server)
	 */
	public static boolean apiRetrieveServerPuk() {

		return InitApiImpl.retrieveServerPuk();
	}

	/**
	 * Method to request XK2 exits after a number of seconds
	 * 
	 * @param seconds2Exit
	 */
	public static void apiExit(int seconds2Exit) {

		CoreUtil.doExit(seconds2Exit, "Exit requested...");
	}

	/**
	 * Method to request bandwidth for streaming, if bandwidth is occupied, the
	 * method will wait until bandwidth is available and will continue the streaming
	 * work for the invoking thread.
	 * 
	 */
	public static void apiRequestNetworkSlot() {

		Hub.hubRequestNetworkSlot();
	}

	/**
	 * Method to check if XK2 was online last time a network connection took place.
	 * This is not a live check, but simply relies on a previous result.
	 * 
	 * @return
	 */
	public static boolean apiSpotCheckNetworkAvailable() {

		return Hub.hubHaveNetwork();
	}

	/**
	 * Method to request the count of messages from a specific user in the inbox.
	 * 
	 * @param fromUser
	 * @return the number of messages
	 */
	public static int apiGetInboxCountFrom(String fromUser) {

		return Hub.hubGetInboxCountFrom(Hub.hubGetContact4(fromUser));
	}

	/**
	 * Method to request the streaming download log with informaiton about where the
	 * download process is to inform the user on progress.
	 * 
	 * @param contactString
	 * @return
	 */
	public static String apiGetNextDownloadLog(String contactString) {

		return StreamDownApiImpl.getNextDownloadProgressFromLog4(contactString);
	}

	/**
	 * Method to request the streaming upload log with informaiton about where the
	 * upload process is to inform the user on progress.
	 * 
	 * @param contactString
	 * @return
	 */
	public static String apiGetNextUploadLog(String contactString) {

		return StreamUpApiImpl.getNextUploadProgressFromLog4(contactString);
	}

	/**
	 * Method to retrieve the next block of a data message (download) to append to
	 * the file with all the previous blocks until the download is complete.
	 * 
	 * @param contactString
	 * @return
	 */
	public static String apiGetNextDataMessage(String contactString) {

		return StreamDownApiImpl.processNextDownload(contactString);
	}

	/**
	 * Method to request the sending of a message. All the details about who the
	 * messages goes to are contained in the instanc eof message.
	 * 
	 * @param message
	 * @return
	 */
	public static String apiSendDataMessage(Message message) {

		return MessageApiImpl.putInOutboxAndTrack(message);
	}

	/**
	 * Method to request the rejection of an incoming download stream. This message
	 * is relayed to the sender to stop their upload.
	 * 
	 * @param contactString
	 * @return
	 */
	public static boolean apiRejectStream(String contactString) {

		return StreamDownApiImpl.rejectStream(contactString);
	}

	/**
	 * Method to request the cancellation of an upload stream.
	 * 
	 * @param contactString
	 * @return
	 */
	public static boolean apiCancelUpload(String contactString) {

		return StreamUpApiImpl.cancelUpload(contactString);
	}

	/**
	 * Method to request whether sending a data message is possible to a given user
	 * for a file. If some conditions are met, then yes, else no.
	 * 
	 * @param contactString
	 * @param fileName
	 * @return
	 */
	public static int apiCanStream(String contactString, String fileName) {

		return StreamUpApiImpl.canStream(contactString, fileName);
	}

	/**
	 * Method to request the streaming upload spec with information about where the
	 * upload is in terms of progress.
	 * 
	 * @param contactString
	 * @return
	 */
	public static StreamSpec apiGetUpStreamSpec4(String contactString) {

		return StreamUpApiImpl.getUpStreamSpec4(contactString);
	}

	/**
	 * Method to request the streaming download spec with information about where
	 * the download is in terms of progress.
	 * 
	 * @param contactString
	 * @return
	 */
	public static StreamSpec apiGetDownStreamSpec4(String contactString) {

		return StreamDownApiImpl.getDownStreamSpec4(contactString);
	}

	public static void apiSendFile(String contactString, String fileName) {

		StreamUpApiImpl.streamFromFileX(contactString, Hub.hubGetContact4(contactString).getKeyPair().getPuk(),
				new File(fileName));
	}

	/**
	 * Method to request the next "sys" message in the inbox from a specific user
	 * contact.
	 * 
	 * @param contactString
	 * @return
	 */
	public static Message apiGetNextSysMessage(String contactString) {

		return MessageApiImpl.getNextSysMessage(contactString);
	}

	/**
	 * Method to request the sending of a message. All the details are within the
	 * instance of message. There is a parameter for save to history or not.
	 * 
	 * @param message
	 * @param history
	 * @return
	 */
	public static boolean apiSendMessage(Message message, boolean history) {

		return MessageApiImpl.sendMessage(MessageApiImpl.spotSys(message), history);
	}

	/**
	 * 
	 * @param bunch
	 * @param history
	 */
	public static void apiSendBunchOfMessages(Message[] bunch, boolean history) {

		MessageApiImpl.sendBunchOfMessages(bunch, history);
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	public static Message apiGetNextTextMessage(String contactString, boolean history) {

		return MessageApiImpl.getNextTextMessage(contactString, history);

	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	public static Message apiGetNextMessage(String contactString, boolean history) {

		return MessageApiImpl.getNextMessage(contactString, history);

	}

	/**
	 * ANDROID --DO NOT REMOVE--
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String[]> apiGetMonitorControllerInfo() {

		return Hub.hubGetControllerInfo();
	}

	/**
	 * ANDROID --DO NOT REMOVE--
	 * 
	 * @return
	 */
	public static User[] apiGetContacts() {

		return Impl4Contacts.getContacts();
	}

	/**
	 * 
	 * @param logPath
	 */
	@Deprecated
	public static void apiSetLogPath(String logPath) {

		// Hub.hubSetLogPath(logPath);
	}

	/**
	 * 
	 * @param contactString
	 * @return number of minutes inactive
	 */
	@Deprecated
	public static long apiGetMinutesInactive(String contactString) {

		return XAPI.apiGetSecondsInactive(contactString) / 60;
	}

	/**
	 * 
	 * @return
	 */
	public static String[] apiGetContactEmails() {

		return Hub.hubGetContactEmails();
	}

	/**
	 * 
	 * @return
	 */
	public static boolean apiGetContactsHaveChanged() {

		return Hub.hubDetectChanges();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	@Deprecated
	public static Date apiGetLastSeen(String contactString) {

		return Hub.hubGetContact4(contactString).getLastSeen();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	@Deprecated
	public static String apiGetInactiveMode(String contactString) {

		return Hub.hubGetContact4(contactString).getStatus();
	}

	/**
	 * 
	 * @return
	 */
	@Deprecated
	public static boolean apiAmLoggedIn() {

		return Hub.hubGetMe().amLoggedIn();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	@Deprecated
	public static boolean apiIsOnline(String contactString) {

		return Hub.hubGetContact4(contactString).isOnline();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	public static boolean apiDeleteContact(String contactString) {

		return Hub.hubDeleteContact(Hub.hubGetContact4(contactString));
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	public static boolean apiIsContact(String contactString) {

		return Impl4Contacts.isContact(contactString);
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	@Deprecated
	public static PUK apiGetPuk(String contactString) {

		return Hub.hubGetContact4(contactString).getKeyPair().getPuk();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	@Deprecated
	public static String apiGetIdForEmail(String contactString) {

		return Hub.hubGetContact4(contactString).getId();
	}

	@Deprecated
	public static void apiSetMeInactive(boolean b) {

		// Impl4Contacts.setMeInactive(b);
	}

	@Deprecated
	public static void apiresetInactivity() {

		// Impl4Contacts.resetInactivity();
	}

	public static User apiGetMe() {

		return Hub.hubGetMe();
	}

	public static String apiGetEncryptedPassword() {

		return XAPI.apiGetMe().getLogin().getEncryptedPassword();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	@Deprecated
	public static long apiGetSecondsInactive(String contactString) {

		return Hub.hubGetContact4(contactString).getSecondsInactive();
	}

	/**
	 * 
	 * @param contactString
	 * @return
	 */
	public static User apiGetContact4Email(String contactString) {

		return Hub.hubGetContact4(contactString);
	}

	@Deprecated
	public static boolean apiAmIInactive() {

		return XAPI.apiGetMe().isInactive();
	}

	public static PUK apiGetServerPuk() {

		return CipherApiImpl.getPuk4Server();
	}

	public static PUK apiLoadPuk4Me() {

		return CipherApiImpl.getPuk4Me();
	}

	public static XmlMessageModel[] apiGetHistory(String to, int lines) {

		return Impl4History.getHistory(Constants4API.HISTORY_FOLDER_NU, to, lines);
	}

	public static void apiDeleteHistory(String contactString) {

		Impl4History.deleteHistory(contactString);
	}

	public static void apiRestart() {

		new RestartApiImpl(true);
	}
}