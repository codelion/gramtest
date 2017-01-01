/*
 * © Copyright 2015 -  SourceClear Inc
 */

package com.sourceclear.gramtest;

import java.util.*;

/**
 *
 */
public class GeneratorVisitor extends bnfBaseVisitor {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private final Map<String,bnfParser.RhsContext> productionsMap = new HashMap<>();
  private int maxNum = 100;
  private int maxDepth = 2;
  private int maxSize = 4;
  private int minSize = 1;
  private boolean useMinimalGenerator = true;
  private List<String> tests = new LinkedList<>();
  private final Stack<String> prodHist = new Stack<>();
          
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  GeneratorVisitor() {
  }
  
  GeneratorVisitor(int num, int depth, int min, int max, boolean useMinimalGenerator) {
    this.maxNum = num;
    this.maxDepth = depth;
    this.minSize = min;
    this.maxSize = max;
    this.useMinimalGenerator = useMinimalGenerator;
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
    if(ctx.STRINGLITERAL() != null) {
      return unquote(ctx.getText());
    }
    return ctx.getText();
  }

  @Override
  public String visitCaptext(bnfParser.CaptextContext ctx) {
    return ctx.getText();
  }

  @Override
  public Object visitOneormore(bnfParser.OneormoreContext ctx) {
    return super.visitOneormore(ctx); 
  }

  @Override
  public Object visitZeroormore(bnfParser.ZeroormoreContext ctx) {
    return super.visitZeroormore(ctx); 
  }

  @Override
  public Object visitOptional(bnfParser.OptionalContext ctx) {
    return super.visitOptional(ctx); 
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
      result = visitAlternatives(ctx.optional().alternatives()); // one time
      result.add(""); // zero time
    }
    else if(ctx.captext() != null) {
      result.add(visitCaptext(ctx.captext()));
    }
    else if(ctx.text() != null) {
      result.add(visitText(ctx.text()));
    }
    else {
      String lhs = ctx.id().getText();
      prodHist.push(lhs);
      return visitRhs(productionsMap.get(lhs));
    }
    return result;
  }

  @Override
  public List<String> visitAlternative(bnfParser.AlternativeContext ctx) {
    List<List<String>> comStr = new LinkedList<>();
    for(bnfParser.ElementContext ec1 : ctx.element()) {
      List<String> slist = visitElement(ec1);
      if(slist.isEmpty()) return new LinkedList<>();
      else comStr.add(slist);
    }
    List<String> emptyStr = new LinkedList<>();
    emptyStr.add("");
    return generateAllStrings(comStr, emptyStr);
  }

  @Override
  public List<String> visitAlternatives(bnfParser.AlternativesContext ctx) {
    List<String> altStrs = new LinkedList<>();
    for(bnfParser.AlternativeContext ac : ctx.alternative()) {
      if(prodHist.size() < maxDepth)
        altStrs.addAll(visitAlternative(ac));
    }
    return altStrs;
  }

  @Override
  public List<String> visitRhs(bnfParser.RhsContext ctx) {
    List<String> tmp = visitAlternatives(ctx.alternatives());
    if(!prodHist.empty()) prodHist.pop();
    return tmp; 
  }

  @Override
  public Object visitLhs(bnfParser.LhsContext ctx) {
    return super.visitLhs(ctx);
  }

  @Override
  public List<String> visitRule_(bnfParser.Rule_Context ctx) {
    return visitRhs(ctx.rhs());
  }

  @Override
  public Object visitRulelist(bnfParser.RulelistContext ctx) {
    List<String> sentences = new LinkedList<>();
    for(bnfParser.Rule_Context rc : ctx.rule_()) {
      productionsMap.put(rc.lhs().id().getText(), rc.rhs());
    }
    maxDepth = maxDepth + productionsMap.size();
    // Should not visit all the rules, but start from the top one
    /*
    for(bnfParser.Rule_Context rc : ctx.rule_()) {
      sentences.addAll(visitRule_(rc));
    }
    */
    sentences.addAll(visitRule_(ctx.rule_(0)));
    sentences.removeAll(Collections.singleton(""));
    if(sentences.size() > maxNum)
      tests = sentences.subList(0, maxNum); // return only top maxNum test cases
    else tests = sentences;
    return super.visitRulelist(ctx);
  }
  
  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  private String unquote(String s) {
    if (s != null && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
      s = s.substring(1, s.length() - 1);
    }
    return s;
  }

  private List<String> generateAllStrings(List<List<String>> strList, List<String> result) {
    if(strList.size() > 0) {
      List<String> newResult = combineTwoLists(result,strList.remove(0));
      if(newResult.isEmpty()) return result;
      else return generateAllStrings(strList, newResult);
    }
    return result;
  }

  private List<String> combineTwoLists(List<String> preList, List<String> postList) {
    List<String> combList = new LinkedList<>();

    if(useMinimalGenerator) {
      Stack<String> preStack = new Stack<>();
      preStack.addAll(preList);
      Stack<String> postStack = new Stack<>();
      postStack.addAll(postList);
      while(!preStack.empty() && !postStack.empty()) {
        String s1 = preStack.pop();
        String s2 = postStack.pop();
        if(combList.size() < maxNum && (s1.length() + s2.length()) < maxSize
                && !(s1.isEmpty() && s2.isEmpty()))
          combList.add(s1+s2);
      }
    }
    for(String s1 : preList) {
      for(String s2 : postList) {
        if(combList.size() < maxNum && minSize <= (s1.length() + s2.length()) && (s1.length() + s2.length()) < maxSize
                && !(s1.isEmpty() && s2.isEmpty()))
          combList.add(s1+s2);
      }
    }
    return combList;
  }

  //---------------------------- Property Methods -----------------------------
  
  public List<String> getTests() {
    return tests;
  }

}
