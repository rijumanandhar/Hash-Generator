package hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class AlgorithmOne implements HashGenerator {
	
	FileReader reader = new FileReader();

	public String calculateHash(String data) {
		byte[] bytes = data.getBytes();

		int length = bytes.length;

		long total = 0;

		// Dump byte array contents and total the values.
		for (byte b : bytes) {
			total += b * 13;
		}

		// create a very simple hash (total of byte values, each multiplied by a prime
		// number, all of which is multiplied by file size)
		total *= length * 997;

		// Format the output to use 16 chars (since this is the max size of a long)
		String hash = String.format("%016X", total);
		return hash;
	}

	@Override
	public String produceFileHash(File file) {
		String filedata = reader.ReadFile(file);
		String hash = calculateHash(filedata);
		return hash;
	}

	@Override
	public String produceDirHash(File[] folder) {
		String filedata = reader.ReadFolder(folder);
		String hash = calculateHash(filedata);
		return hash;
	}

	@Override
	public String produceDirMetaHash(File[] folder) {
		String filedata = reader.ReadMetaData(folder);
		String hash = calculateHash(filedata);
		return hash;
	}

}
