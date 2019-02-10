package com.twitter.processor.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TweetsFeedFileIO implements IInputDataIO{

	private String tweetFeedFilePath;
	
	public TweetsFeedFileIO(String tweetFeedFilePath){
		this.tweetFeedFilePath = tweetFeedFilePath;
	}
	
	@Override
	public List<String> readData() {
		List<String> tweets = new ArrayList<>();
		try {
			tweets = tweetsFeedReadFromFile(tweetFeedFilePath);
		}catch(FileNotFoundException fe) {
			System.err.println("File not found: "+tweetFeedFilePath);
		}catch(IOException e) {
			e.printStackTrace();
		}	
		return tweets;
	}
	
	private List<String> tweetsFeedReadFromFile(String tweetFeedFilePath) throws FileNotFoundException,IOException {		
		List<String> tweets = new ArrayList<>();
		Scanner scanner = new Scanner(new File(tweetFeedFilePath));
		while (scanner.hasNextLine()) {
			String data = scanner.nextLine();
			tweets.add(data);
		}
		scanner.close();	
		return tweets;
	}
	
	public static void main(String[] args) {
		//TweetsFeedFileIO tweetsFeedFileIO = new TweetsFeedFileIO("/Users/arshadmayet/eclipse-workspace/JavaTestApp/tweet.txt");
		TweetsFeedFileIO tweetsFeedFileIO = new TweetsFeedFileIO("C:/dev/arshad/JavaTestApp/tweet.txt");
		
		tweetsFeedFileIO.readData();
	}

}
