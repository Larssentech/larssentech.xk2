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
package org.larssentech.xkomm2.core.obj.model;

import java.io.File;

import org.larssentech.lib.basiclib.io.parser.XMLParser;
import org.larssentech.lib.basiclib.toolkit.ByteArrayToolkit;
import org.larssentech.xkomm.core.obj.constants.Constants4Stream;
import org.larssentech.xkomm2.core.obj.objects.StreamHeader;

public class StreamHeaderModel implements Constants4Stream {

	private StreamHeader streamHeader = new StreamHeader();

	public StreamHeaderModel(byte[] block) {

		byte[] headerBytes = extractRawHeader(block);

		String headerBlock = new String(headerBytes);

		String fileName = XMLParser.parseValueForTag2(headerBlock, FILE_NAME_TAG);

		String fileSize0 = XMLParser.parseValueForTag2(headerBlock, FILE_SIZE_TAG);

		String numBlocks0 = XMLParser.parseValueForTag2(headerBlock, NUM_BLOCKS_TAG);

		String blockNum0 = XMLParser.parseValueForTag2(headerBlock, BLOCK_NUM_TAG);

		this.streamHeader.setFile(new File(COPY_PATH + fileName));
		this.streamHeader.setFileSize(Long.parseLong(fileSize0));
		this.streamHeader.setNumBlocks(Integer.parseInt(numBlocks0));
		this.streamHeader.setBlockNum(Integer.parseInt(blockNum0));
	}

	private static byte[] extractRawHeader(byte[] block) {

		int headerLen = block.length < HEADER_LENGTH ? block.length : HEADER_LENGTH;
		new ByteArrayToolkit();
		byte[] header = ByteArrayToolkit.shrinkArray(block, headerLen);
		return header;
	}

	public static byte[] stripHeader(byte[] block) {

		int b64Start = HEADER_LENGTH;
		byte[] b64 = new byte[block.length - b64Start];

		for (int i = 0; i < b64.length; i++) { b64[i] = block[b64Start + i]; }
		return b64;
	}

	public static byte[] createBlockHeader(File file, int blockNumber) {

		byte[] headerBytes = new byte[HEADER_LENGTH];

		String fileName = XMLParser.createXMLFor(FILE_NAME_TAG, file.getName());
		String blockSize = XMLParser.createXMLFor(BLOCK_SIZE_TAG, "" + BLOCK_BYTES);
		String fileSize = XMLParser.createXMLFor(FILE_SIZE_TAG, "" + file.length());
		String blockNum = XMLParser.createXMLFor(BLOCK_NUM_TAG, "" + blockNumber);
		String numBlocks = XMLParser.createXMLFor(NUM_BLOCKS_TAG, "" + (1 + (file.length() / BLOCK_BYTES)));
		String header = XMLParser.createXMLFor(XML_FILE_TAG, (fileName + blockSize + fileSize + blockNum + numBlocks));

		for (int i = 0; i < header.length(); i++) headerBytes[i] = header.getBytes()[i];
		for (int i = header.length(); i < headerBytes.length; i++) headerBytes[i] = "=".getBytes()[0];

		return headerBytes;
	}

	public StreamHeader getStreamHeader() {

		return this.streamHeader;
	}

}
