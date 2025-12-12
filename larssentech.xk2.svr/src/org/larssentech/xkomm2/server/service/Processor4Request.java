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

package org.larssentech.xkomm2.server.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.larssentech.lib.basiclib.io.parser.XMLParser;
import org.larssentech.lib.basiclib.net.SocketBundle;
import org.larssentech.xkomm.core.obj.constants.NetworkConstants;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.core.obj.objects.RequestBundle;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.crypto.CryptoFunctions;
import org.larssentech.xkomm2.server.dao.GeneralDAO;
import org.larssentech.xkomm2.server.settings.SQLStatements;
import org.larssentech.xkomm2.server.uids.MessageUids;
import org.larssentech.xkomm2.server.util.ServerLock;

/**
 * The server has the responsibility of resolving any duplicate messages
 * received from clients and to only send clients the next message in
 * chronological order.
 * 
 * @author User
 *
 */
class Processor4Request implements CommonServerConstants {

	private final SocketBundle sb;

	Processor4Request(final SocketBundle sb) {

		this.sb = sb;
	}

	abstract static class RequestDecodeFunctions implements NetworkConstants {

		static String msgType(String request) {

			return decode(N_MSG_TYPE, request);
		}

		static String msgNum(String request) {

			return decode(N_MSG_NUM, request);
		}

		private static String decode(String what, String fromWhat) {

			int start = fromWhat.indexOf(what) + what.length();
			int end = fromWhat.indexOf(N_CLOSER, start);
			return fromWhat.substring(start, end);
		}

		static String contactToDelete(String request) {

			return decode(N_CONTACT, request);
		}

		static String userToInvite(String request) {

			return decode(N_RECEIVER, request);
		}
	}

	class Processor4User {

		public void processRetrieveContacts(RequestBundle requestBundle) throws IOException {

			String id = requestBundle.getUserId();
			doSendData(id, GeneralDAO.genericGet(SQLStatements.getContactsQuery4, new String[] { id }));

			Processor4Request.this.close();
		}

		public void processDeleteContact(RequestBundle requestBundle) throws IOException {

			String id = requestBundle.getUserId();
			String request = requestBundle.getRequest();

			String who = RequestDecodeFunctions.contactToDelete(request);

			if (!who.equals(id)) {

				if (GeneralDAO.genericSet(SQLStatements.deleteContactForIDs, (new String[] { id, who })) && GeneralDAO.genericSet(SQLStatements.deleteContactForIDs, (new String[] { who, id })))

					Processor4Request.this.reply(OK);

				else Processor4Request.this.reply(ERROR);

			}
			else Processor4Request.this.reply(ERROR);

			Processor4Request.this.close();
		}

	}

	class Processor4Message {

		public void processSend1MessageXml(RequestBundle requestBundle) throws IOException {

			String fromId = requestBundle.getUserId();
			String request = requestBundle.getRequest();

			String toId = XMLParser.parseValueForTag(request, XML_TO_O);

			String[] params = new String[] {

					fromId,

					toId,

					XMLParser.parseValueForTag(request, XML_MSG_BODY_O),

					XMLParser.parseValueForTag(request, XML_MSG_TYPE_O),

					XMLParser.parseValueForTag(request, XML_MSG_SRC_O),

					XMLParser.parseValueForTag(request, XML_MSG_ORIGIN_O),

					XMLParser.parseValueForTag(request, XML_XUID_O) };

			List<String[]> usersAreContacts = GeneralDAO.genericGet(SQLStatements.areUsersContacts, new String[] { fromId, toId });

			String uid = XMLParser.parseValueForTag(request, XML_XUID_O);
			boolean seenB4 = MessageUids.containsUid(uid);

			if (usersAreContacts.size() == 1) {

				if (!seenB4) {

					if (GeneralDAO.genericSet(SQLStatements.sendMesageQuery2, params)) {
						MessageUids.addUid(uid);
						Logger.pl("Stored " + uid + " - never seen before.");
						Processor4Request.this.reply(OK);
					}

					else {
						Processor4Request.this.reply(ERROR);
					}
				}
				else {
					Logger.pl("Message " + uid + " seen before, ignoring.");
					Processor4Request.this.reply(OK);
				}
			}

			else Processor4Request.this.reply(ERROR);

			Processor4Request.this.close();
		}

		public void processCreateContact4User(RequestBundle requestBundle) {

			String inviterId = requestBundle.getUserId();
			String request = requestBundle.getRequest();

			// Decode the desired invitee
			String inviteeEmail = RequestDecodeFunctions.userToInvite(request);
			String inviteeId = NetworkConstants.BAD_ID;
			// Obtain the ID for the invitee email
			List<String[]> result = GeneralDAO.genericGet(SQLStatements.retrieveIDForEmailQuery, new String[] { inviteeEmail });

			if (null == result || result.size() == 0) inviteeId = NetworkConstants.BAD_ID;
			else inviteeId = ((String[]) result.get(0))[0];

			// Creates the invite (or fails and returns)
			if (!GeneralDAO.genericSet(SQLStatements.createInvite, new String[] { inviterId, inviteeId })) {

				Processor4Request.this.reply(ERROR);
				return;
			}

			// Once invite has been successfully created, let's check if there
			// is a previous invite (the reverse one could have happened
			// earlier)
			// Check if counter-invite (acceptance of invite) by checking
			// if there is an invite with the opposite inviter/invitee
			if (this.processContactAccept(inviterId, inviteeId)) return;

			// If this was NOT the counterinvite, create the contacts??
			this.processContactCreate(inviterId, inviteeId);
		}

		private void processContactCreate(String inviterId, String inviteeId) {

			String inviterEmail = "";

			List<String[]> result = GeneralDAO.genericGet(SQLStatements.retrieveEmailForIDQuery, new String[] { inviterId });

			if (null == result || result.size() == 0) inviterEmail = NetworkConstants.BAD_ID;
			else inviterEmail = ((String[]) result.get(0))[0];

			String inviteNotification = INVITATION + inviterEmail + INVITATION_MSG;

			List<String[]> usersAreContacts = GeneralDAO.genericGet(SQLStatements.areUsersContacts, new String[] { inviteeId, inviteeId });

			if (usersAreContacts.size() == 1) {

				String[] params = new String[] {

						inviteeId,

						inviteeId,

						inviteNotification,

						"" + Message.TEXT,

						Message.XK2,

						"" + Message.DIRECT,

						"" };

				if (GeneralDAO.genericSet(SQLStatements.sendMesageQuery2, params)) Processor4Request.this.reply(OK);

				else Processor4Request.this.reply(ERROR);
			}

			else Processor4Request.this.reply(ERROR);

		}

		private boolean processContactAccept(String inviterId, String inviteeId) {

			boolean isCounterInvite = GeneralDAO.genericGet(SQLStatements.getInviteQuery, new String[] { inviteeId, inviterId }).size() == 1;

			// If this is the acceptance of an earlier invitation, then
			// create the contacts and delete the invites
			if (isCounterInvite) {

				// Add contacts with the REFERRER
				boolean success = GeneralDAO.genericSet(SQLStatements.createContact, new String[] { inviteeId, inviterId })
						&& GeneralDAO.genericSet(SQLStatements.createContact, new String[] { inviterId, inviteeId })
						&& GeneralDAO.genericSet(SQLStatements.deleteInviteFor, new String[] { inviterId, inviteeId })
						&& GeneralDAO.genericSet(SQLStatements.deleteInviteFor, new String[] { inviteeId, inviterId });

				if (success) Processor4Request.this.reply(OK);

				else Processor4Request.this.reply(ERROR);

			}
			return isCounterInvite;
		}

		public void processRetrieveSomeMessages4Type(RequestBundle requestBundle) throws IOException {

			String id = requestBundle.getUserId();
			String request = requestBundle.getRequest();

			String typeS = RequestDecodeFunctions.msgType(request);
			String numMsgS = RequestDecodeFunctions.msgNum(request);

			if (typeS.equals("" + Message.DATA)) { Logger.pl("Data message requested for " + id); numMsgS = "1"; }

			// Obtain the messages
			List<String[]> messages = GeneralDAO.genericGet(SQLStatements.retrieve_some_user_msgs_query_4_type, new String[] { id, typeS, numMsgS });

			boolean success = false;

			// If user is locked (receiving a message right now), skip
			if (!ServerLock.locked(id)) success = doSendData(id, messages);
			else Logger.pl("%");

			if (success) {

				// Request deletion
				for (int i = 0; i < messages.size(); i++) GeneralDAO.genericSet(SQLStatements.deleteMessageWithID, new String[] { messages.get(i)[0] });

				Processor4Request.this.close();

				List<String[]> numL = GeneralDAO.genericGet(SQLStatements.retrieveMsgCountFor, new String[] { id });
				String[] numA = numL.get(0);
				String numS = numA[0];
				int num = Integer.parseInt(numS);

				if (num == 0) GeneralDAO.genericSet(SQLStatements.updateAudit, new String[] { id });
			}
		}
	}

	class Processor4Account {

		public void initSession(RequestBundle requestBundle) throws IOException {

			String id = requestBundle.getUserId();
			GeneralDAO.genericSet(SQLStatements.initSession, new String[] { id });
			doSendData(id, GeneralDAO.genericGet(SQLStatements.retrSession, new String[] { id }));

			Processor4Request.this.close();
		}

		public void retrieveSession(RequestBundle requestBundle) throws IOException {

			String id = requestBundle.getUserId();
			List<String[]> session = GeneralDAO.genericGet(SQLStatements.retrSession, new String[] { id });
			doSendData(id, session);

			Processor4Request.this.close();
		}

		public void processLogin(RequestBundle requestBundle) throws IOException {

			String loginResponse = AccountFunctions.loginSetOnline(requestBundle);

			Processor4Request.this.reply(loginResponse);
			Processor4Request.this.reply(EMPTY_STRING);

			Processor4Request.this.close();
		}

		/**
		 * Method to check if there are any candidate accounts waiting to be
		 * created. If so, create the account for the candidate.
		 * 
		 * @param requestBundle
		 */
		void processCandidate(RequestBundle requestBundle) {

			// GeneralDAO.getId4Candidate(requestBundle);
			AccountFunctions.getId4Candidate(requestBundle);

			if (!requestBundle.getUserId().equals(BAD_ID)) {

				CryptoFunctions.decryptEncPassword(requestBundle);

				AccountFunctions.createUser(requestBundle);

				GeneralDAO.genericSet(SQLStatements.createContact, new String[] { requestBundle.getUserId(), requestBundle.getUserId() });

				GeneralDAO.genericSet(SQLStatements.createPukStubQuery, new String[] { requestBundle.getUserId() });

				GeneralDAO.genericSet(SQLStatements.updateUserPubKey, new String[] { requestBundle.getPuk(), requestBundle.getUserId() });

				GeneralDAO.genericSet(SQLStatements.insertSession, new String[] { requestBundle.getUserId() });

				GeneralDAO.genericSet(SQLStatements.deleteCandidate, new String[] { requestBundle.getEmail() });

			}
		}
	}

	private void close() throws IOException {

		this.sb.close();
	}

	private void reply(String s) {

		this.sb.printOut(s);
	}

	private String read() throws IOException {

		return this.sb.readLineIn();
	}

	private boolean doSendData(String id, List<String[]> list) {

		// This is to make sure we never send "the next" message to the user
		// with the ID=id before we have sent the current message.
		// int sleepCounter = 0;
		/*
		 * while (ServerLock.locked(id)) try {
		 * 
		 * sleepCounter++; Thread.sleep(100); Out.p("%"); if (sleepCounter >
		 * 100) { Out.pl("Messages to user: " + id +
		 * " stuck for > 10 seconds..."); return false; } } catch
		 * (InterruptedException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * }
		 */

		if (null != list) {

			try {

				this.reply(OK);

				ServerLock.engage(id);

				// Client will expect all lines to come after the OK
				// ending the records with a single "." as the last line
				// ending the transmission with double dot ".."
				Iterator<String[]> ite = list.iterator();

				while (ite.hasNext()) {

					String[] thisRecord = ite.next();

					// Send the fields in the message
					for (int i = 0; i < thisRecord.length; i++) this.reply(thisRecord[i]);

					// End the record
					this.reply(DOT_1);
				}

				// End the data transmission
				this.reply(DOT_2);

				// Receive confirmation from the client--IOException can be
				// thrown here
				if (this.read().equals("OK")) {
					Logger.pl("Client says data received OK");
					this.reply("OK");
					ServerLock.release(id);

					return true;
				}
				else {
					Logger.pl("Client did not confirm receipt of data");
					return false;
				}
			}

			catch (IOException e) {
				e.printStackTrace();
				ServerLock.release(id);
				return false;
			}
		}

		else this.reply(ERROR);

		return false;
	}
}