package com.entrery.wavelets;

public class Main {

	public static void main(String[] args) throws Exception {
		
			 int iterations = 3;
		
			 System.out.println("Transformation started...");
			 
			 ImageUtil imageUtil = new ImageUtil();
			 
			 double[][] data = imageUtil.load("Greedy_recon.png");
			 HaarWaveletTransformer.forward(data, iterations);
			
			 /* approximation */
			 HaarWaveletTransformer.useQuadrant(data, 0, data.length / ((int)Math.pow(2 , iterations)), 0, data[0].length / ((int)Math.pow(2 , iterations)));
			
			 /* X coefficient */
			 HaarWaveletTransformer.reduceQuadrant(data, 0, data.length / (2 * iterations), data[0].length / (2 * iterations), data[0].length);
			 
			 /* Y coefficient */
			 HaarWaveletTransformer.reduceQuadrant(data, data.length / 2, data.length, 0,data[0].length / 2);
			 
			 /* Diagonal Coefficient */
			 HaarWaveletTransformer.reduceQuadrant(data, data.length / 2, data.length, data[0].length / 2, data[0].length);
			 
			 /*
		     double[][] approximation = new double[data.length / 2][data.length / 2];
			 double[][] differencesX = new double[data.length / 2][data.length / 2];
			 double[][] differencesY = new double[data.length / 2][data.length / 2];
			 double[][] differencesD = new double[data.length / 2][data.length / 2];
			 
			 fourPieceForward(data, approximation, differencesX, differencesY, differencesD);
			 */
			 
			 imageUtil.save(data, "FWT");
			 HaarWaveletTransformer.reverse(data, iterations);
			 imageUtil.save(data, "IWT");
			 
			 System.out.println("Transformation finished.");
		
		System.out.println(4 << 1);
	}
}
