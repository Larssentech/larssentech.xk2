package org.larssentech.xkomm2.ui.gui.J.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.larssentech.lib.awtlib.GUITool;
import org.larssentech.xkomm2.core.obj.version.Version4Xk2;
import org.larssentech.xkomm2.ui.gui.J.constants.GReg;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class AboutBox extends JDialog {

	private Image image;
	private JLabel imageLabel;
	private final Label messageLabel1;
	private final Label messageLabel2;

	public static void main(String[] args) {

		ThemeMaker.makeTheme();
		new AboutBox();
	}

	public AboutBox() {

		this.setModal(true);
		this.setResizable(true);
		this.getContentPane().setBackground(Xkomm2Theme.getBackground());
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		this.setSize(500, 400);
		this.setTitle("About XK2J");

		this.image = Toolkit.getDefaultToolkit().createImage("xk2.png");

		this.imageLabel = new JLabel(new ImageIcon(this.image));
		this.imageLabel.setBackground(Xkomm2Theme.getBackground());

		this.messageLabel1 = new Label();
		this.messageLabel1.setAlignment(1);
		this.messageLabel1.setPreferredSize(new Dimension(400, 15));
		this.messageLabel1.setText("XK2J: GUI " + GReg.J_VERSION + " - XK2 Core v" + Version4Xk2.BASE_VERSION_STRING);
		this.messageLabel1.setBackground(Xkomm2Theme.getBackground());
		this.messageLabel1.setForeground(Xkomm2Theme.getForeground());

		this.messageLabel2 = new Label();
		this.messageLabel2.setAlignment(1);
		this.messageLabel2.setFont(new Font("", 0, 10));
		this.messageLabel2.setPreferredSize(new Dimension(300, 10));
		this.messageLabel2.setText(Constants4Uis.COPYRIGHT + " Team");
		this.messageLabel2.setBackground(Xkomm2Theme.getBackground());
		this.messageLabel2.setForeground(Xkomm2Theme.getForeground());

		this.add(this.imageLabel);
		this.add(this.messageLabel1);
		this.add(this.messageLabel2);

		GUITool.center(this);
		this.setVisible(true);
	}

}
