package com.kif.deckgenmodels;

import java.util.List;

public class ImageResult {
	Long created;
	List<Image> data;
	public ImageResult() {
		// TODO Auto-generated constructor stub
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public List<Image> getData() {
		return data;
	}
	public void setData(List<Image> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		
		
		return "ImageResult [created=" + created + ", data=" + data + "]";
	}

	
	
	
}
