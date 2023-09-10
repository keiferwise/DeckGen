package com.kif.deckgenmodels;
import org.springframework.stereotype.Component;

@Component
public class Image {
	String url;

	public Image() {
		// TODO Auto-generated constructor stub
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Image [url=" + url + "]";
	}

}
