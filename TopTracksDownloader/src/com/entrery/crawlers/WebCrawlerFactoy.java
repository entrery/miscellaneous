package com.entrery.crawlers;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;

public final class WebCrawlerFactoy {

	public static final WebCrawlerFactoy INSTANCE = new WebCrawlerFactoy();
	
	private WebCrawlerFactoy() {
	}
	
	public IWebCrawler<String> createDownloadLinkFinder(String youtubeUrl) {
		return new DownloadLinkFinder(new WebClient(), "http://www.youtube-mp3.org", youtubeUrl);
	}
	
	public IWebCrawler<List<String>> createRadioFreshTop30Finder() {
		return new RadioFreshTop30Finder(new WebClient(), "http://radiofresh.bg/top30.php");
	}
}
