package hash;

import java.io.File;

public class AlgorithmTwo implements HashGenerator {

	FileReader reader = new FileReader();

	public String calculateHash(String data) {
		byte[] bytes = data.getBytes();

		int length = bytes.length;

		long total = 0;

		// Dump byte array contents and total the values.
		for (int i=0; i<bytes.length-1;i++) {
			if (isEven(i)) {
				total = total +(bytes [i] | bytes [i+1]);
			}else {
				total = total +(bytes [i] & bytes [i+1]);
			}
			
		}

		// create a very simple hash (total of byte values, each multiplied by a prime
		// number, all of which is multiplied by file size)
		total *= length * 227;

		// Format the output to use 16 chars (since this is the max size of a long)
		String hash = String.format("%016X", total);
		return hash;
	}
	
	public static boolean isEven(int a) {
		return (a % 2) == 0;
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
