package com.entrery.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadThread extends NotifyingThread {

	private static final String DOWNLOAD_LOCATION = System.getProperty("download_location");

	private String downloadUrl;
	private int index;
	
	public DownloadThread(String downloadUrl, int index) {
		this.downloadUrl = downloadUrl;
		this.index = index;
	}
	
	@Override
	public void doRun() {
		downloadMp3(downloadUrl);
	}

	private void downloadMp3(String downloadUrl) {
		try {
			URLConnection conn = new URL(downloadUrl).openConnection();
		    InputStream is = conn.getInputStream();
	
		    OutputStream outstream = new FileOutputStream(new File(DOWNLOAD_LOCATION + "/" + index + ".mp3"));
		    byte[] buffer = new byte[4096];
		    int len;
		    while ((len = is.read(buffer)) > 0) {
		        outstream.write(buffer, 0, len);
		    }
		    outstream.close();
		} catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}
