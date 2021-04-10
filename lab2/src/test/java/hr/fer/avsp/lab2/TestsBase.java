package hr.fer.avsp.lab2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestsBase {
	static final String tempFilePath = "results_temp.txt";
	long start;

	@BeforeEach
	public void beforeEach() throws FileNotFoundException {
		System.setOut(new PrintStream(tempFilePath));
		start = System.currentTimeMillis();
	}

	@AfterEach
	public void afterEach() {
		long end = System.currentTimeMillis();
		System.err.println("Done in " + (end - start) / 1000.0 + " s.");
		new File(tempFilePath).delete();
	}

	@AfterAll
	public static void afterAll() {
		System.setIn(System.in);
		System.setOut(System.out);
	}

	protected void runTest(String inputPath, String outputPath) throws IOException {
		System.setIn(new FileInputStream(inputPath));
		runProgram();
		assertTrue(Testing.compareFiles(outputPath, tempFilePath));
	}

	protected abstract void runProgram() throws IOException;
}
