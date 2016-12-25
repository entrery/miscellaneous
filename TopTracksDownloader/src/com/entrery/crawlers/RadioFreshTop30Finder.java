package com.entrery.crawlers;

import java.util.ArrayList;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class RadioFreshTop30Finder extends AbstractWebCrawler<List<String>> {

	private static final String TOP30_DIV_ID = "top30";

	public RadioFreshTop30Finder(WebClient webClient, String baseUrl) {
		super(webClient, baseUrl);
	}

	@Override
	protected List<String> hook(HtmlPage page) throws Exception {
		HtmlDivision div = (HtmlDivision) page.getElementById(TOP30_DIV_ID);
		Iterable<DomNode> children = div.getFirstChild().getChildNodes();

		List<String> top30Tracks = new ArrayList<>();
		 
	    for (DomNode node : children) {
	    	if(node instanceof HtmlAnchor) {
	    		HtmlAnchor href = (HtmlAnchor) node;
	    		top30Tracks.add(href.getHrefAttribute());
	    	}
		}
	    
		return top30Tracks;
	}
}
