package com.twitter.processor.data;

import java.util.Set;
import java.util.TreeSet;

public class User implements IUser{

	private static final long serialVersionUID = 1L;
	private String name;
	private Set<String> followers;
	
	public User(String name) {
		this.name = name;
		this.followers = new TreeSet<>();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Set<String> getFollowers() {
		return followers;
	}
	
	@Override
	public void setFollowers(Set<String> followers) {
		this.followers = followers;
	}
	
	@Override
	public void addFollower(String followerName) {
		this.followers.add(followerName);
	}
	
	@Override
	public void removeFollower(String followerName) {
		this.followers.remove(followerName);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((followers == null) ? 0 : followers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (followers == null) {
			if (other.followers != null)
				return false;
		} else if (!followers.equals(other.followers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", followers=" + followers + "]";
	}
	
}
