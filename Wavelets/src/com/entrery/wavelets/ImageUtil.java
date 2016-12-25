package com.entrery.wavelets;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	private static final String BASE_LOCATION = "C:/Users/EnTrERy/Desktop/";

	private String fileLocation;
	
	public ImageUtil(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public ImageUtil() {
		this(BASE_LOCATION);
	}
	
	public double[][] load(String filename) throws Exception {
		File input = new File(fileLocation + filename);
		BufferedImage image = ImageIO.read(input);
		Raster raster = image.getData();

		double array[][] = new double[raster.getWidth()][raster.getHeight()];

		for (int j = 0; j < raster.getWidth(); j++) {
			for (int k = 0; k < raster.getHeight(); k++) {
				array[j][k] = raster.getSampleDouble(j, k, 0);
			}
		}
		return array;
	}

	public void save(double[][] data, String filename) throws IOException {
		BufferedImage im = new BufferedImage(data.length, data[0].length,
				BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster writableRaster = im.getRaster();
		for (int i = 0; i < data[0].length; i++)
			for (int j = 0; j < data.length; j++)
				writableRaster.setSample(i, j, 0, data[i][j]);

		ImageIO.write(im, "PNG", new File(fileLocation + filename + ".png"));
	}
}
