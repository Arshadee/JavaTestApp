package com.twitter.processor.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TweetsFeedFileTest.class, UserRelationsFileTest.class,
	UserRelationsBuilderTest.class, TweetsFeedProcessorTest.class })
public class AllTests {

}
