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
package org.larssentech.xkomm2.ui.shared.contact;

import org.larssentech.xkomm.core.obj.objects.AccountPack;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;

public class Frame4Contact extends Gui4Account {

	public Frame4Contact(AccountPack p, int fieldCols) {

		super(p);
		super.build();

		this.setTitle(Constants4Uis.ADD_CONTACT_TITLE_FULL);
		this.loginLabel.setText(Constants4Uis.DIALOG_INVITE_CONTACT);

		// Hide the sh!t we don't need
		this.plainPassLabel.setVisible(false);
		this.passField.setVisible(false);
	}

	@Override
	protected void doOK() {

		super.doOK();
		this.dispose();
	}

}
// <-- 100 lines max