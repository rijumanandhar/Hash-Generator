package hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class FileReader {
	
	public String ReadFile(File file) {
		String filedata = "";
		try {
			Scanner fileReader = new Scanner(file);
			while (fileReader.hasNext()) {
				filedata = filedata+ fileReader.nextLine() 
				+ String.format("%n");
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filedata;
	}
	
	public String ReadFolder(File[] folder) {
		String filedata = "";
		for (int i = 0; i < folder.length; i++) {
			try {
				Scanner fileReader = new Scanner(folder[i]);
				while (fileReader.hasNext()) {
					filedata = filedata + fileReader.nextLine() 
						+ String.format("%n");
				}
				fileReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return filedata;	
	}
	
	public String ReadMetaData(File[] folder) {
		String filedata = "";
		for (File next : folder) {
			// Get name and last modified information.
			String name = next.getName();

			long lastModified = next.lastModified();

			filedata = "Name: " + name + ", modified: " + new Date(lastModified);
			long fileLength = next.length();

			filedata = filedata + ", Length: " + fileLength;

		}
		return filedata;
	}

}
