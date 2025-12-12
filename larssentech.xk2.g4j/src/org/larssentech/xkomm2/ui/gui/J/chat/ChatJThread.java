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

package org.larssentech.xkomm2.ui.gui.J.chat;

import org.larssentech.xkomm2.ui.gui.J.constants.GReg;

/**
 * CONTROLLER (AUTOMATIC)
 * 
 * Class to run in the background automatically. It regularly calls the Exec
 * asking it to "do" something.
 * 
 * @author avanz.io
 * 
 */
class ChatJThread extends Thread {

	private boolean on = true;

	private final ChatJFrame chatJFrame;

	public ChatJThread(final ChatJFrame chatJFrame) {

		this.on = true;

		this.chatJFrame = chatJFrame;
	}

	@Override
	public void run() {

		while (this.isOn()) {

			try {

				this.chatJFrame.getExec4Chat().doRefresh();

				Thread.sleep(GReg.CHAT_THREAD_SLEEP);
			}

			catch (InterruptedException ignored) {}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isOn() {

		return this.on;
	}

	void stopThreads() {

		this.setOn(false);
	}

	private void setOn(boolean on) {

		this.on = on;
	}
}