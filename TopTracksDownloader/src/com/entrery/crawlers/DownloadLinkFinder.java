package com.entrery.crawlers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class DownloadLinkFinder extends AbstractWebCrawler<String> {

	private static final String DOWNLOAD_LINK_HREF_ID = "dl_link";
	private static final String YOUTUBE_URL_INPUT_ID = "youtube-url";
	private static final String SUBMIT_BUTTON_VALUE = "Convert Video";
	private static final String SUBMIT_FORM_ID = "submit-form";
	
	private String youtubeUrl;

	public DownloadLinkFinder(WebClient webClient, String baseUrl, String youtubeUrl) {
		super(webClient, baseUrl);
		this.youtubeUrl = youtubeUrl;
	}

	@Override
	protected String hook(HtmlPage mainPage) throws Exception {
	    final HtmlForm form = (HtmlForm) mainPage.getElementById(SUBMIT_FORM_ID);
	    final HtmlSubmitInput button = form.getInputByValue(SUBMIT_BUTTON_VALUE);
	    final HtmlTextInput textField = (HtmlTextInput) mainPage.getElementById(YOUTUBE_URL_INPUT_ID);

	    textField.setValueAttribute(youtubeUrl);

	    final HtmlPage page2 = button.click();
	  
	    Thread.sleep(3000l);
	    
	    HtmlAnchor href = (HtmlAnchor) page2.getElementById(DOWNLOAD_LINK_HREF_ID).getFirstChild();
	    return getBaseUrl() + href.getHrefAttribute();
	}
}
