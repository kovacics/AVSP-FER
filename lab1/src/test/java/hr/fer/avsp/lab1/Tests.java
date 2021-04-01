package hr.fer.avsp.lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests {

	static final String tempFilePath = "results_temp.txt";
	long start, end;

	@BeforeEach
	public void beforeEach() throws FileNotFoundException {
		System.setOut(new PrintStream(tempFilePath));
		start = System.currentTimeMillis();
	}

	@Test
	public void task01Test01() throws IOException, DecoderException {
		String inputPath = "examples/lab1A/test2/R.in";
		String outputPath = "examples/lab1A/test2/R.out";
		runSimHashTest(inputPath, outputPath);
	}

	@Test
	public void task02Test01() throws IOException, DecoderException {
		String inputPath = "examples/lab1B/test0/R.in";
		String outputPath = "examples/lab1B/test0/R.out";
		runSimHashBucketsTest(inputPath, outputPath);
	}

	@Test
	public void task02Test02() throws IOException, DecoderException {
		String inputPath = "examples/lab1B/test2/R.in";
		String outputPath = "examples/lab1B/test2/R.out";
		runSimHashBucketsTest(inputPath, outputPath);
	}

	@AfterEach
	public void afterEach() {
		end = System.currentTimeMillis();
		System.err.println("Done in " + (end - start) / 1000.0 + " s.");
		new File(tempFilePath).delete();
	}

	@AfterAll
	public static void afterAll() {
		System.setIn(System.in);
		System.setOut(System.out);
	}

	private void runSimHashTest(String inputPath, String outputPath) throws IOException, DecoderException {
		System.setIn(new FileInputStream(inputPath));
		SimHash.main(new String[] {});
		assertTrue(Testing.compareFiles(outputPath, tempFilePath));
	}

	private void runSimHashBucketsTest(String inputPath, String outputPath) throws IOException, DecoderException {
		System.setIn(new FileInputStream(inputPath));
		SimHashBuckets.main(new String[] {});
		assertTrue(Testing.compareFiles(outputPath, tempFilePath));
	}

}
