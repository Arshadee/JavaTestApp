package com.twitter.processor.junit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.twitter.processor.TweetsFeedProcessor;
import com.twitter.processor.UserRelationsBuilder;
import com.twitter.processor.data.IUser;
import com.twitter.processor.io.file.UserRelationsFileIO;

public class TweetsFeedProcessorTest {

	@Test
	public void checkGeneratedOutput() {
		boolean passed = true;
		
		TweetsFeedProcessor tweetsFeedProcessing = new TweetsFeedProcessor(TweetsFeedProcessor.workingDirectory+"user.txt",
				TweetsFeedProcessor.workingDirectory+"tweet.txt");
		
		List<String> linesList = tweetsFeedProcessing.processTweetFeed(true);
		List<String> sampleLinesList = readSampleFile(TweetsFeedProcessor.workingDirectory+"sampleOut.txt");
		
		int lineIndex = 0;
		for (String sampleLine : sampleLinesList){
			//check if there is a deviation in output from the sample expected output
			if (!linesList.get(lineIndex).equals(sampleLine)){
				passed = false;
				System.out.println("Diff on Line "+lineIndex);
				System.out.println("Generated: "+linesList.get(lineIndex));
				System.out.println("Expected: "+sampleLine);
			}
			lineIndex++;
		}
		
		assertTrue("Generated output does not match sample expected output",passed);
	}
	
	
	private List<String> readSampleFile(String filePath)  {		
		List<String> linesList = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(filePath));
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				linesList.add(data);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return linesList;
	}
	
	

}
