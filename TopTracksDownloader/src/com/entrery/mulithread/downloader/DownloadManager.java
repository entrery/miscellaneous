package com.entrery.mulithread.downloader;

import java.util.List;

import com.entrery.threads.DownloadThread;
import com.entrery.threads.ThreadCompleteListener;

public class DownloadManager implements ThreadCompleteListener {

	private List<String> downloadUrls;
	private int index = 0;
	
	public DownloadManager(List<String> downloadUrls) {
		this.downloadUrls = downloadUrls;
	}
	
	public void downloadTracks() {
		index += 4;
		
		for (int i = 0; i < 4; i++) {
			startNewDownload(i);
		}
	}

	@Override
	public synchronized void notifyOfThreadComplete(Thread thread) {
		if(index < downloadUrls.size()) {
			startNewDownload(index);
			index++;
		}
	}

	private void startNewDownload(int index) {
		DownloadThread downloadThread = new DownloadThread(downloadUrls.get(index), index);
		downloadThread.addListener(this);
		downloadThread.start();
	}
}
