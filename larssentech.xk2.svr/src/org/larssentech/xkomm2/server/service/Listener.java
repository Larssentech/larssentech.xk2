/*
 * Copyright 2014-2023 Larssentech Developers
 * 
 * This file is part of XKomm.
 * 
 * XKomm is free software: you can redistribute it and/or modify it under the
 * Outs of the GNU General Public License as published by the Free Software
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
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.larssentech.lib.basiclib.console.Out;
import org.larssentech.lib.basiclib.net.SocketBundle;
import org.larssentech.lib.basiclib.settings.SettingsExtractor;
import org.larssentech.xkomm2.server.constants.CommonServerConstants;
import org.larssentech.xkomm2.server.crypto.CryptoFunctions;

public class Listener implements CommonServerConstants {

	private static final int port = Integer.parseInt(SettingsExtractor.extractThis4(SERVER_DATA, PORT));

	private static ServerSocket ss;

	public Listener() {

		try {

			CryptoFunctions.startServerCtk();

			Out.p("Listener: " + "\t");
			Out.pl("Server socket on port: " + port);

			Listener.ss = new ServerSocket(Listener.port);

			do {

				Socket s = Listener.ss.accept();

				s.setSoTimeout(SO_TIMEOUT); // Important

				SocketBundle sb = new SocketBundle(s);

				new RequestManager(sb).start();

				System.gc();
			}

			while (true);
		}

		catch (BindException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		finally {

			try {

				Listener.ss.close();
			}
			catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}