package com.entrery.wavelets;


public class Main2 {

	public static void main(String[] args) throws Exception {
			 String fileLocation = System.getProperty("location");
 			 String iterIn = System.getProperty("iterations");
 			  
 			 int iterations = Integer.parseInt(iterIn);
			 System.out.println("Transformation started...");
			 
			 ImageUtil imageUtil = new ImageUtil(fileLocation);
			  
			 double[][] data = imageUtil.load("Greedy_recon.png");
			 HaarWaveletTransformer.forward(data, iterations);
			 
			 String quadrant = System.getProperty("useQuadrant");
			 
			 if(quadrant != null) {
				 int quadrantInt = Integer.parseInt(quadrant); 

				 if(quadrantInt == 1) {
					 /* Approximation */
					 HaarWaveletTransformer.useQuadrant(data, 0, data.length / ((int)Math.pow(2 , iterations)), 0, data[0].length / ((int)Math.pow(2 , iterations)));
				 }
				
				 if(quadrantInt == 2) {
					 /* X coefficient */
					 HaarWaveletTransformer.useQuadrant(data, 0, data.length / ((int)Math.pow(2 , iterations)), data[0].length / ((int)Math.pow(2 , iterations)), data[0].length);
				 }

				 if(quadrantInt == 3) {
					 /* Y coefficient */
					 HaarWaveletTransformer.useQuadrant(data, data.length / ((int)Math.pow(2 , iterations)), data.length, 0,data[0].length / ((int)Math.pow(2 , iterations)));
				 }

				 if(quadrantInt == 4) {
					 /* Diagonal Coefficient */
					 HaarWaveletTransformer.useQuadrant(data, data.length / ((int)Math.pow(2 , iterations)), data.length, data[0].length / ((int)Math.pow(2 , iterations)), data[0].length);
				 }
				 
				 /*
			     double[][] approximation = new double[data.length / 2][data.length / 2];
				 double[][] differencesX = new double[data.length / 2][data.length / 2];
				 double[][] differencesY = new double[data.length / 2][data.length / 2];
				 double[][] differencesD = new double[data.length / 2][data.length / 2];
				 
				 fourPieceForward(data, approximation, differencesX, differencesY, differencesD);
			 */
			 }
			 
			 imageUtil.save(data, "FWT");
			 HaarWaveletTransformer.reverse(data, iterations);
			 imageUtil.save(data, "IWT");
			 
			 System.out.println("Transformation finished.");
	}
}
