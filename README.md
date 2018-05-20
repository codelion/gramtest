# GramTest [![Build Status](https://travis-ci.org/codelion/gramtest.svg?branch=master)](https://travis-ci.org/codelion/gramtest)
This tool allows you to generate test cases based on arbitrary user defined grammars. The input grammar is given in BNF notation. Potential applications include fuzzing and automated testing.

## Instructions
We use maven as our build tool. When you compile the project using maven (`mvn clean package`) it will generate a single all dependencies Jar, that can be run from the command line as follows:

`java -jar gramtest.jar -file grammar.bnf`

To take a look at all the additional options, you can run `java -jar gramtest.jar -h` which will print the details as below.

```
usage: gramtest [options]
 -dep <depth of rules>         maximum depth for recursive rules
 -ext <extension>              file extension for generated tests
 -file <grammar file>          path to the grammar file (in BNF notation)
 -h,--help                     prints this message
 -max <max size of test>       maximum size of each generated test
 -min <min size of test>       minimum size of each generated test
 -mingen <minimal generator>   use minimal sentence generation
                               (true/false)
 -num <number of tests>        maximum number of test cases
 -tests <test folder>          path to the folder to store generated tests
```

## Bugs found using GramTest
| Project | Reference | Status |
|---------|---------|--------|
| Apache Commons Validator | https://issues.apache.org/jira/browse/VALIDATOR-410 | Open | 
| segmentio/is-url | https://github.com/segmentio/is-url/issues/15 | Open |
| gajus/url-regexp | https://github.com/gajus/url-regexp/issues/6 | Open |
| segmentio/is-url | https://github.com/segmentio/is-url/issues/21 | Open |

## Articles
- [Continuous fuzzing of Java projects with GramTest](https://lambdasec.github.io/Fuzzing-Java-Libraries-with-GramTest/)
- [How does grammar-based test case generation work?](https://blog.srcclr.com/how-does-grammar-based-test-case-generation-work/)
- [Practical tips for implementing grammar-based test case generation](https://blog.srcclr.com/practical-tips-for-implementing-grammar-based-test-case-generation/)

## FAQ

- _Can I use this particular grammar with GramTest?_

As long as the input grammar is described in BNF it is possible to use it with GramTest to generate random strings. Several grammars that you find online are not given in proper BNF format, have comments/text in some productions, or do not have complete clauses. Verify that the grammar you are using doesn't have any bugs before using it. 

- _Why did you choose BNF and not EBNF/ABNF/etc. as the input format for GramTest?_

BNF was the simplest of all formats to use, it is certainly possible to implement another input format but it is going to be a non-trivial excercise. You will need to implement your own [generator](https://github.com/codelion/gramtest/blob/master/src/main/java/com/sourceclear/gramtest/GeneratorVisitor.java) that can create strings based on each type of production in the grammar. 

- _Where can I find some sample BNF grammars to use?_

Several examples are available in the [test folder](https://github.com/codelion/gramtest/tree/master/src/test/resources). You can refer to the unit tests to see how they can be used.
