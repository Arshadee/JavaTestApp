package com.twitter.processor.junit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

public class UserRelationsBuilderTest {

	@Test
	public void buildUserRelations() {
		boolean passed = true;
		
		UserRelationsBuilder br = new UserRelationsBuilder(TweetsFeedProcessor.workingDirectory+"user.txt");
		br.processUserRelationships();
		
		Map<String,IUser> userRelationMap = br.getUserMap();
		List<String> linesList = new ArrayList<String>();
			
		userRelationMap.forEach((k,v)->linesList.add("Key : "+k+" , Value : "+v));
	
		int lineNumber = 0;
		for (String line : linesList){
			lineNumber++;
			
			if (lineNumber == 1){
				if (!line.equals("Key : Alan , Value : User [name=Alan, followers=[Martin]]")){
					passed = false;
				}		
			}
			
			if (lineNumber == 2){
				if (!line.equals("Key : Martin , Value : User [name=Martin, followers=[]]")){
					passed = false;
				}		
			}
			
			if (lineNumber == 3){
				if (!line.equals("Key : Ward , Value : User [name=Ward, followers=[Alan, Martin]]")){
					passed = false;
				}		
			}
			
		}
		
		//File did not load
		if (lineNumber == 0){
			passed = false;
		}
		
		assertTrue("User relations not built correctly",passed);
	}
	
	
	
	
	

}
