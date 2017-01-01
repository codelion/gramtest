package com.sourceclear.gramtest;

import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public class TestRunner implements Runnable {

  private ParserRuleContext tree;
  private GeneratorVisitor extractor;
  private int depth = 2;
  private int max = 4;
  private int min = 1;
  private BlockingQueue<String> queue;

  TestRunner(InputStream bnfGrammar, BlockingQueue<String> queue) throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(bnfGrammar));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    this.tree = grammarparser.rulelist();
    this.queue = queue;
  }

  TestRunner(InputStream bnfGrammar, BlockingQueue<String> queue, int depth, int min, int max) throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(bnfGrammar));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    this.tree = grammarparser.rulelist();
    this.queue = queue;
    this.depth = depth;
    this.max = max;
    this.min = min;
  }

  public void run() {
    while(true) {
      this.extractor = new GeneratorVisitor(depth * max, depth, min, max, true);
      this.extractor.visit(tree);
      List<String> generatedTests = this.extractor.getTests();
      for (String s: generatedTests)
        try {
          this.queue.put(s);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      if((depth + max) % 2 == 0) depth++;
      else max++;
    }
  }

}

