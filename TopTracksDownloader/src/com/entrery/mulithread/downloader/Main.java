package com.entrery.mulithread.downloader;

import java.util.ArrayList;
import java.util.List;

import com.entrery.crawlers.IWebCrawler;
import com.entrery.crawlers.WebCrawlerFactoy;

public class Main {
	
	public static void main(String[] args) {
		IWebCrawler<List<String>> radioFreshFinder = WebCrawlerFactoy.INSTANCE.createRadioFreshTop30Finder();
		List<String> youtubeUrls = radioFreshFinder.crawl();
		
		List<String> downloadUrls = new ArrayList<String>();
		for (String youtubeUrl : youtubeUrls) {
			IWebCrawler<String> linkFinder = WebCrawlerFactoy.INSTANCE.createDownloadLinkFinder(youtubeUrl);
			downloadUrls.add(linkFinder.crawl());
		}
		
		DownloadManager manager = new DownloadManager(downloadUrls);
		manager.downloadTracks();
	}
}
