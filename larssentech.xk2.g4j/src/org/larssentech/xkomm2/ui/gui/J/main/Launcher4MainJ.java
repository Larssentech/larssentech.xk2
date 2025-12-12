package org.larssentech.xkomm2.ui.gui.J.main;

import javax.swing.SwingUtilities;

public class Launcher4MainJ {

	public Launcher4MainJ() {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new MainJFrame();
			}
		});

	}
}
