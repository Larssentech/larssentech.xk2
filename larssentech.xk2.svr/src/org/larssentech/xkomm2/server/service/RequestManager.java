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
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.larssentech.lib.basiclib.io.parser.XMLParser;
import org.larssentech.lib.basiclib.net.SocketBundle;
import org.larssentech.xkomm.core.obj.constants.LoginHeaderConstants;
import org.larssentech.xkomm.core.obj.model.LoginHeaderModel;
import org.larssentech.xkomm.core.obj.objects.RequestBundle;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.crypto.CryptoFunctions;

class RequestManager extends Thread implements CommonServerConstants {

	private SocketBundle sb;

	RequestManager(SocketBundle sb) {

		this.sb = sb;
		this.setName("Thread-Request-Manager");
	}

	public void run() {

		// This is the object that will contain everything regarding this
		// request
		final RequestBundle requestBundle = new RequestBundle();

		/**
		 * Inner class to process the login
		 * 
		 * @author avanz.io
		 *
		 */
		class LoginHeaderFunctions implements CommonServerConstants, LoginHeaderConstants {

			public LoginHeaderFunctions() throws IOException {

				/**
				 * We send a welcome message followed by the server's public key
				 * so that the client can use it to encrypt (and PK sign) his
				 * password to us.
				 */
				{
					RequestManager.this.sb.printOut(LINE_1_OUT);
					RequestManager.this.sb.printOut(CryptoFunctions.getServerPUKValueB64());
				}

				/**
				 * This next line tells us what to do next
				 */
				String entireLine = "";

				{
					// fail next if timeout or reset
					entireLine = RequestManager.this.sb.readLineIn();
				}

				/**
				 * If <end> then the user does not really want anything else. If
				 * the line dropped, entireLine may be null, so an exception
				 * will be thrown and this request is lost
				 */
				{
					if (entireLine.equals(CLIENT_LINE_ENDER)) return;
				}

				/**
				 * If we get to this point, read the login values and populate
				 * header
				 */
				{
					int headerLineNum = Integer.parseInt(XMLParser.parseValueForTag(entireLine, HEADER_SIZE));

					String newLine = EMPTY_STRING;

					for (int i = 0; i < headerLineNum; i++) {

						newLine = RequestManager.this.sb.readLineIn();

						String key = XMLParser.parseKeyForLine(newLine);
						String val = XMLParser.parseValueForTag2(newLine, key);

						requestBundle.getHeaderAttributes().put(key, val);
					}

					// Ask the header to populate
					LoginHeaderModel.populate(requestBundle);
				}
			}
		}

		try {

			{
				new LoginHeaderFunctions(); // fail if timeout or reset
			}

			if (requestBundle.isComplete()) LoginProcessor.processLogin(requestBundle, this.sb);
		}

		/**
		 * Catches anything catcheable across Manager, Parser and Processors
		 */
		catch (SocketException e) {

			e.printStackTrace();
		}

		catch (SocketTimeoutException e) {

			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}

		catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}
}