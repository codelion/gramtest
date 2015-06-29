/*
 * © Copyright 2015 -  SourceClear Inc
 */

package com.sourceclear.gramtest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class GeneratorVisitor extends bnfBaseVisitor {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private final Map<String,bnfParser.RhsContext> productionsMap = new HashMap<>();
  private int maxStringLength = 2;
          
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  public GeneratorVisitor() {
  }
  
  public GeneratorVisitor(int max) {
    maxStringLength = max;
  }

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  //------------------------ Implements:
  //------------------------ Overrides:
  
  @Override
  public Object visitId(bnfParser.IdContext ctx) {
    return super.visitId(ctx);
  }

  @Override
  public String visitText(bnfParser.TextContext ctx) {
    return ctx.getText();
  }

  @Override
  public String visitCaptext(bnfParser.CaptextContext ctx) {
    return ctx.getText();
  }

  @Override
  public Object visitOneormore(bnfParser.OneormoreContext ctx) {
    return super.visitOneormore(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitZeroormore(bnfParser.ZeroormoreContext ctx) {
    return super.visitZeroormore(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitOptional(bnfParser.OptionalContext ctx) {
    return super.visitOptional(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<String> visitElement(bnfParser.ElementContext ctx) {
    List<String> result = new LinkedList<>();
    if(ctx.zeroormore() != null) {
      result = visitAlternatives(ctx.zeroormore().alternatives()); // one time
      result.add(""); // zero time
    }
    else if(ctx.oneormore() != null) {
      result = visitAlternatives(ctx.oneormore().alternatives()); // one time
      List<String> twoLs = combineTwoLists(result,result); //two times
      result.addAll(twoLs);
    }
    else if(ctx.optional() != null) {
      //currently similar to zero of more times
      result = visitAlternatives(ctx.zeroormore().alternatives()); // one time
      result.add(""); // zero time
    }
    else if(ctx.captext() != null) {
      result.add(visitCaptext(ctx.captext()));
    }
    else if(ctx.text() != null) {
      result.add(visitText(ctx.text()));
    }
    else {
      return visitRhs(productionsMap.get(ctx.id().getText()));
    }
    return result;
  }

  @Override
  public List<String> visitAlternative(bnfParser.AlternativeContext ctx) {
    List<List<String>> comStr = new LinkedList<>();
    for(bnfParser.ElementContext ec1 : ctx.element()) {
     comStr.add(visitElement(ec1));
    }
    List<String> emptyStr = new LinkedList<>();
    emptyStr.add("");
    return generateAllStrings(comStr, emptyStr);
  }

  @Override
  public List<String> visitAlternatives(bnfParser.AlternativesContext ctx) {
    List<String> altStrs = new LinkedList<>();
    for(bnfParser.AlternativeContext ac : ctx.alternative()) {
      altStrs.addAll(visitAlternative(ac));
    }
    return altStrs;
  }

  @Override
  public List<String> visitRhs(bnfParser.RhsContext ctx) {
    return visitAlternatives(ctx.alternatives()); 
  }

  @Override
  public Object visitLhs(bnfParser.LhsContext ctx) {
    return super.visitLhs(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<String> visitRule_(bnfParser.Rule_Context ctx) {
    return visitRhs(ctx.rhs());
  }

  @Override
  public List<String> visitRulelist(bnfParser.RulelistContext ctx) {
    List<String> sentences = new LinkedList();
    for(bnfParser.Rule_Context rc : ctx.rule_()) {
      productionsMap.put(rc.lhs().id().getText(), rc.rhs());
    }
    for(bnfParser.Rule_Context rc : ctx.rule_()) {
      sentences.addAll(visitRule_(rc));
    }
    return sentences;
  }
  
  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------
  
  private List<String> generateAllStrings(List<List<String>> strList, List<String> result) {
    if(strList.size() > 0) {
      List<String> newResult = combineTwoLists(result,strList.remove(0));
      return generateAllStrings(strList, newResult);
    }
    return result;
  }
  
  private List<String> combineTwoLists(List<String> preList, List<String> postList) {
    List<String> combList = new LinkedList<>();
    for(String s1 : preList) {
      for(String s2 : postList) {
        if(s1.length() + s2.length() <= maxStringLength)
          combList.add(s1+s2);
      }
    }
    return combList;
  }

  //---------------------------- Property Methods -----------------------------
  
}
