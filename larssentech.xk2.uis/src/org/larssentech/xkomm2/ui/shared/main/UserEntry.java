package org.larssentech.xkomm2.ui.shared.main;

import javax.swing.JLabel;

class UserEntry extends JLabel {

	private boolean online = false;
	private boolean enabled = true;

	UserEntry(String contactString, boolean online) {

		this.setText(contactString);
		this.online = online;
	}

	public String getHashCode() { return this.getText(); }

	@Override
	public boolean equals(Object o) {

		return this.toString().equals(o.toString()) && this.online == ((UserEntry) o).isOnline();
	}

	public void setEnabled(boolean b) { this.enabled = b; }

	public String toString() {

		return this.getText();
	}

	public boolean isOnline() { return this.online; }

	@Override
	public boolean isEnabled() { return this.enabled; }

}
