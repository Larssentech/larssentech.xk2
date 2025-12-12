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

import org.larssentech.lib.basiclib.net.SocketBundle;
import org.larssentech.xkomm.core.obj.objects.RequestBundle;
import org.larssentech.xkomm.core.obj.util.Logger;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.util.XmlUtil;

class RequestParser implements CommonServerConstants {

	protected final SocketBundle sb;
	protected final RequestBundle requestBundle;

	RequestParser(SocketBundle sb, RequestBundle requestBundle) throws IOException {

		this.sb = sb;
		this.requestBundle = requestBundle;

		String request = "<empty>";

		request = sb.readLineIn();

		requestBundle.setRequest(request);

	}

	void parseRequest() throws IOException {

		Logger.p(".");

		Processor4Request processor = new Processor4Request(this.sb);

		if (XmlUtil.getRequestName(this.requestBundle.getRequest()).startsWith(SS_SEND_MESSAGE)) processor.new Processor4Message().processSend1MessageXml(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_RETRIEVE_MESSAGES_4_TYPE)) processor.new Processor4Message().processRetrieveSomeMessages4Type(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_INVITE_USER)) processor.new Processor4Message().processCreateContact4User(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_DELETE_CONTACT)) processor.new Processor4User().processDeleteContact(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_RETRIEVE_CONTACTS)) processor.new Processor4User().processRetrieveContacts(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_LOGIN)) processor.new Processor4Account().processLogin(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_INIT_SESSION)) processor.new Processor4Account().initSession(this.requestBundle);

		else if (this.requestBundle.getRequest().startsWith(SS_RETR_SESSION)) processor.new Processor4Account().retrieveSession(this.requestBundle);

		else {

			try {

				this.sb.close();

			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}