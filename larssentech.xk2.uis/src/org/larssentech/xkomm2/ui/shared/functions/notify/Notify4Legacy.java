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
package org.larssentech.xkomm2.ui.shared.functions.notify;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Frame;

class Notify4Legacy extends Frame {

	private static int counter;

	Notify4Legacy() {

		this.setSize(100, 100);
		this.setBackground(Color.red);
	}

	static void newMessageSound(String string) {

		Thread t = new Thread() {

			// @SuppressWarnings("deprecation")

			@Override
			public void run() {

				if (counter % 5 == 0) {

					AudioClip clip = Applet.newAudioClip(getClass().getResource("beep3.wav"));
					clip.play();
				}

				counter++;

			}
		};
		t.start();
	}
}
