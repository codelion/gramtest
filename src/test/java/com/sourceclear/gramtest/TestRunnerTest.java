package com.sourceclear.gramtest;

import java.io.IOException;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.ParseException;
import org.junit.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 *
 */
public class TestRunnerTest {

  /**
   * Test with url grammar
   * @throws java.io.IOException
   */
  @Test
  @Ignore("Non terminating test case")
  public void testQueueGenerator() throws IOException, InterruptedException {
    final BlockingQueue<String> queue = new SynchronousQueue<>();
    TestRunner continuousRunner = new TestRunner(getClass().getResourceAsStream("/json.bnf"), queue, 8, 4, 32);
    new Thread(continuousRunner).start();
    consumeTests(queue);
  }

  private void consumeTests(BlockingQueue queue) throws InterruptedException {
    while(true) {
      String testCase = ((String) queue.take()).replace("$$$", "\"");
      JsonValue j;
      try {
        j = Json.parse(testCase);
        if(!testCase.equals(j.toString())) System.out.println("Inverse Error : " + testCase);
      }
      catch (ParseException e) {
        System.out.println("Parse Error : " + testCase);
      }
    }
  }

}