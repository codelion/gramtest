package com.sourceclear.gramtest;

import java.io.IOException;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.*;

import java.net.MalformedURLException;
import java.net.URL;
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
    TestRunner continuousRunner = new TestRunner(getClass().getResourceAsStream("/url.bnf"), queue, 10, 8, 16);
    new Thread(continuousRunner).start();
    consumeTests(queue);
  }

  private void consumeTests(BlockingQueue queue) throws InterruptedException {
    UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES +
            UrlValidator.ALLOW_2_SLASHES + UrlValidator.ALLOW_LOCAL_URLS);
    while(true) {
      String testCase = (String) queue.take();
      if(!validator.isValid(testCase)) {
        try {
          new URL(testCase);
          System.out.println(testCase);
        } catch (MalformedURLException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

}