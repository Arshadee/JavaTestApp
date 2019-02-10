package com.twitter.processor.junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.twitter.processor.TweetsFeedProcessor;
import com.twitter.processor.io.file.TweetsFeedFileIO;
import com.twitter.processor.io.file.UserRelationsFileIO;

public class TweetsFeedFileTest {

	@Test
	public void TweetsFeedFileMissingFile() {
		boolean failedToReadFile = false;
		
		TweetsFeedFileIO tweetsFeedFileIO = new TweetsFeedFileIO("");
		List<String> list = tweetsFeedFileIO.readData();
		
		if (list.size() == 0){
			failedToReadFile = true;
		}
		
		assertTrue("Missing file not handled correctly",failedToReadFile);
	}
	
	@Test
	public void TweetsFeedFileExists() {
		boolean readFileSuccessful = false;
		
		TweetsFeedFileIO tweetsFeedFileIO = new TweetsFeedFileIO(TweetsFeedProcessor.workingDirectory+"tweet.txt");
		List<String> list = tweetsFeedFileIO.readData();
		
		if (list.size() == 3){
			readFileSuccessful = true;
		}
		
		assertTrue("Existing file not handled correctly",readFileSuccessful);
	}
	

}
