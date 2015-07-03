/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourceclear.gramtest;

import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Assert;

/**
 *
 * @author asankhaya
 */
public class MainTest extends TestCase {
  
  public MainTest(String testName) {
    super(testName);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test with arithmetic expressions grammar
   * @throws java.io.IOException
   */
  public void testArithExpGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/arithexp.bnf")));        
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor();
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(100,generatedTests.size());
  }
  
  /**
   * Test with course codes grammar
   * @throws java.io.IOException
   */
  public void testCourseCodeGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/coursecodes.bnf")));        
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor();
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(100,generatedTests.size());
  }
  
}
