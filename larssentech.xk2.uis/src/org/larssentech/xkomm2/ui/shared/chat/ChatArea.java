/*
 * Copyright 2014-2024 Larssentech Developers
 * 
 * This file is part of XKomm.
 * 
 * XKomm is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * XKomm is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General License for more details.
 * 
 * You should have received a copy of the GNU General License along with XKomm.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.larssentech.xkomm2.ui.shared.chat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.larssentech.xkomm.core.obj.model.XmlMessageModel;
import org.larssentech.xkomm.core.obj.objects.Message;
import org.larssentech.xkomm.ui.shared.chat.Xml2Message;
import org.larssentech.xkomm.ui.shared.util.UisUtil;
import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class ChatArea extends JScrollPane implements Constants4Uis {

	private JTextPane msga = new JTextPane();

	private void initChatArea() {

		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.getViewport().add(this.msga);
		this.setBackground(Xkomm2Theme.getBackground());
		this.setForeground(Xkomm2Theme.getForeground());
		this.setBorder(BorderFactory.createEmptyBorder());

		this.msga.setEditable(false);
		this.msga.setBackground(Xkomm2Theme.getBackground());
		this.msga.setForeground(Xkomm2Theme.getForeground());
		this.msga.setCaretPosition(this.msga.getDocument().getLength());
		this.msga.setBorder(BorderFactory.createEmptyBorder());
	}

	public ChatArea() {

		super();
		this.initChatArea();
	}

	public void appendMessage(Message m) {

		if (m.isGood() && m.getBody().length() > 0) {

			this.appendFormatted(m);

			this.scroll();
		}
	}

	public void setText(String t) {

		try {
			this.msga.getDocument().remove(0, this.msga.getDocument().getLength());
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private int appendFormatted(Message m) {

		StyledDocument doc = this.msga.getStyledDocument();

		try {

			SimpleAttributeSet attribs = new SimpleAttributeSet();

			// Shared alignment and text colour constants
			int align = m.getWay() == Message.IN ? StyleConstants.ALIGN_LEFT : StyleConstants.ALIGN_RIGHT;

			StyleConstants.setAlignment(attribs, align);
			StyleConstants.setLeftIndent(attribs, 5);
			StyleConstants.setRightIndent(attribs, 5);

			// Date line specific format
			StyleConstants.setForeground(attribs, Xkomm2Theme.getGrayedOutText());
			StyleConstants.setFontSize(attribs, 8);
			StyleConstants.setItalic(attribs, true);

			// Apply
			doc.setParagraphAttributes(doc.getLength(), 1, attribs, false);

			// Insert Source and Date
			doc.insertString(doc.getLength(), LINE_END + m.getSource(), attribs);
			doc.insertString(doc.getLength(), LINE_END + UisUtil.requestFormatDate(m.getSentDate()), attribs);
			doc.setParagraphAttributes(doc.getLength(), 1, attribs, false);

			// Message line specific format
			Color colour = m.getWay() == Message.IN ? Xkomm2Theme.getMsgIn() : Xkomm2Theme.getMsgOut();

			StyleConstants.setForeground(attribs, colour);
			StyleConstants.setFontSize(attribs, 12);
			StyleConstants.setItalic(attribs, false);

			if (m.getWay() == Message.IN) StyleConstants.setRightIndent(attribs, 25);
			if (m.getWay() == Message.OUT) StyleConstants.setLeftIndent(attribs, 25);

			// Apply
			doc.setParagraphAttributes(doc.getLength(), 1, attribs, false);

			// Append first carriage return then the message text and another
			// carriage return

			String body = ChatArea.chopBody(m.getBody());

			doc.insertString(doc.getLength(), LINE_END + body + LINE_END, attribs);
		}
		catch (BadLocationException e) {

			e.printStackTrace();
		}
		return doc.getLength();
	}

	private static String chopBody(String body) {

		// A list for the original body broken into words
		List<String> processingList = new ArrayList<String>();

		// Splitting the body into 'words'
		StringTokenizer tok = new StringTokenizer(body, Constants4Uis.SPACE);

		// Put the 'words' in a list
		while (tok.hasMoreElements()) { processingList.add(tok.nextToken()); }

		// An array for the 'chopped' words
		List<String> choppedList = new ArrayList<String>();

		// For each word
		for (int i = 0; i < processingList.size(); i++) {

			String thisWord = processingList.get(i);

			int len = Constants4Uis.MAX_WORD_LENGTH;

			// If and while a long word, split it
			if (thisWord.length() > len) {
				while (thisWord.length() > len) {
					String part1 = thisWord.substring(0, len);

					choppedList.add(part1);

					thisWord = thisWord.substring(len, thisWord.length());
					choppedList.add(Constants4Uis.NEW_LINE);
				}

				// Last chunk
				choppedList.add(thisWord);

			}

			// If not
			else choppedList.add(thisWord);

			// Add a space after the word, except last word
			if (i < processingList.size() - 1) choppedList.add(Constants4Uis.SPACE);
		}

		String returnString = "";

		// Flatten
		for (String thisWord : choppedList) returnString += thisWord;

		return returnString;
	}

	public void appendStreamLog(String log, int way) {

		if (log.length() > 0) {
			Message m = new Message();

			m.setSentDate(new Date());
			m.setBodyBytes(log.getBytes());
			m.setWay(way);
			m.setGood(true);

			this.appendMessage(m);
		}
	}

	private void scroll() {

		this.msga.setCaretPosition(this.msga.getDocument().getLength());

	}

	public void appendMessages(XmlMessageModel[] s) {

		for (int i = 0; i < s.length; i++) {

			Message m = Xml2Message.convertXml2Message(s[i]);

			this.appendMessage(m);
		}

	}
}