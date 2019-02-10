package com.twitter.processor.data;

import java.io.Serializable;

public interface ITweet extends Serializable{
	
	public String getSender();
	
	public void setSender(String sender);
	
	public String getContent();
	
	public void setContent(String content);
	
	public boolean equals(Object obj);
	
	public int hashCode();
}
