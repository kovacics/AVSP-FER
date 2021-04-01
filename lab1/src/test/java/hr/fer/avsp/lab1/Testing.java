package hr.fer.avsp.lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Testing {

	public static boolean compareFiles(String path1, String path2) throws FileNotFoundException {
		Scanner sc1 = new Scanner(new File(path1));
		Scanner sc2 = new Scanner(new File(path2));

		int currentLine = 0;
		boolean sameFiles = true;

		while (sc1.hasNextLine() && sc2.hasNextLine()) {

			String line1 = sc1.nextLine();
			String line2 = sc2.nextLine();

			if (!line1.trim().equals(line2.trim())) {
				sameFiles = false;
				System.err.println("Difference at line " + currentLine);
				System.err.println("\tfile 1: " + line1.trim());
				System.err.println("\tfile 2: " + line2.trim());
			}
			currentLine++;
		}

		if (sc1.hasNext() || sc2.hasNext()) {
			sameFiles = false;
			System.err.println("Files lengths don't match!");
		}

		if (sameFiles) {
			System.err.println("Files are identical.");
		}

		return sameFiles;

	}

}
