/*
 * Copyright 1999-2023 Larssentech Developers
 *
 * This file is part of the Larssentech BasicLib2 project.
 *
 * The Larssentech BasicLib2 Library is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * XKomm is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * the source code. If not, see <http://www.gnu.org/licenses/>.
 */

package org.larssentech.xkomm2.ui.shared.util;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.larssentech.lib.awtlib.GUITool;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class JInfoPanel extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;

	public JInfoPanel(boolean modal, int i, int j) {

		super();

		this.setModal(modal);
		this.setResizable(true);

		JScrollPane scroll = new JScrollPane();

		this.textArea = new JTextArea("", i, j);
		this.textArea.setSize(i, j - 10);
		this.textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.textArea.setBackground(Xkomm2Theme.getBackground());
		this.textArea.setForeground(Xkomm2Theme.getOnline());
		this.textArea.setLineWrap(true);
		this.textArea.setEditable(false);

		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.getViewport().add(this.textArea);
		scroll.setPreferredSize(getPreferredSize());
		scroll.setBackground(Xkomm2Theme.getBackground());

		this.getRootPane().setBorder(BorderFactory.createEmptyBorder());
		this.getContentPane().add(scroll);
		this.getContentPane().setBackground(Xkomm2Theme.getBackground());

		this.setSize(new Dimension(i, j));

		this.textArea.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseClicked(MouseEvent e) {

				// JInfoPanel.this.dispose();

			}
		});

	}

	public void showInfo(String title, String message) {

		this.setTitle(title);
		this.textArea.setText(message);

		GUITool.center(this);
		this.setVisible(true);
	}

	public void showInfo(String title, BufferedReader buf) throws IOException {

		this.setTitle(title);

		String thisLine = "";

		while ((thisLine = buf.readLine()) != null)
			this.textArea.append(thisLine + Constants4Uis.NEW_LINE);

		this.textArea.setCaretPosition(0);

		GUITool.center(this);
		this.setVisible(true);
	}
}