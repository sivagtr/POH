/**
 * 
 */
package com.mssr;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author Siva Srinivasa Rao Mothukuri
 * 
 */
public class POH {

	private String words32[] = new String[32];
	@SuppressWarnings("unused")
	private int length = 0;
	private String words16[] = new String[16];
	private String words32Orig[] = new String[32];
	private String initial[] = new String[16];
	private String chuncks[];
	private String finalOutput[] = new String[16];

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public String performOperation(String input)
			throws UnsupportedEncodingException {

		String output = "";
		try {
			POHParser(input);
		} catch (Exception e) {
			System.out.println("Exception has occured " + e.getMessage());
			e.printStackTrace();
		}
		output = fill();
		return output;
	}

	private String fill() {
		String op = "";
		for (int i = 0; i < 16; i++)
			op += finalOutput[i];
		return op;
	}

	private String stringToHex(String str) throws UnsupportedEncodingException {
		return String.format("%x", new BigInteger(1, str.getBytes("UTF8")));
	}

	private String decToHex(int dec) {
		return Integer.toHexString(dec);
	}

	private void POHParser(String input) throws UnsupportedEncodingException {
		String padded = getPadding(input);
		getChunks(padded);
		for (int i = 0; i < chuncks.length; i++) {
			if (i == 0) {
				fillInitialvalues(1);
			} else {
				fillInitialvalues(2);
			}
			getWords(chuncks[i]);
			doPhaseI();
			doPhaseII();
			doPhaseIII();
		}
	}

	private void fillInitialvalues(int i) {
		// TODO Auto-generated method stub

		switch (i) {
		case 1:
			initial[0] = "01234567";
			initial[1] = "89abcdef";
			initial[2] = "10325476";
			initial[3] = "98BADCFE";
			initial[4] = "0A1B2C3D";
			initial[5] = "4E5F6978";
			initial[6] = "02467531";
			initial[7] = "8ACEFDB9";
			initial[8] = "0123ABCD";
			initial[9] = "4567EF89";
			initial[10] = "ACE02468";
			initial[11] = "BDF13579";
			initial[12] = "FEBA7632";
			initial[13] = "DC985410";
			initial[14] = "E1D2C3B2";
			initial[15] = "C9B8A776";
			break;
		case 2:
			initial = finalOutput.clone();
			break;
		}
	}

	private void getChunks(String padded) {
		chuncks = splitByLength(padded, 256);
	}

	public String[] splitByLength(String s, int chunkSize) {
		int arraySize = (int) Math.ceil((double) s.length() / chunkSize);

		String[] returnArray = new String[arraySize];

		int index = 0;
		for (int i = 0; i < s.length(); i = i + chunkSize) {
			if (s.length() - i < chunkSize) {
				returnArray[index++] = s.substring(i);
			} else {
				returnArray[index++] = s.substring(i, i + chunkSize);
			}
		}

		return returnArray;
	}

	private void doPhaseIII() {

		String[] output = new String[32];

		for (int i = 0; i < 512; i++) {
			for (int k = 0; k < 16; k++) {
				output[k] = null;
			}
			output[4] = functionOperations(initial[i % 16], words16[i % 16],
					i % 3);

			int counter = 1;
			for (int j = 0; j < 16; j++) {
				if (output[counter % 16] == null) {
					output[counter % 16] = words16[j];

				}
				counter++;
			}
			for (int u = 0; u < 16; u++) {
				int sz = output[u].length();
				if (sz <= 8) {
					int k = 8 - sz;
					for (int l = 0; l < k; l++) {
						output[u] = "0" + output[u];
					}
				} else {
					System.out.println("Size exceeded");
					System.exit(1);
				}
			}
			words16 = output.clone();
			int c = 15;
			for (int u = 0; u < 16; u++) {
				words16[(u + 3) % 16] = bitWiseOperation(words32Orig[u % 16],
						words16[c--], 0);
				int sz = words16[u].length();
				if (sz <= 8) {
					int k = 8 - sz;
					for (int l = 0; l < k; l++) {
						words16[u] = "0" + words16[u];
					}
				} else {
					System.out.println("Size exceeded");
					System.exit(1);
				}
			}
			finalOutput = words16.clone();
		}

	}

	private void doPhaseII() {
		// TODO Auto-generated method stub

		for (int i = 0; i < 16; i++) {
			words16[i] = words32[16 + i];
		}
		int count = 31;
		for (int i = 0; i < 16; i++) {
			words16[i] = bitWiseOperation(words32[i], words32[count], 0);
			if (i % 2 == 0) {
				words16[i] = bitWiseOperation(initial[i % 3], words16[i], 2);
			} else {
				words16[i] = bitWiseOperation(initial[i % 4], words16[i], 2);
			}
			count--;
		}
		for (int u = 0; u < 16; u++) {
			int sz = words16[u].length();
			if (sz <= 8) {
				int k = 8 - sz;
				for (int l = 0; l < k; l++) {
					words16[u] = "0" + words16[u];
				}
			} else {
				System.out.println("Size exceeded");
				System.exit(1);
			}

		}
		for (int u = 0; u < 16; u++) {

			words16[u] = bitWiseOperation(words32Orig[u % 16], words16[u], 0);
			int sz = words16[u].length();
			if (sz <= 8) {
				int k = 8 - sz;
				for (int l = 0; l < k; l++) {
					words16[u] = "0" + words16[u];
				}
			} else {
				System.out.println("Size exceeded");
				System.exit(1);
			}
		}

	}

	private String bitWiseOperation(String word1, String word2, int operation) {

		BigInteger a = new BigInteger(word1, 16);
		BigInteger b = new BigInteger(word2, 16);

		BigInteger result = null;
		switch (operation) {
		case 0:
			result = a.xor(b);
			break;
		case 1:
			result = a.and(b);
			break;
		case 2:
			result = a.andNot(b);
			break;
		case 3:
			result = a.shiftRight(12);
			break;
		case 4:
			result = a.shiftRight(4);
			break;
		case 5:
			result = a.shiftRight(6);
			break;
		}

		return result.toString(16);
	}

	private String functionOperations(String word1, String word2, int operation) {
		String result = null;
		switch (operation) {
		case 0:
			result = bitWiseOperation(word1, word2, 1);
			result = bitWiseOperation(result, word2, 3);
			result = bitWiseOperation(word1, result, 2);
			break;
		case 1:
			result = bitWiseOperation(word1, word2, 3);
			result = bitWiseOperation(word1, result, 2);
			break;
		case 2:
			result = bitWiseOperation(word1, word2, 1);
			result = bitWiseOperation(result, word2, 3);
			break;
		}

		return result;
	}

	private void doPhaseI() {

		String[] output = new String[32];

		for (int i = 0; i < 514; i++) {
			for (int k = 0; k < 32; k++) {
				output[k] = null;
			}
			output[4] = bitWiseOperation(initial[i % 16], words32[i % 32], 0);
			output[5] = bitWiseOperation(words32[4], "0", 4);
			output[7] = bitWiseOperation(initial[i % 6], words32[6], 2);
			output[10] = bitWiseOperation(initial[i % 13], words32[19], 1);
			output[11] = bitWiseOperation(words32[6], words32[7], 1);
			output[12] = bitWiseOperation(initial[i % 5], words32[9], 3);
			output[13] = bitWiseOperation(words32[0], words32[27], 2);
			output[16] = bitWiseOperation(initial[i % 2], words32[14], 1);
			output[21] = bitWiseOperation(words32[16], "0", 5);
			output[23] = bitWiseOperation(initial[i % 4], words32[19], 1);
			output[27] = bitWiseOperation(initial[i % 9], words32[30], 3);

			int counter = 1;
			for (int j = 0; j < 32; j++) {
				if (output[counter % 32] == null) {
					output[counter % 32] = words32[j];

				}
				counter++;
			}
			for (int u = 0; u < 32; u++) {

				int sz = output[u].length();
				if (sz <= 8) {
					int k = 8 - sz;
					for (int l = 0; l < k; l++) {
						output[u] = "0" + output[u];
					}
				} else {
					System.out.println("Size exceeded");
					System.exit(1);
				}
			}
			words32 = output.clone();
		}

		for (int u = 0; u < 32; u++) {

			words32[u] = bitWiseOperation(words32Orig[u], words32[u], 0);
			int sz = words32[u].length();
			if (sz <= 8) {
				int k = 8 - sz;
				for (int l = 0; l < k; l++) {
					words32[u] = "0" + words32[u];
				}
			} else {
				System.out.println("Size exceeded");
				System.exit(1);
			}
		}
	}

	private void getWords(String chunck) {
		words32 = splitByLength(chunck, 8);
		words32Orig = words32.clone();
	}

	private String getPadding(String input) throws UnsupportedEncodingException {

		String hex = stringToHex(input);
		String paddingbit = "";
		int x;
		int c;
		int length = input.length();
		this.length = input.length();
		if (length > 255) {
			x = length / 255;
			c = length % (x * 255);
			paddingbit = decToHex(c);

		} else {
			c = length % (255);
			paddingbit = decToHex(c);
		}
		if (paddingbit.length() == 1) {
			paddingbit = "0" + paddingbit;
		}
		int l = hex.length();
		int pad;
		if (l > 256) {
			int y = l / 256;
			pad = (y * 256) - l;
			if (pad < 0) {
				pad = ((y + 1) * 256) - l;
			}
		} else {
			pad = 256 - l;
		}
		for (int i = 0; i < (pad / 2); i++) {
			hex = hex + paddingbit;
		}
		return hex;
	}
}
