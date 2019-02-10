package com.twitter.processor;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.twitter.processor.data.IUser;
import com.twitter.processor.data.User;
import com.twitter.processor.io.file.IInputDataIO;
import com.twitter.processor.io.file.UserRelationsFileIO;

public class UserRelationsBuilder {
	
	private String userFollowingFile = TweetsFeedProcessor.workingDirectory+"user.txt";
	private SortedMap<String,IUser> userMap = new TreeMap<>((s1,s2)-> s1.compareTo(s2));
	
	public UserRelationsBuilder(String userFollowingFile){
		this.userFollowingFile = userFollowingFile;
	}
	
	public String getUserFollowingFile() {
		return userFollowingFile;
	}

	public void setUserFollowingFile(String userFile) {
		this.userFollowingFile = userFile;
	}

	public SortedMap<String, IUser> getUserMap() {
		return userMap;
	}

	public void setUserMap(SortedMap<String, IUser> userMap) {
		this.userMap = userMap;
	}
	
	public void displayUserMap() {
		this.userMap.forEach((k,v)->System.out.println("Key : "+k+" , Value : "+v));
	}
	
	private String[] getSplit(String txt){
		String[] lines = txt.split(" ", 2);
		return lines;
	}

	private List<String> getUserRelationStrings() {
		IInputDataIO io = new UserRelationsFileIO(userFollowingFile);
		return io.readData();
	}
	
	private void addFollowersToUser(IUser user, String[] followers) {
		for(String followerString:followers) { 
			String followerStrName= followerString.trim();
			user.addFollower(followerStrName);
			if(userMap.get(followerStrName)==null){
				IUser usr = new User(followerStrName);
				userMap.put(followerStrName, usr);		
			}
			
//		    String followerStrName= followerString.trim();
//			if(userMap.get(followerStrName.trim())==null){
//				IUser followUser = new User(followerStrName);
//				userMap.put(followerStrName, followUser);		
//			}				
//			userMap.get(followerStrName).addFollower(user.getName());
		}
	}
	
	private void updateUserFollowers(IUser user,String action, String grpFollowers) {
		String[] followers = grpFollowers.split(",");
		if(action.equals("follows")) addFollowersToUser(user, followers);
		
	}
	
	public void processUserRelationships() {
		
		List<String> usrRels = getUserRelationStrings();
		
		for(String ur : usrRels) {
			String[] userActionsFollowers = getSplit(ur);
			if(userMap.get(userActionsFollowers[0])==null){
				IUser user = new User(userActionsFollowers[0]);
				userMap.put(userActionsFollowers[0], user);		
			}			
			String[] actionsFollowers = getSplit(userActionsFollowers[1]);
			updateUserFollowers(userMap.get(userActionsFollowers[0]),actionsFollowers[0],actionsFollowers[1]);			
		}
		
	}
	
	public static void main(String[] args) {
		UserRelationsBuilder br = new UserRelationsBuilder(TweetsFeedProcessor.workingDirectory+"user.txt");
		br.processUserRelationships();
		br.displayUserMap();
	}

}
