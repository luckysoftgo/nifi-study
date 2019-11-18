package com.application.base.nifi;

import com.application.base.processor.FirstProcessor;
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
public class FirstTest {

	@Test
	public void testOnTrigger() throws IOException {
		// Content to be mock a json file
		InputStream content = new ByteArrayInputStream(("{\"error_code\":0,\"reason\":\"ok\"," +
				"\"result\":{\"items\":[{\"regStatus\":\"存续\",\"estiblishTime\":1495555200000,\"regCapital\":\"\"," +
				"\"pencertileScore\":4902,\"type\":1,\"legalPersonName\":\"温旭颖\",\"toco\":2," +
				"\"legalPersonId\":2051255554,\"name\":\"青云科技\",\"logo\":\"https://img5.tianyancha" +
				".com/logo/lll/f4155abb4babbc1985049529e103779a.png@!f_200x200\",\"alias\":\"青云科技\",\"id\":3053414776," +
				"\"category\":\"723\",\"personType\":1,\"base\":\"han\"},{\"regStatus\":\"注销\"," +
				"\"estiblishTime\":1473264000000,\"regCapital\":\"\",\"pencertileScore\":3860,\"type\":1," +
				"\"legalPersonName\":\"常青\",\"toco\":8,\"legalPersonId\":1911055314,\"name\":\"青云科技\"," +
				"\"logo\":\"\"," +
				"\"alias\":\"青云科技\",\"id\":2958332903,\"category\":\"721\",\"personType\":1,\"base\":\"xj\"}]," +
				"\"total\":18}}").getBytes());
		
		// Generate a test runner to mock a processor in a flow
		TestRunner runner = TestRunners.newTestRunner(new FirstProcessor());
		
		// Add properties
		runner.setProperty(FirstProcessor.JSON_PATH, "$.result.items[*]");
		runner.setProperty(FirstProcessor.ARRAY_FLAG,"true");
		
		// Add the content to the runner
		runner.enqueue(content);
		
		// Run the enqueued content, it also takes an int = number of contents queued
		runner.run(1);
		
		// All results were processed with out failure
		runner.assertQueueEmpty();
		
		// If you need to read or do additional tests on results you can access the content
		List<MockFlowFile> results = runner.getFlowFilesForRelationship(FirstProcessor.SUCCESS);
		assertTrue("1 match", results.size() == 1);
		MockFlowFile result = results.get(0);
		String resultValue = new String(runner.getContentAsByteArray(result));
		System.out.println("Match: " + resultValue);
		
		// Test attributes and content
		//result.assertAttributeEquals(NiFiConstant.MATCH_ATTR, "this is  a json path processor !");
		//result.assertContentEquals("this is  a json path processor !");
	}

}
