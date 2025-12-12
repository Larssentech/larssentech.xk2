package org.larssentech.xkomm2.ui.shared.chat;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class MessageBox extends JScrollPane {

	private final JTextArea textArea = new JTextArea();

	public MessageBox() {

		this.setBackground(Xkomm2Theme.getBackground());
		this.setBorder(getBorder());

		this.textArea.setBackground(Xkomm2Theme.getBackground());
		this.textArea.setForeground(Xkomm2Theme.getForeground());

		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setCaretColor(Color.WHITE);

		this.getViewport().add(this.textArea);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public Border getBorder() {

		Border b0 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border b1 = BorderFactory.createMatteBorder(1, 1, 1, 1, Xkomm2Theme.getBorder());
		Border b2 = BorderFactory.createCompoundBorder(b0, b1);

		Border b3 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border b4 = BorderFactory.createCompoundBorder(b2, b3);

		return b4;
	}

	public String getText() { return this.textArea.getText(); }

	public void setText(String string) {

		this.textArea.setText("");
	}

	public JTextArea getTextArea() { return this.textArea; }

}