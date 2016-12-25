package com.entrery.wavelets;

public class Debuchies2WaveletTransformer {

	/* Symmetry due to orthogonality */
	
	private static final double s0 = (1 + Math.sqrt(3)) / (4 * Math.sqrt(2));
	private static final double s1 = (3 + Math.sqrt(3)) / (4 * Math.sqrt(2));
	private static final double s2 = (3 - Math.sqrt(3)) / (4 * Math.sqrt(2));
	private static final double s3 = (1 - Math.sqrt(3)) / (4 * Math.sqrt(2));
	
	private static final double w0 = s3;
	private static final double w1 = -s2;
	private static final double w2 = s1;
	private static final double w3 = -s0;

	private static double[] scallingCoefficients = {s0, s1, s2, s3};
	private static double[] scallingRecCoefficients = scallingCoefficients;
	private static double[] decompositionCoefficients = {w0, w1, w2, w3};
	private static double[] decompositionRecCoefficients = decompositionCoefficients;
	
	public static double[] forward(double[] data) {

		double[] result = new double[data.length];

		int h = result.length >> 1;
		for (int i = 0; i < h; i++) {

			result[i] = result[i + h] = 0.;

			for (int j = 0; j < 4; j++) {

				int k = (i * 2) + j;
				while (k >= result.length)
					k -= result.length;

				result[i] += data[k] * scallingCoefficients[j];
				result[i + h] += data[k] * decompositionCoefficients[j];
			}
		}
		return result;
	}

	public static double[] reverse(double[] data) {

		double[] result = new double[data.length];
		for (int i = 0; i < result.length; i++)
			result[i] = 0.;

		int h = result.length >> 1;
		for (int i = 0; i < h; i++) {

			for (int j = 0; j < 4; j++) {

				int k = (i * 2) + j;
				while (k >= result.length)
					k -= result.length;

				result[k] += (data[i] * scallingRecCoefficients[j])
						+ (data[i + h] * decompositionRecCoefficients[j]);
			}
		}
		return result;
	}

	public static void forward(double[][] data, int iterations) {
		int rows = data.length;
		int cols = data[0].length;
	
		double[] row = new double[cols];
		double[] col = new double[rows];
	
		for (int k = 0; k < iterations; k++) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < row.length; j++)
					row[j] = data[i][j];
	
				row = forward(row);
				
				for (int j = 0; j < row.length; j++)
					data[i][j] = row[j];
			}
	
			for (int j = 0; j < cols; j++) {
				for (int i = 0; i < col.length; i++)
					col[i] = data[i][j];
	
				col = forward(col);
	
				for (int i = 0; i < col.length; i++)
					data[i][j] = col[i];
			}
		}
	}

	public static void reverse(double[][] data, int iterations) {
		int rows = data.length;
		int cols = data[0].length;

		double[] col = new double[rows];
		double[] row = new double[cols];

		for (int l = 0; l < iterations; l++) {
			for (int j = 0; j < cols; j++) {
				for (int i = 0; i < row.length; i++)
					col[i] = data[i][j];

				col = reverse(col);
				
				for (int i = 0; i < col.length; i++)
					data[i][j] = col[i];
			}

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < row.length; j++)
					row[j] = data[i][j];

				row = reverse(row);
				
				for (int j = 0; j < row.length; j++)
					data[i][j] = row[j];
			}
		}
	}

	public static void reduceQuadrant(double[][] data, int rowStart,
			int rowEnd, int colStart, int colEnd) {
		for (int i = rowStart; i < rowEnd; i++) {
			for (int j = colStart; j < colEnd; j++) {
				data[i][j] = 0.0;
			}
		}
	}

	public static void useQuadrant(double[][] data, int rowStart, int rowEnd,
			int colStart, int colEnd) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (rowStart <= i && i < rowEnd && colStart <= j && j < colEnd) {
					continue;
				}
				data[i][j] = 0.0;
			}
		}
	}
}