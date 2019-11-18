package com.application.base.nifi;

import com.application.base.processor.SecondProcessor;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author : 孤狼
 * @NAME: FirstTest
 * @DESC:
 **/
public class SecondTest {

	@Test
	public void testOnTrigger() throws IOException {
		// Content to be mock a json file
		InputStream content = new ByteArrayInputStream("你是谁呀?".getBytes());
		
		// Generate a test runner to mock a processor in a flow
		TestRunner runner = TestRunners.newTestRunner(new SecondProcessor());
		
		// Add properties
		runner.setProperty(SecondProcessor.INPUT_VALUE, "我是世间第一帅!");
		
		// Add the content to the runner
		runner.enqueue(content);
		
		// Run the enqueued content, it also takes an int = number of contents queued
		runner.run(1);
		
		// All results were processed with out failure
		runner.assertQueueEmpty();
		
		// If you need to read or do additional tests on results you can access the content
		List<MockFlowFile> results = runner.getFlowFilesForRelationship(SecondProcessor.RELATIONSHIP_SUCCESS);
		assertTrue("1 match", results.size() == 1);
		MockFlowFile result = results.get(0);
		String resultValue = new String(runner.getContentAsByteArray(result));
		System.out.println("result : "+resultValue);
	}

}
