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
  private BlockingQueue<String> queue;

  TestRunner(InputStream bnfGrammar, BlockingQueue<String> queue) throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(bnfGrammar));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    this.tree = grammarparser.rulelist();
    this.queue = queue;
  }

  public void run() {
    int depth = 1;
    int size = 1;
    while(true) {
      this.extractor = new GeneratorVisitor(depth + size, depth, size, true);
      this.extractor.visit(tree);
      List<String> generatedTests = this.extractor.getTests();
      for (String s: generatedTests)
        try {
          this.queue.put(s);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      if((depth + size) % 2 == 0) depth++;
      else size++;
    }
  }

}

