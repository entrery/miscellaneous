package com.entrery.wavelets;


public class HaarWaveletTransformer {

	/* Haar or (Debuchies 1) coefficients */

	/* Scaling coefficients */
	private static final double s0 = Math.sqrt(0.5);
	private static final double s1 = Math.sqrt(0.5);

	/* Decomposition coefficients */
	private static final double w0 = Math.sqrt(0.5);
	private static final double w1 = -Math.sqrt(0.5);

	public static void forward(double[] data) {
		double[] temp = new double[data.length];

		int h = data.length >> 1;
		for (int i = 0; i < h; i++) {
			int k = (i << 1);
			temp[i] = data[k] * s0 + data[k + 1] * s1;
			temp[i + h] = data[k] * w0 + data[k + 1] * w1;
		}

		for (int i = 0; i < data.length; i++)
			data[i] = temp[i];
	}

	public static void forwardFast(double[] data) {
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

				forward(row);

				for (int j = 0; j < row.length; j++)
					data[i][j] = row[j];
			}

			for (int j = 0; j < cols; j++) {
				for (int i = 0; i < col.length; i++)
					col[i] = data[i][j];

				forward(col);

				for (int i = 0; i < col.length; i++)
					data[i][j] = col[i];
			}
		}
	}

	public static void fourPieceForward(double[][] data,
			double[][] approximation, double[][] differencesX,
			double[][] differencesY, double[][] differencesD) {

		forward(data, 1);

		fill(data, approximation, 0, data.length / 2, 0, data[0].length / 2);
		fill(data, differencesX, 0, data.length / 2, data[0].length / 2, data[0].length);
		fill(data, differencesY, data.length / 2, data.length, 0, data[0].length / 2);
		fill(data, differencesD, data.length / 2, data.length, data[0].length / 2, data[0].length);
	}

	public static void fill(double[][] data, double[][] result, int rowStart,
			int rowEnd, int colStart, int colEnd) {
		int k = 0, p = 0;
		for (int i = rowStart; i < rowEnd; i++) {
			for (int j = colStart; j < colEnd; j++) {
				if (p == Math.abs(colEnd - colStart)) {
					p = 0;
					k++;
				}
				
				result[k][p] = data[i][j];
				p++;
			}
		}
	}

	public static void reverse(double[] data) {
		double[] temp = new double[data.length];

		int h = data.length >> 1;
		for (int i = 0; i < h; i++) {
			int k = (i << 1);
			temp[k] = (data[i] * s0 + data[i + h] * w0);
			temp[k + 1] = (data[i] * s1 + data[i + h] * w1);
		}

		for (int i = 0; i < data.length; i++)
			data[i] = temp[i];
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

				reverse(col);

				for (int i = 0; i < col.length; i++)
					data[i][j] = col[i];
			}

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < row.length; j++)
					row[j] = data[i][j];

				reverse(row);

				for (int j = 0; j < row.length; j++)
					data[i][j] = row[j];
			}
		}
	}

	public static void printArray(double[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}

	public static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print((int) matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
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
	
	public static double[] multiply(double[] vector, double[][] waveletMatrix) {

		if (vector.length != waveletMatrix.length) {
			throw new IllegalArgumentException();
		}

		double[] result = new double[vector.length];

		for (int i = 0; i < waveletMatrix[0].length; i++) {
			for (int j = 0; j < waveletMatrix.length; j++) {
				result[i] += vector[j] * waveletMatrix[j][i];
			}
		}
		return result;
	}

	public static double[][] transponse(double[][] waveletMatrix) {
		double[][] transponse = new double[waveletMatrix[0].length][waveletMatrix.length];
		
		for (int i = 0; i < waveletMatrix.length; i++)
			for (int j = 0; j < waveletMatrix[0].length; j++)
				transponse[j][i] = waveletMatrix[i][j];
		return transponse;
	}
}