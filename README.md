# GramTest
This tool allows you to generate test cases based on arbitrary user defined grammars. The input grammar is given in BNF notation. Potential applications include fuzzing and automated testing.

## Instructions
We use maven as our build tool. When you compile the project using maven it will generate a single Jar, that can be run from the command line as follows:

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

## Articles

- [How does grammar-based test case generation work?](https://blog.srcclr.com/how-does-grammar-based-test-case-generation-work/)
- [Practical tips for implementing grammar-based test case generation](https://blog.srcclr.com/practical-tips-for-implementing-grammar-based-test-case-generation/)
