package hr.fer.avsp.lab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class SimHash {

	public static void main(String[] args) throws IOException, DecoderException {
		task();
	}

	private static void task() throws IOException, FileNotFoundException, DecoderException {

		ArrayList<String> hashes = new ArrayList<>();

		try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
			int N = Integer.parseInt(r.readLine());
			while (N > 0) {
				String line = r.readLine();
				hashes.add(getSimHash(line));
				N--;
			}

			int Q = Integer.parseInt(r.readLine());
			while (Q > 0) {
				String[] line = r.readLine().split(" ");
				int I = Integer.parseInt(line[0]);
				int K = Integer.parseInt(line[1]);

				int similarDocs = countSimilar(hashes, I, K);
				System.out.println(similarDocs);
				Q--;
			}
		}
	}

	public static String getSimHash(String input) {
		int[] finalHash = new int[128];

		for (String word : input.split(" ")) {
			byte[] md5 = DigestUtils.md5(word);
			int currentBitIndex = 0;

			for (byte b : md5) {
				for (int i = 7; i >= 0; i--) {
					int currentBit = b >> i & 0x01;
					finalHash[currentBitIndex++] += currentBit == 1 ? 1 : -1;
				}
			}
		}

		for (int i = 0; i < finalHash.length; i++) {
			finalHash[i] = finalHash[i] >= 0 ? 1 : 0;
		}

		return Hex.encodeHexString(intBitArrayToByteArray(finalHash));
	}

	private static int countSimilar(List<String> hashes, int I, int K) throws DecoderException {
		int count = 0;
		String queryHash = hashes.get(I);
		for (int j = 0; j < hashes.size(); j++) {
			if (j != I && hammingDistance(queryHash, hashes.get(j)) <= K) {
				count++;
			}
		}
		return count;
	}

	private static int hammingDistance(String hex1, String hex2) throws DecoderException {
		int distance = 0;
		byte[] bytes1 = Hex.decodeHex(hex1);
		byte[] bytes2 = Hex.decodeHex(hex2);

		for (int i = 0; i < bytes1.length; i++) {
			byte b1 = bytes1[i];
			byte b2 = bytes2[i];
			int xored = b1 ^ b2;

			for (int j = 7; j >= 0; j--) {
				if ((xored >> j & 0x01) == 1) {
					distance++;
				}
			}
		}

		return distance;
	}

	private static byte[] intBitArrayToByteArray(int[] bits) {

		byte[] bytes = new byte[(int) Math.ceil(bits.length / 8.0)];

		int currentWeight = 1;
		int currentByteIndex = bytes.length - 1;
		byte sum = 0;

		for (int i = bits.length - 1; i >= 0; i--) {
			sum += bits[i] * currentWeight;
			currentWeight *= 2;

			if (i != bits.length - 1 && (bits.length - i) % 8 == 0) {
				bytes[currentByteIndex--] = sum;
				sum = 0;
				currentWeight = 1;
			}
		}

		if (bits.length % 8 != 0) {
			bytes[currentByteIndex] = sum;
		}

		return bytes;
	}

}
