package org.larssentech.xkomm2.ui.shared.main;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.larssentech.xkomm2.ui.shared.constants.Constants4Uis;
import org.larssentech.xkomm2.ui.shared.util.Xkomm2Theme;

public class ContactContainer extends JScrollPane {

	final JList<UserEntry> contactLizt = new JList<UserEntry>();
	private final DefaultListModel<UserEntry> model = new DefaultListModel<UserEntry>();

	public ContactContainer() {

		super();

		this.getViewport().add(this.contactLizt);
		this.contactLizt.setCellRenderer(new LiztCellRenderer());
		this.contactLizt.setDoubleBuffered(true);
		this.contactLizt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.contactLizt.setFocusable(false); // To eliminate the blue focus border
		this.contactLizt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.contactLizt.setModel(this.model);

		this.contactLizt.setBackground(Xkomm2Theme.getBackground());
		this.contactLizt.setForeground(Xkomm2Theme.getForeground());

		this.setBorder(BorderFactory.createEmptyBorder());
	}

	public String getSelectedUserEntry() {

		if (this.contactLizt.getSelectedValue() != null) return this.contactLizt.getSelectedValue().toString();

		return Constants4Uis.EMPTY_STRING;
	}

	public void doRefreshLizt(boolean[] statuses, String[] contactString) {

		List<UserEntry> emails = new ArrayList<UserEntry>();

		for (int i = 0; i < contactString.length; i++) emails.add(new UserEntry(contactString[i], statuses[i]));

		emails = ContactContainer.sortByStatus(emails);

		for (int i = 0; i < contactString.length; i++) if (!this.model.contains(new UserEntry(contactString[i], statuses[i]))) this.updateLizt(emails);

		for (int i = 0; i < this.model.size(); i++) if (!emails.contains(this.model.get(i))) this.updateLizt(emails);

	}

	private static List<UserEntry> sortByStatus(List<UserEntry> emails) {

		List<UserEntry> sortedList = new ArrayList<UserEntry>();

		for (UserEntry thisEntry : emails) if (thisEntry.isOnline()) sortedList.add(thisEntry);

		for (UserEntry thisEntry : emails) if (!thisEntry.isOnline()) sortedList.add(thisEntry);

		return sortedList;

	}

	@Override
	public void setEnabled(boolean b) {

		this.contactLizt.setEnabled(b);

		for (int i = 0; i < this.model.size(); i++) this.model.get(i).setEnabled(b);
	}

	private void updateLizt(List<UserEntry> newLizt) {

		this.model.removeAllElements();
		// this.model.addAll(newLizt); -- NOT supported on Java 5

		Iterator<UserEntry> ite = newLizt.iterator();

		while (ite.hasNext()) {

			this.model.addElement(ite.next());

			// Sleep to avoid immediate successive calls to the cell renderer
			// which messes up the EDT (specifically cell renderer)
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException ignored) {}
		}
	}

	public String[] getAllUserEntries() {

		String[] returnArray = new String[this.model.size()];

		for (int i = 0; i < this.model.size(); i++) returnArray[i] = this.model.get(i).toString();

		return returnArray;
	}

	public JList<UserEntry> getContactLizt() {

		return this.contactLizt;
	}

	// TODO: for compatibility
	// getSelectedIndex()
	// getSelectedItem()
	// getItems()

}

class LiztCellRenderer extends JLabel implements ListCellRenderer<Object> {

	public LiztCellRenderer() {

		setOpaque(true);

	}

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		UserEntry entry = (UserEntry) value;

		setText(entry.getText());

		if (!entry.isEnabled()) {
			setBackground(Xkomm2Theme.getBackground());
			setForeground(Xkomm2Theme.getDisabledColorText());
		}

		else {

			if (isSelected) {
				if (entry.isOnline()) {
					setBackground(Xkomm2Theme.getSelection());
					setForeground(Xkomm2Theme.getMsgIn());
				}
				else {
					setBackground(Xkomm2Theme.getSelection());
					setForeground(Xkomm2Theme.getGrayedOutText());
				}
			}

			else {
				if (entry.isOnline()) {
					setBackground(Xkomm2Theme.getBackground());
					setForeground(Xkomm2Theme.getMsgIn());
				}
				else {
					setBackground(Xkomm2Theme.getBackground());
					setForeground(Xkomm2Theme.getGrayedOutText());
				}
			}
		}

		return this;
	}

}