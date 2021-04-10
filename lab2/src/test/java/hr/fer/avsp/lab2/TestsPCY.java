package hr.fer.avsp.lab2;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TestsPCY extends TestsBase {

	@Test
	public void test01() throws IOException {
		String inputPath = "examples/test2/R.in";
		String outputPath = "examples/test2/R.out";
		runTest(inputPath, outputPath);
	}

	@Override
	protected void runProgram() throws IOException {
		PCY.main(new String[] {});
	}
}
