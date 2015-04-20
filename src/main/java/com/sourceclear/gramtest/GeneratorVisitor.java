/*
 * © Copyright 2015 -  SourceClear Inc
 */

package com.sourceclear.gramtest;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class GeneratorVisitor extends bnfBaseVisitor {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
          
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  @Override
  public Object visitId(bnfParser.IdContext ctx) {
    return super.visitId(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitText(bnfParser.TextContext ctx) {
    return super.visitText(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitCaptext(bnfParser.CaptextContext ctx) {
    return super.visitCaptext(ctx); //To change body of generated methods, choose Tools | Templates.
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
  public Object visitElement(bnfParser.ElementContext ctx) {
    return super.visitElement(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitAlternative(bnfParser.AlternativeContext ctx) {
    return super.visitAlternative(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitAlternatives(bnfParser.AlternativesContext ctx) {
    return super.visitAlternatives(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitRhs(bnfParser.RhsContext ctx) {
    return super.visitRhs(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitLhs(bnfParser.LhsContext ctx) {
    return super.visitLhs(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitRule_(bnfParser.Rule_Context ctx) {
    return super.visitRule_(ctx); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object visitRulelist(bnfParser.RulelistContext ctx) {
    List<String> sentences = new LinkedList();
    return super.visitRulelist(ctx); //To change body of generated methods, choose Tools | Templates.
  }
  
  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
  
}
