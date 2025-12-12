package org.larssentech.xkomm2.ui.shared.functions;

import org.larssentech.xkomm.ui.shared.util.Alerter;
import org.larssentech.xkomm2.api.impl.login.AccountPackEncoder;
import org.larssentech.xkomm2.api.xapi.Xkomm2Api;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.contact.Gui4Login;

public class Functions4Login {

	public static boolean requestLogin() {

		// Account pack for the login dialog
		final AccountPackEncoder pack = new AccountPackEncoder().load(Constants4Uis.ACCOUNT_FILE);

		// The login dialoog
		new Gui4Login(pack, Constants4Uis.LOGIN_COLS).makePretty(320, 200);

		// If details are not quite right will exit
		if (!pack.isGood()) new Alerter().badAccountPack(true);

		// Try logging in
		if (!Xkomm2Api.apiInitialLogin(pack)) new Alerter().badLogin(true);

		// Save credentials only after a good login
		pack.persist(Constants4Uis.ACCOUNT_FILE);

		// Start XK2
		if (!Xkomm2Api.apiStartXK2()) new Alerter().badStart(true);

		return true;
	}
}
