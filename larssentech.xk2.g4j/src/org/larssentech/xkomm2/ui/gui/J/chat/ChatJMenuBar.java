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

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import org.larssentech.xkomm2.ui.gui.J.constants.GReg;

/**
 * VIEW
 * 
 * @author avanz.io
 *
 */
class ChatJMenuBar extends MenuBar {

	ChatJMenuBar(final ChatJFrame owner) {

		this.add(new ChatFrameMenuFile(owner));
		this.add(new ChatFrameMenuContact(owner));
		this.add(new ChatFrameMenuChat(owner));
		this.add(new ChatFrameMenuStream(owner));
		this.add(new ChatFrameMenuHelp(owner));
	}
}

class ChatFrameMenuContact extends Menu {

	ChatFrameMenuContact(final ChatJFrame owner) {

		this.setName(GReg.MENU_CHAT_CONTACT_NAME);
		this.setLabel(GReg.MENU_CHAT_CONTACT_NAME);

		final MenuItem puk;

		puk = new MenuItem(GReg.MENU_CHAT_CHAT_ITEM_VIEW_PUK_4);

		this.add(puk);

		puk.setActionCommand(GReg.ACTION_CHAT_DISPLAY_PUK);

		puk.addActionListener(new ChatJListener(owner));
	}
}

class ChatFrameMenuChat extends Menu {

	ChatFrameMenuChat(final ChatJFrame owner) {

		this.setName(GReg.MENU_CHAT_CHAT_NAME);
		this.setLabel(GReg.MENU_CHAT_CHAT_NAME);

		final MenuItem clear;

		clear = new MenuItem(GReg.MENU_CHAT_CHAT_ITEM_CLEAR);

		this.add(clear);

		clear.setActionCommand(GReg.ACTION_CHAT_CLEAR);

		clear.addActionListener(new ChatJListener(owner));
	}
}

class ChatFrameMenuFile extends Menu {

	ChatFrameMenuFile(final ChatJFrame owner) {

		this.setName(GReg.MENU_CHAT_FILE_NAME);
		this.setLabel(GReg.MENU_CHAT_FILE_NAME);
		final MenuItem close = new MenuItem(GReg.MENU_CHAT_FILE_ITEM_CLOSE);

		this.add(close);

		close.setActionCommand(GReg.ACTION_CHAT_CLOSE);
		close.addActionListener(new ChatJListener(owner));
	}
}

class ChatFrameMenuStream extends Menu {

	private final MenuItem streamFile, rejectDownload, cancelUpload;

	ChatFrameMenuStream(final ChatJFrame owner) {

		this.setName(GReg.MENU_CHAT_STREAM_NAME);
		this.setLabel(GReg.MENU_CHAT_STREAM_NAME);

		this.streamFile = new MenuItem(GReg.MENU_CHAT_STREAM_ITEM_STREAM_SEND);
		this.cancelUpload = new MenuItem(GReg.MENU_CHAT_STREAM_ITEM_STREAM_SEND_CANCEL);
		this.rejectDownload = new MenuItem(GReg.MENU_CHAT_STREAM_ITEM_STREAM_RECEIVE_REJECT);

		this.add(this.streamFile);
		this.addSeparator();

		this.add(this.cancelUpload);
		this.add(this.rejectDownload);

		this.streamFile.setActionCommand(GReg.ACTION_CHAT_STREAM);
		this.cancelUpload.setActionCommand(GReg.ACTION_CHAT_STREAM_CANCEL_SEND);
		this.rejectDownload.setActionCommand(GReg.ACTION_CHAT_STREAM_CANCEL_RECEIVE);

		this.streamFile.addActionListener(new ChatJListener(owner));
		this.cancelUpload.addActionListener(new ChatJListener(owner));
		this.rejectDownload.addActionListener(new ChatJListener(owner));
	}
}

class ChatFrameMenuHelp extends Menu {

	ChatFrameMenuHelp(final ChatJFrame owner) {

		this.setName(GReg.MENU_CHAT_HELP_NAME);
		this.setLabel(GReg.MENU_CHAT_HELP_NAME);

		final MenuItem system = new MenuItem(GReg.MENU_CHAT_HELP_ITEM_DISPLAY_SYSTEM_INFO);

		this.add(system);

		system.setActionCommand(GReg.ACTION_SYSTEM_INFO);
		system.addActionListener(new ChatJListener(owner));
	}
}