# Test Sorting

This exercise is about getting familiar with unit testing and JUnit 4.12 in particular.

1. Design and code a simple sorting method in the [Sorting](src/main/java/com/epam/rd/autotasks/Sorting.java) class.
Here are some details:
- the method sorts an integer array in ascending order
- an integer array is passed as a parameter to the method
- when given array is null, the method must throw an IllegalArgumentException.


2. Complete the test methods in [SortingTest](src/main/java/com/epam/rd/autotasks/SortingTest.java) class.
Use assertions to check your sorting methods. Be sure to use @Test annotation parameters to catch expected exceptions.

To pass the exercise, your tests must correctly detect flaws of some other sorting method implementations.
There are special tests in [SortingTestsTest](src/test/java/com/epam/rd/autotasks/SortingTestsTest.java) class that apply your tests to your and some of such bad implementations.
Your sorting method must pass your tests, and others must fail in some cases.
