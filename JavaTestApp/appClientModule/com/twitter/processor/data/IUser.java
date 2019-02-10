package com.twitter.processor.data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IUser extends Serializable{
	
	public String getName();
	
	public void setName(String name);
	
	public Set<String> getFollowers() ;
	
	public void setFollowers(Set<String> followers);
	
	public void addFollower(String followerName);
	
	public void removeFollower(String followerName);
	
	public boolean equals(Object obj);
	
	public int hashCode();
	
}
