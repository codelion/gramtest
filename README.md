# Grammar based test case generation
This tool allows you to generate test cases based on aribitary user defined grammars. The input grammar is given in BNF notation. Potential applications include fuzzing and automated testing.

## Instructions
We use maven as our build tool. When you compile the project using maven it will generate a single Jar, that can be run from the command line as follows:

`java -jar gramtest.jar -file grammar.bnf`

To take a look at all the addtional options, you can run `java -jar gramtest.jar -h` which will print the details as below.

```
usage: gramtest [options]
 -dep <depth of rules>    maximum depth of production rules to be followed
 -ext <extension>         file extension for generated tests
 -file <grammar file>     path to the grammar file (in BNF notation)
 -h,--help                prints this message
 -num <number of tests>   maximum number of test cases to be generated
 -tests <test folder>     path to the folder to store generated tests
```
