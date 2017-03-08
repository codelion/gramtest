/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourceclear.gramtest;

import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.*;

/**
 *
 * @author asankhaya
 */
public class MainTest {
  
  public MainTest() {
    super();
  }
  
  @Before
  public void setUp() throws Exception {
  }
  
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test with arithmetic expressions grammar
   * @throws java.io.IOException
   */
  @Test
  public void testArithExpGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/arithexp.bnf")));        
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor();
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(100, generatedTests.size());
  }
  
  /**
   * Test with course codes grammar
   * @throws java.io.IOException
   */
  @Test
  public void testCourseCodeGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/coursecodes.bnf")));        
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor();
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(10, generatedTests.size());
  }
  
  /**
   * Test with main grammar
   * @throws java.io.IOException
   */
  @Test
  public void testMainStrGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/main.bnf")));        
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor(100,2,1,10,true);
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(100, generatedTests.size());
  }

  /**
   * Test with JSON grammar
   * @throws java.io.IOException
   */
  @Test
  public void testJSONGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/json.bnf")));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor(100,2,10,100,true);
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(100, generatedTests.size());
  }

  /**
   * Test with Datalog grammar
   * @throws java.io.IOException
   */
  @Test
  @Ignore("Ignoring long running test")
  public void testDatalogGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/datalog.bnf")));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor(100,2,8,16,true);
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(100, generatedTests.size());
  }

  /**
   * Test with CSV grammar
   * @throws java.io.IOException
   */
  @Test
  public void testCSVGram() throws IOException {
    Reader r = new InputStreamReader(getClass().getResourceAsStream("/csv.bnf"), "UTF-8");
    Lexer lexer = new bnfLexer(new ANTLRInputStream(r));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor(100,4,1,32,true);
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(10, generatedTests.size());
  }

  /**
   * Test with url grammar
   * @throws java.io.IOException
   */
  @Test
  @Ignore("Ignoring long running test")
  public void testURLGram() throws IOException {
    Lexer lexer = new bnfLexer(new ANTLRInputStream(getClass().getResourceAsStream("/url.bnf")));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    bnfParser grammarparser = new bnfParser(tokens);
    ParserRuleContext tree = grammarparser.rulelist();
    GeneratorVisitor extractor = new GeneratorVisitor();
    extractor.visit(tree);
    List<String> generatedTests = extractor.getTests();
    Assert.assertEquals(9, generatedTests.size());
  }
  
}
