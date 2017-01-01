/*
 * © Copyright 2015 -  SourceClear Inc
 */

package com.sourceclear.gramtest;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.cli.*;

/**
 *
 */
public class Main {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public static void main(String[] args ) {
    // create the parser
    CommandLineParser parser = new DefaultParser();
    // create Options object
    Options options = new Options();
    Option help = new Option("h","help",false,"prints this message");
    Option maxoption = Option.builder("num").type(Integer.class)
                                    .argName("number of tests")
                                    .hasArg()
                                    .desc("maximum number of test cases")
                                    .build();
    Option depthoption = Option.builder("dep").type(Integer.class)
                                    .argName("depth of rules")
                                    .hasArg()
                                    .desc("maximum depth for recursive rules")
                                    .build();
    Option maxsizeoption = Option.builder("max").type(Integer.class)
            .argName("max size of test")
            .hasArg()
            .desc("maximum size of each generated test")
            .build();
    Option minsizeoption = Option.builder("min").type(Integer.class)
            .argName("min size of test")
            .hasArg()
            .desc("minimum size of each generated test")
            .build();
    Option grammarfile = Option.builder("file").argName("grammar file")
                                      .hasArg()
                                      .desc("path to the grammar file (in BNF notation)")
                                      .build();
    Option testsfolder = Option.builder("tests").argName("test folder")
                                      .hasArg()
                                      .desc("path to the folder to store generated tests")
                                      .build();
    Option extension = Option.builder("ext").argName("extension")
                                    .hasArg()
                                    .desc("file extension for generated tests")
                                    .build();
    Option usemingen = Option.builder("mingen").type(Boolean.class)
                                .argName("minimal generator")
                                .hasArg()
                                .desc("use minimal sentence generation (true/false)")
                                .build();
    options.addOption(help);
    options.addOption(grammarfile);
    options.addOption(testsfolder);
    options.addOption(extension);
    options.addOption(maxoption);
    options.addOption(depthoption);
    options.addOption(usemingen);
    options.addOption(maxsizeoption);
    options.addOption(minsizeoption);
    try {
      // parse the command line arguments
      CommandLine line = parser.parse(options, args);
      if(line.hasOption("help") || line.getOptions().length == 0) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("gramtest [options]", options);
      }
      else if(line.hasOption("file")) {
        String filename = line.getOptionValue("file");
        int num = 100;
        int depth = 2;
        int max = 4;
        int min = 1;
        String extStr = "txt";
        boolean useMinGen = true;
        if(line.hasOption("num")) {
          num = Integer.valueOf(line.getOptionValue("num"));
        }
        if(line.hasOption("dep")) {
          depth = Integer.valueOf(line.getOptionValue("dep"));
        }
        if(line.hasOption("max")) {
          max = Integer.valueOf(line.getOptionValue("max"));
        }
        if(line.hasOption("min")) {
          min = Integer.valueOf(line.getOptionValue("min"));
        }
        if(line.hasOption("mingen")) {
          useMinGen = Boolean.valueOf(line.getOptionValue("mingen"));
        }
        if(line.hasOption("ext")) {
          extStr = String.valueOf(line.getOptionValue("ext"));
        }
        Lexer lexer;
        try {
          lexer = new bnfLexer(new ANTLRFileStream(filename));
          CommonTokenStream tokens = new CommonTokenStream(lexer);
          bnfParser grammarparser = new bnfParser(tokens);
          //grammarparser.setTrace(true);
          ParserRuleContext tree = grammarparser.rulelist();
          GeneratorVisitor extractor = new GeneratorVisitor(num,depth,min,max,useMinGen);
          extractor.visit(tree);
          List<String> generatedTests = extractor.getTests();
          System.out.println("Generating tests ...");
          if(line.hasOption("tests")) {
            String folderPath = line.getOptionValue("tests");
            int i = 1;
            for(String s: generatedTests) {
              File f = new File(folderPath+"/"+i+"."+extStr);
              Files.createParentDirs(f);
              Files.write(s, f, Charset.forName("UTF-8"));
              i++;
            }
            System.out.println("All tests have been saved in the "+ folderPath + " folder!");
          }
          else {
            for(String s : generatedTests) {
              System.out.println(s);
            }
          }
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
