package com.twitter.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.twitter.processor.data.ITweet;
import com.twitter.processor.data.IUser;
import com.twitter.processor.data.Tweet;
import com.twitter.processor.io.file.IInputDataIO;
import com.twitter.processor.io.file.TweetsFeedFileIO;

public class TweetsFeedProcessor {
	
	//private String tweetFile = "/Users/arshadmayet/eclipse-workspace/JavaTestApp/tweet.txt";
	public static String workingDirectory = "C:/dev/arshad/JavaTestApp/"; 
	private String tweetFile = workingDirectory+"tweet.txt";
	private String userFollowingFile = workingDirectory+"user.txt";
	
	/**
	 * Initialises the TweetFeedProcess
	 * @param userFolloingFile
	 * @param tweetFile
	 */
	public TweetsFeedProcessor(String userFolloingFile, String tweetFile){
		this.tweetFile = tweetFile;
		this.userFollowingFile = userFolloingFile;
	}
	
	/**
	 * Load Tweets Feed
	 * @return
	 */
	private List<String> getTweetsStrings() {
		
		IInputDataIO io = new TweetsFeedFileIO(tweetFile);
		return io.readData();
	
	}
	
	/**
	 * Convert Tweet Strings to Messages
	 * @param tweets
	 * @return
	 */
	private List<ITweet> convertStringsToTweets(List<String> tweets) {
		List<ITweet> tweetMessages = new LinkedList<>();
	
		for(String twit : tweets) {
			String[] twitParts = twit.split(">");
			tweetMessages.add(new Tweet(twitParts[0], twitParts[1]));		
		}
		
		return tweetMessages;
	}
	
	private SortedMap<String,Set<String>> initialiseUserTweetsMap(Map<String,IUser> userRelationMap){
		Set<String> users = userRelationMap.keySet();
		SortedMap<String,Set<String>> userTweetsMap = new TreeMap<>((s1,s2)-> s1.compareTo(s2));
		users.forEach(j -> userTweetsMap.put(j, new LinkedHashSet<String>()));
		return userTweetsMap;
	}
	
	private SortedMap<String,Set<String>> updateUserTweetsMap(List<ITweet> tweetMessages,
			                                                  Map<String,IUser> userRelationMap,Set<String> users){
		SortedMap<String,Set<String>> userTweetsMap = initialiseUserTweetsMap(userRelationMap);
		tweetMessages.forEach(i ->{
			users.forEach(j -> {
				String sender = i.getSender();
				if((sender.equals(j))||
				  (userRelationMap.get(j).getFollowers().contains(sender))){
					userTweetsMap.get(j).add(i.toString());
				}
			});
		});
	    return userTweetsMap;
	}
	
	private boolean checkFileExists(String path){
		boolean result = false;
		File file = new File(path);
		result = file.exists();
		return result;
	}
	
	/**
	 * Main Processing Entry Point for the processing of the Tweet Feeds
	 */
	public List<String> processTweetFeed(boolean suppressOutput) {
		boolean filesExist = true;
		
		//Check if tweet file exists
		if (!checkFileExists(tweetFile)){
			filesExist = false;
			System.out.println("Tweet file does not exist at path: "+tweetFile);
		}
		
		//Check if user following file exists
		if (!checkFileExists(userFollowingFile)){
			filesExist = false;
			System.out.println("User Following file does not exist at path: "+userFollowingFile);
		}
		
		//Cannot proceed if files do not exist
		if (!filesExist){
			return new ArrayList<String>();
		}
				
		//Load Tweets
		List<String> tweetStrings = getTweetsStrings();
		List<ITweet> tweetMessages = convertStringsToTweets(tweetStrings);
		
		//Load Followings
		UserRelationsBuilder br = new UserRelationsBuilder(userFollowingFile);
		br.processUserRelationships();
		
		Map<String,IUser> userRelationMap = br.getUserMap();
		Set<String> users = userRelationMap.keySet();
		SortedMap<String,Set<String>> userTweetsMap = updateUserTweetsMap(tweetMessages,userRelationMap,users);		
	
		//Display final output
		List<String> linesList = displayUserTweetMap(userTweetsMap, suppressOutput);
		
		return linesList;
	}
	
	/**
	 * Display Tweets per User Followings
	 * @param tweetMap
	 */
	public List<String> displayUserTweetMap(SortedMap<String,Set<String>> tweetMap, boolean suppressOutput) {
		List<String> linesList = new ArrayList<String>();
		Set<String> users = tweetMap.keySet();
		
		for (String user : users){
			Set<String> tweets = tweetMap.get(user);
			linesList.add(user);
			
			if (!suppressOutput){
				System.out.println(user);
			}
			
			for (String tweet : tweets){
				if (!suppressOutput){
					System.out.println("\t"+tweet);
				}
				
				linesList.add("\t"+tweet);
			}//end for
			
		}//end for
		
		return linesList;
		
	}
	
	public static void main(String[] args) {
		
		
		//Check for correct command line arguments
		if (args != null){
			if (args.length != 2){
				System.out.println("Error: All arguments not present.");
				System.out.println("Correct usage: java -jar JavaTestApp.jar <User following file full path> <Tweet file full path>");
				System.out.println("Correct usage example: java -jar JavaTestApp.jar C:/dev/arshad/JavaTestApp/user.txt C:/dev/arshad/JavaTestApp/tweet.txt");
			} else {
				TweetsFeedProcessor tweetsFeedProcessing = new TweetsFeedProcessor(args[0],args[1]);
				tweetsFeedProcessing.processTweetFeed(false);
			}
		}
		
		
	    
	}

}
