package com.entrery.crawlers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class AbstractWebCrawler<E> implements IWebCrawler<E>{

	private WebClient webClient;
	private String baseUrl;
	
	public AbstractWebCrawler(WebClient webClient, String baseUrl) {
		this.webClient = webClient;
		this.baseUrl = baseUrl;
	}
	
	public WebClient getWebClient() {
		return webClient;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public E crawl() {
		E result = null;
		
		try {
			HtmlPage page = webClient.getPage(baseUrl);
			result = hook(page);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			closeAll();
		}
	
		return result;
	}
	
	protected abstract E hook(HtmlPage page) throws Exception;
	
	private void closeAll() {
		if(webClient != null) {
			webClient.closeAllWindows();
		}
	}
}
