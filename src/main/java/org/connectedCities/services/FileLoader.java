package org.connectedCities.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import org.connectedCities.domain.ErrorMsgs;
import org.connectedCities.utility.Terminator;

public class FileLoader {

	public BufferedReader loadFile(final String filePath) {
		File dataFile = new File(filePath);
		checkFile(dataFile);
		MappedByteBuffer mByteBuffer = loadDataFile(dataFile);
		return getBufferedReader(mByteBuffer);
	}

	protected final MappedByteBuffer loadDataFile(File dataFile) {
		FileInputStream fInputStream = null;
		MappedByteBuffer mBytebuffer = null;
		FileChannel fChannel = null;
		try {
			fInputStream = new FileInputStream(dataFile);
			fChannel = fInputStream.getChannel();
			mBytebuffer = fChannel.map(MapMode.READ_ONLY, 0, fChannel.size());
		} catch (FileNotFoundException ex) {
			Terminator.terminate(ex.getMessage(), 1);
		} catch (IOException e) {
			Terminator.terminate(e.getMessage(), 1);
		} finally {
			closeResource(fInputStream, fChannel);
		}
		return mBytebuffer;
	}

	protected final BufferedReader getBufferedReader(
			final MappedByteBuffer mByteBuffer) {
		byte[] buffer = new byte[mByteBuffer.limit()];
		mByteBuffer.get(buffer);
		ByteArrayInputStream isr = new ByteArrayInputStream(buffer);
		InputStreamReader ip = new InputStreamReader(isr);
		return new BufferedReader(ip);
	}

	protected final void closeResource(final FileInputStream fis,
			final FileChannel fChannel) {
		try {
			fis.close();
			fChannel.close();
		} catch (IOException ex) {
			Terminator.terminate(ex.getMessage(), 1);
		}

	}

	protected final void checkFile(final File file) {
		if (!file.exists()) {
			Terminator.terminate(
					file.getName() + ErrorMsgs.FILE_NOT_FOUND.value(), 1);
		}
		if (file.length() == 0) {
			Terminator.terminate(
					file.getName() + ErrorMsgs.FILE_IS_EMPTY.value(), 1);
		}
	}

}
