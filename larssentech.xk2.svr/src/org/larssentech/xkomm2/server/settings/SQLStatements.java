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
package org.larssentech.xkomm2.server.settings;

public interface SQLStatements {

	String loginQuery = "SELECT idxk_user FROM xk_user WHERE xk_user_email = '<XX>'";
	String candidateQuery = "SELECT idxk_candidate FROM xk_candidate WHERE xk_candidate_email = '<XX>'";
	String updatePassword = "UPDATE xk_user set xk_user_pass = '<XX>'  where xk_user_email = '<XX>'";
	String createPukStubQuery = "insert into xk_pub_key (idxk_pub_key, idxk_user, xk_pub_key) values (NULL, '<XX>', 'NO_PUBKEY')";
	String getPasswordQuery = "SELECT xk_user_pass FROM xk_user WHERE xk_user_email = '<XX>'";
	String createNewUser = "insert into xk_user values (null, '<XX>', '<XX>', '19990101000000', 'E', 1, CURRENT_TIMESTAMP, 0)";
	String setOnlineQuery = "update xk_user set xk_user_mode = '<XX>', xk_user_last_active2 = CURRENT_TIMESTAMP, xk_seconds_inactive = '<XX>' where idxk_user = '<XX>'";
	String initSession = "update xk_session set xk_token = CURRENT_TIMESTAMP where xk_owner = <XX>";
	String retrSession = "select xk_token, xk_user_audit_new_msgs from xk_session left outer join xk_user_audit on xk_owner = xk_user_audit_user_id where xk_owner = <XX>";
	String insertSession = "insert into xk_session values(null, '<XX>', CURRENT_TIMESTAMP)";
	String updateAudit = "update xk_user_audit set xk_user_audit_new_msgs = 0 where xk_user_audit_user_id = <XX>";
	String getContactsQuery4 = "select u.idxk_user, xk_user_email, xk_user_last_active2, p.xk_pub_key, xk_user_status, u.xk_user_mode, u.xk_seconds_inactive from xk_user u, xk_contact c, xk_pub_key p where  u.idxk_user=c.xk_user_2 and u.idxk_user=p.idxk_user and c.xk_user_1 = <XX>";
	String retrieveIDForEmailQuery = "SELECT idxk_user FROM xk_user WHERE xk_user_email = '<XX>'";
	String retrieveEmailForIDQuery = "SELECT xk_user_email FROM xk_user WHERE idxk_user = <XX>";
	String getInviteQuery = "select xk_user_invite_from, xk_user_invite_to from xk_user_invite where xk_user_invite_from = <XX> and xk_user_invite_to = <XX>";
	String createContact = "insert into xk_contact values(null, <XX> ,<XX>)";
	String createInvite = "insert into xk_user_invite values(null, <XX> ,<XX>)";
	String deleteContactForIDs = "delete from xk_contact where xk_user_1 = <XX> and xk_user_2 = <XX>";
	String deleteCandidate = "delete from xk_candidate where xk_candidate_email = '<XX>'";
	String areUsersContacts = "select * from xk_contact where xk_user_1 = <XX> and xk_user_2 = <XX>";
	String updateUserPubKey = "UPDATE xk_pub_key SET xk_pub_key = '<XX>' WHERE idxk_user = <XX>";
	String getUserPubKey = "SELECT xk_pub_key FROM xk_pub_key WHERE idxk_user = <XX>";
	String retrieve_some_user_msgs_query = "select a0.idxk_msg, a0.xk_msg_from_id, a0.xk_msg_to_id, a0.xk_msg_txt, a0.xk_msg_dts, a0.xk_msg_type, u.xk_user_email, a0.xk_xuid from xk_msg a0 inner join xk_user u on a0.xk_msg_from_id = u.idxk_user where a0.xk_msg_to_id = <XX> order by 1 limit <XX>";
	String retrieve_some_user_msgs_query_4_type = "select a0.idxk_msg, a0.xk_msg_from_id, a0.xk_msg_to_id, a0.xk_msg_txt, a0.xk_msg_dts, a0.xk_msg_type, u.xk_user_email, a0.xk_xuid from xk_msg a0 inner join xk_user u on a0.xk_msg_from_id = u.idxk_user where a0.xk_msg_to_id = <XX> and xk_msg_type = <XX> order by 1 limit <XX>";
	String deleteMessageWithID = "delete from xk_msg where idxk_msg = <XX>";
	String deleteInviteFor = "delete from xk_user_invite where xk_user_invite_from = <XX> and xk_user_invite_to = <XX>";
	String retrieveMsgCountFor = "SELECT count(*) FROM xk_msg where xk_msg_to_id = <XX>";
	String sendMesageQuery2 = "insert into xk_msg (idxk_msg, xk_msg_from_id, xk_msg_to_id, xk_msg_txt, xk_msg_dts, xk_msg_type, xk_msg_src, xk_msg_origin, xk_xuid) values(null, <XX>, <XX>, '<XX>', null, <XX>, '<XX>', <XX>, '<XX>')";
}