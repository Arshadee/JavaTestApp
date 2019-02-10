package com.twitter.processor.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRelationsFileIO implements IInputDataIO{
	/*
	/Users/arshadmayet/eclipse-workspace/JavaTestApp/tweet.txt
	/Users/arshadmayet/eclipse-workspace/JavaTestApp/appClientModule/assessment/User.java
	*/
	
	private String userRelationFilePath;
	
	public UserRelationsFileIO(String userRelationFilePath){
		this.userRelationFilePath = userRelationFilePath;
	}
	
	public List<String> readData(){
		List<String> userRels = new ArrayList<>();
		try {
			userRels = userRelationsReadFromFile(userRelationFilePath);
		}catch(FileNotFoundException fe) {
			System.err.println("File not found: "+userRelationFilePath);
		}catch(IOException e) {
			e.printStackTrace();
		}	
		return userRels;
	}

	private List<String> userRelationsReadFromFile(String userRelationFilePath) throws FileNotFoundException,IOException {		
		List<String> userRelations = new ArrayList<>();
		Scanner scanner = new Scanner(new File(userRelationFilePath));
		while (scanner.hasNextLine()) {
			String data = scanner.nextLine();
			userRelations.add(data);
		}
		scanner.close();	
		return userRelations;
	}
	
	public static void main(String[] args) {
		
		List<String> usrRels = new ArrayList<>();
		//UserRelationsFileIO usrRel = new UserRelationsFileIO("/Users/arshadmayet/eclipse-workspace/JavaTestApp/user.txt");
		UserRelationsFileIO usrRel = new UserRelationsFileIO("C:/dev/arshad/JavaTestApp/user.txt");
	    usrRel.readData();
		
	}
}
