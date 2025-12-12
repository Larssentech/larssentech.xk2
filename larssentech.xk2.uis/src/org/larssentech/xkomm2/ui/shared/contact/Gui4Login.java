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

package org.larssentech.xkomm2.ui.shared.contact;

import org.larssentech.xkomm.core.obj.objects.AccountPack;

public class Gui4Login extends Gui4Account {

	public Gui4Login(final AccountPack pack, int fieldCols) {

		super(pack);
		Gui4Login.this.build();
	}

	@Override
	protected void build() {

		super.build();
		this.setTitle("Login to XK2");
		this.populate(this.pack);
	}

	@Override
	protected void doOK() {

		super.doOK();

		if (super.pack.loginIsGood()) {

			super.pack.update(this.emailField.getText(), new String(this.passField.getPassword()), true, false);

			this.dispose();
		}
	}
}
//<-- 100 lines max