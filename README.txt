# Project 1: Deterministic Finite Automata

* Author: Jack Lawford & Alec Conn
* Class: CS361 001
* Semester: Fall 2025


## Overview
-------------------
This project implements a Deterministic Finite Automaton (DFA) in Java. The implementation
models a DFA with states, transitions and an alphabet. This allows for string acceptance 
testing 

## Reflection
------------------------
Overall this project went well for us. Jack was able to implement the the DFA and DFAState
java files. Alec was responsible for uploading and testing on onyx to fix any failed tests. 


## Compiling and Using
------------------------
1. Navigate to the project directory containing the fa and test folders

2. Compile the test cases:
   javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java

3. Run the tests:
   java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.dfa.DFATest
