/*
 * © Copyright 2015 -  SourceClear Inc
 */

package com.sourceclear.gramtest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 */
public class Main {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public static void main(String[] args ) {
    // create the parser
    CommandLineParser parser = new BasicParser();
    // create Options object
    Options options = new Options();
    Option help = new Option("h","help",false,"prints this message");
    Option grammarfile = OptionBuilder.withArgName("grammar file")
                                      .hasArg()
                                      .withDescription("path to the grammar file (in BNF notation)")
                                      .create("file");
    Option testsfolder = OptionBuilder.withArgName("test folder")
                                      .hasArg()
                                      .withDescription("path to the folder to store generated tests")
                                      .create("tests");
    Option extension = OptionBuilder.withArgName("extension")
                                    .hasArg()
                                    .withDescription("file extension for generated tests")
                                    .create("ext");
    options.addOption(help);
    options.addOption(grammarfile);
    options.addOption(testsfolder);
    options.addOption(extension);
    try {
      // parse the command line arguments
      CommandLine line = parser.parse(options, args);
      if(line.hasOption("help") || line.getOptions().length == 0) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("gramtest [options]", options);
      }
      else if(line.hasOption("file")) {
        String filename = line.getOptionValue("file");
        Lexer lexer;
        try {
          lexer = new bnfLexer(new ANTLRFileStream(filename));        
          CommonTokenStream tokens = new CommonTokenStream(lexer);
          bnfParser grammarparser = new bnfParser(tokens);
          //grammarparser.setTrace(true);
          ParserRuleContext tree = grammarparser.rulelist();
          GeneratorVisitor extractor = new GeneratorVisitor(7);
          extractor.visit(tree);
        }
        catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Exception: grammar file cannot be read!", ex);
        }
      }
      else {
        System.out.println("Error: grammar file not specified!");
        System.out.println("Please give the path to the grammar file using the \"-file\" option.");
      }
    }
    catch(ParseException exp) {
      // oops, something went wrong
      System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
    }
  }
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
  
}
