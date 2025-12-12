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
package org.larssentech.xkomm2.ui.shared.main;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class MainZtatuzBar extends JLabel {

	public MainZtatuzBar(String initialText) {

		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBackground(Xkomm2Theme.getBackground());
		this.setForeground(Xkomm2Theme.getGrayedOutText());
		this.setText(initialText);
	}

}
