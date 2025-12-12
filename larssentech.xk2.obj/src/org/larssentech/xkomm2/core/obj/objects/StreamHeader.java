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

package org.larssentech.xkomm2.core.obj.objects;

import java.io.File;

public class StreamHeader {

	private File file;
	private long blockSize;
	private long fileSize;
	private int blockNum;
	private int numBlocks;

	public long getBlockSize() {

		return this.blockSize;
	}

	public long getFileSize() {

		return this.fileSize;
	}

	public int getBlockNum() {

		return this.blockNum;
	}

	public int getNumBlocks() {

		return this.numBlocks;
	}

	public void setBlockSize(long blockSize) {

		this.blockSize = blockSize;
	}

	public void setFileSize(long fileSize) {

		this.fileSize = fileSize;
	}

	public void setBlockNum(int blockNum) {

		this.blockNum = blockNum;
	}

	public void setNumBlocks(int numBlocks) {

		this.numBlocks = numBlocks;
	}

	public File getFile() {

		return this.file;
	}

	public void setFile(File file) {

		this.file = file;
	}

}