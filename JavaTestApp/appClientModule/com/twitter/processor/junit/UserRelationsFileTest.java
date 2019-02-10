package com.twitter.processor.junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.twitter.processor.TweetsFeedProcessor;
import com.twitter.processor.io.file.UserRelationsFileIO;

public class UserRelationsFileTest {

	@Test
	public void UserRelationsMissingFile() {
		boolean failedToReadFile = false;
		
		UserRelationsFileIO userRelationsFileIO = new UserRelationsFileIO("");
		List<String> list = userRelationsFileIO.readData();
		
		if (list.size() == 0){
			failedToReadFile = true;
		}
		
		assertTrue("Missing file not handled correctly",failedToReadFile);
	}
	
	@Test
	public void UserRelationsFileExists() {
		boolean readFileSuccessful = false;
		
		UserRelationsFileIO userRelationsFileIO = new UserRelationsFileIO(TweetsFeedProcessor.workingDirectory+"user.txt");
		List<String> list = userRelationsFileIO.readData();
		
		if (list.size() == 3){
			readFileSuccessful = true;
		}
		
		assertTrue("Existing file not handled correctly",readFileSuccessful);
	}
	

}
