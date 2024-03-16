package com.kif.deckservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix ="com.kif")
@ConfigurationPropertiesScan 
public class AppProperties {
	
	private String apikey;
	private String cardDetailsTemplate;
	private String deckListTemplate;
	private String decklistJSONFormat;
	private String manaPath;
	private String sharedsecret;
	private String siteTitle;
	
	public AppProperties(String apikey, String cardDetailsTemplate, String deckListTemplate, String decklistJSONFormat,
			String manaPath, String sharedsecret, String siteTitle) {
		super();
		this.apikey = apikey;
		this.cardDetailsTemplate = cardDetailsTemplate;
		this.deckListTemplate = deckListTemplate;
		this.decklistJSONFormat = decklistJSONFormat;
		this.manaPath = manaPath;
		this.sharedsecret = sharedsecret;
		this.siteTitle = siteTitle;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getCardDetailsTemplate() {
		return cardDetailsTemplate;
	}

	public void setCardDetailsTemplate(String cardDetailsTemplate) {
		this.cardDetailsTemplate = cardDetailsTemplate;
	}

	public String getDeckListTemplate() {
		return deckListTemplate;
	}

	public void setDeckListTemplate(String deckListTemplate) {
		this.deckListTemplate = deckListTemplate;
	}

	public String getDecklistJSONFormat() {
		return decklistJSONFormat;
	}

	public void setDecklistJSONFormat(String decklistJSONFormat) {
		this.decklistJSONFormat = decklistJSONFormat;
	}

	public String getManaPath() {
		return manaPath;
	}

	public void setManaPath(String manaPath) {
		this.manaPath = manaPath;
	}

	public String getSharedsecret() {
		return sharedsecret;
	}

	public void setSharedsecret(String sharedsecret) {
		this.sharedsecret = sharedsecret;
	}

	public String getSiteTitle() {
		return siteTitle;
	}

	public void setSiteTitle(String siteTitle) {
		this.siteTitle = siteTitle;
	}

	public AppProperties() {
		// TODO Auto-generated constructor stub
	}

}
