package org.larssentech.xkomm2.ui.gui.J.util;

import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class ThemeMaker {

	public static void makeTheme() {

		Xkomm2Theme.setBackground(GReg.BACKGROUND_COLOR);
		Xkomm2Theme.setForeground(GReg.FOREGROUND_COLOR);

		Xkomm2Theme.setMsgIn(GReg.DEF_MSG_IN_COLOUR);
		Xkomm2Theme.setMsgOut(GReg.DEF_MSG_OUT_COLOUR);

		Xkomm2Theme.setOnline(GReg.ONLINE_COLOR);
		Xkomm2Theme.setOffline(GReg.OFFLINE_COLOR);
		Xkomm2Theme.setDisabledColorText(GReg.DISABLED_COLOR_TEXT);

		Xkomm2Theme.setGrayedOut(GReg.GRAYEDOUT_COLOUR);
		Xkomm2Theme.setGrayedOutText(GReg.GRAYED_OUT_TEXT);

		Xkomm2Theme.setBorder(GReg.BORDER_COLOR);
		Xkomm2Theme.setSelection(GReg.SELECTION);
	}
}