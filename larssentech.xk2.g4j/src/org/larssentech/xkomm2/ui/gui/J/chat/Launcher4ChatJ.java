package org.larssentech.xkomm2.ui.gui.J.chat;

import java.awt.Frame;

import javax.swing.SwingUtilities;

import org.larssentech.xkomm2.ui.shared.chat.ChatRoomMan;

public class Launcher4ChatJ extends Frame {

	public Launcher4ChatJ(final String to) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				if (!ChatRoomMan.isReg(to)) ChatRoomMan.reg(to, new ChatJFrame(to));
			}
		});
	}
}