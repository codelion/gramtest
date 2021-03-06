package com.sourceclear.gramtest;

import java.io.IOException;

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
  @Ignore("Ignoring non-terminating test case")
  public void testQueueGenerator() throws IOException, InterruptedException {
    final BlockingQueue<String> queue = new SynchronousQueue<>();
    TestRunner continuousRunner = new TestRunner(getClass().getResourceAsStream("/url.bnf"), queue, 10, 8, 16);
    new Thread(continuousRunner).start();
    consumeTests(queue);
  }

  private void consumeTests(BlockingQueue queue) throws InterruptedException {
    while(true) {
      String testCase = (String) queue.take();
      System.out.println(testCase);
    }
  }

}