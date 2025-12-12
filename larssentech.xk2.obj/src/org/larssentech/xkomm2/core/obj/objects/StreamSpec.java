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

package org.larssentech.xkomm2.core.obj.objects;

import java.io.File;
import java.util.Date;

public class StreamSpec {

	private String to;
	private final File file;
	private final Date start;
	private int completedP100;
	private boolean cancelled = false;
	private boolean rejected = false;
	private boolean done = false;

	public StreamSpec(String to, File file) {

		this.to = to;
		this.file = file;
		this.start = new Date();
		this.completedP100 = 0;
	}

	public int getCompletedP100() { return this.completedP100; }

	public void setCompletedP100(int completedP100) {

		if (this.completedP100 != 100) {

			this.completedP100 = completedP100;

		}
	}

	public boolean isDone() { return this.getCompletedP100() == 100; }

	public File getFile() { return this.file; }

	public Date getStart() { return this.start; }

	public boolean isCancelled() { return this.cancelled; }

	public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

	public boolean isRejected() { return this.rejected; }

	public void setRejected(boolean rejected) { this.rejected = rejected; }

	public String getTo() { return this.to; }

	public void setDone(boolean b) { this.done = true; }

	public boolean getDone() { return this.done; }
}
