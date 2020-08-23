package hash;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

public class GuiPanel extends JPanel {
	private final static String[] option = { "Algorithm 1", "Algorithm 2", "Algorithm 3" };

	private JComboBox<String> algorithmCombo;
	private JLabel fileNameLabel, folderNameLabel, hashLabel, checkLabel;
	private JButton loadButton;
	private JMenuItem selectFile, selectFolder;
	private JCheckBox metaCheck;

	BufferedReader br;

	String filepath;
	File[] files;
	File filename, foldername;

	List<String> filepathList, hashList;
	List<Integer> algoList;
	List<Boolean> isMetaList;

	public GuiPanel() {
		algorithmCombo = new JComboBox(option);

		fileNameLabel = new JLabel();
		folderNameLabel = new JLabel();
		hashLabel = new JLabel();
		checkLabel = new JLabel("Select Metadata only");

		metaCheck = new JCheckBox();

		loadButton = new JButton("Load");

		add(algorithmCombo);
		add(fileNameLabel);
		add(folderNameLabel);
		add(hashLabel);
		add(loadButton);
		add(metaCheck);
		add(checkLabel);

		loadButton.addActionListener(new LoadListener());

		// setPreferredSize(new Dimension(800, 80));
		loadButton.setBounds(75, 50, 150, 30);
		checkLabel.setBounds(200, 100, 150, 30);
		metaCheck.setBounds(375, 100, 150, 30);
		algorithmCombo.setBounds(350, 50, 150, 30);
		folderNameLabel.setBounds(200, 150, 1500, 30);
		fileNameLabel.setBounds(200, 175, 1500, 30);
		hashLabel.setBounds(200, 200, 400, 30);
		setLayout(null);
	}

	public JMenuBar setupMenu() {
		ActionListener selector = new FileListener();

		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");

		menuBar.add(file);

		JMenuItem about = new JMenuItem("About");
		JMenuItem exit = new JMenuItem("Exit");
		selectFile = new JMenuItem("Select File");
		selectFolder = new JMenuItem("Select Folder");

		file.add(selectFile);
		file.add(selectFolder);
		file.add(about);
		file.add(exit);

		selectFile.addActionListener(selector);
		selectFolder.addActionListener(selector);

		exit.addActionListener(new ExitListener());
		about.addActionListener(new AboutListener());

		return menuBar;
	}

	private class AboutListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			ImageIcon abouImg = new ImageIcon("aboutBig.png");
			String description = "This program is a GUI based application designed to support conversion between different units. \n Author: Riju Manandhar (77189982) \n \u00a9 All rights reserved. 2018.";
			JOptionPane.showMessageDialog(null, description, "About", JOptionPane.INFORMATION_MESSAGE, abouImg);
		}
	}

	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
			// System.exit(0) terminates the JVM therefore terminating the program.
		}
	}

	private class FileListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			clear();

			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(".")); // sets the current directory to the project's directory
			fc.setDialogTitle("Choose File");

			if (event.getSource() == selectFile) {
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);// only files can be selected
				fc.showOpenDialog(null);

				filename = fc.getSelectedFile();
				filepath = filename.getPath();

				fileNameLabel.setText("Filename: " + filename.getName());
			} else {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// only files can be selected
				fc.showOpenDialog(null);

				foldername = fc.getSelectedFile();
				filepath = foldername.getPath();

				folderNameLabel.setText("Foldername: " + foldername.getName());

				files = foldername.listFiles();
				String temp = "Filename: ";
				for (File f : files) {
					temp = temp + f.getName() + ", ";
				}
				fileNameLabel.setText(temp);
			}
		}
	}

	public void clear() {
		files = null;
		filename = null;
		foldername = null;
	}

	public void reader() {
		String line = "";

		filepathList = new ArrayList<>();
		hashList = new ArrayList<>();
		algoList = new ArrayList<>();
		isMetaList = new ArrayList<>();

		try {
			File dataFile = new File("data.txt");
			Scanner fileReader = new Scanner(dataFile);
			while (fileReader.hasNext()) {
				line = fileReader.nextLine();
				String[] temp = line.split(" ");// splits the content of the file using space as token
				try {
					algoList.add(Integer.parseInt(temp[0]));
					filepathList.add(temp[1]);
					isMetaList.add(Boolean.parseBoolean(temp[2]));
					hashList.add(temp[3]);
				} catch (Exception e) {

				}

			}
			fileReader.close();
		} catch (Exception e) {

		}

	}

	public void search(int algo, String filepath, boolean isMeta, String hash) {
		reader();
		boolean check = false;

		for (int i = 0; i < filepathList.size(); i++) {
			if (filepathList.get(i).equals(filepath) && algoList.get(i).equals(algo) && isMetaList.get(i).equals(isMeta)
					&& !hashList.get(i).equals(hash)) {
				String message = "The File " + filepathList.get(i) + " Has Been Tampered";
				JOptionPane.showMessageDialog(null, message, "File Tampered", JOptionPane.WARNING_MESSAGE);
				hashList.add(i, hash);
				check = true;
				hashList.add(i, hash);
				// System.out.println(i);
			} else if (filepathList.get(i).equals(filepath) && algoList.get(i).equals(algo)
					&& isMetaList.get(i).equals(isMeta) && hashList.get(i).equals(hash)) {
				String message = "Hash Already Exists";
				JOptionPane.showMessageDialog(null, message, "Hash Exists", JOptionPane.WARNING_MESSAGE);
				check = true;
			}

		}

		if (check == false) {
			algoList.add(algo);
			filepathList.add(filepath);
			isMetaList.add(isMeta);
			hashList.add(hash);
		}

		filewrite(algoList, filepathList, isMetaList, hashList);
	}

	private void filewrite(List<Integer> algoList, List<String> filepathList, List<Boolean> isMetaList,
			List<String> hashList) {

		try {
			File dataFile = new File("data.txt");
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
			FileWriter fw = new FileWriter(dataFile.getName(), false);
			for (int i = 0; i < this.filepathList.size(); i++) {
				fw.write(algoList.get(i) + " " + filepathList.get(i) + " " + isMetaList.get(i) + " " + hashList.get(i)
						+ System.lineSeparator());
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class LoadListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			int opt = algorithmCombo.getSelectedIndex();
			System.out.println(opt);

			boolean isMeta = false;

			String hash = "";

			AlgorithmOne algorithm1 = new AlgorithmOne();
			AlgorithmTwo algorithm2 = new AlgorithmTwo();
			AlgorithmThree algorithm3 = new AlgorithmThree();

			if (filename != null || files != null) {

				if (opt == 0 && filename != null && !metaCheck.isSelected()) {
					hash = algorithm1.produceFileHash(filename);
				} else if (opt == 0 && files != null && metaCheck.isSelected()) {
					hash = algorithm1.produceDirMetaHash(files);
					isMeta = true;
				} else if (opt == 0 && files != null && !metaCheck.isSelected()) {
					hash = algorithm1.produceDirHash(files);
				}

				if (opt == 1 && filename != null && !metaCheck.isSelected()) {
					hash = algorithm2.produceFileHash(filename);
				} else if (opt == 1 && files != null && metaCheck.isSelected()) {
					hash = algorithm2.produceDirMetaHash(files);
					isMeta = true;
				} else if (opt == 1 && files != null && !metaCheck.isSelected()) {
					hash = algorithm2.produceDirHash(files);
				}

				if (opt == 2 && filename != null && !metaCheck.isSelected()) {
					hash = algorithm3.produceFileHash(filename);
				} else if (opt == 2 && files != null && metaCheck.isSelected()) {
					hash = algorithm3.produceDirMetaHash(files);
					isMeta = true;
				} else if (opt == 2 && files != null && !metaCheck.isSelected()) {
					hash = algorithm3.produceDirHash(files);
				}

				hashLabel.setText("Hash Generated: " + hash);

				search(opt, filepath, isMeta, hash);

			} else {
				String message = "No File Selected. Select a file and Try Again";
				JOptionPane.showMessageDialog(null, message, "No File Selected", JOptionPane.WARNING_MESSAGE);
			}

		}

	}

}
