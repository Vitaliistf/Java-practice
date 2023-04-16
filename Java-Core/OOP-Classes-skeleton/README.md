# Classes

The purpose of this exercise is to introduce you to class development in Java.

Estimated time - 40 minutes.

## Description

Develop [Rectangle](src/main/java/com/epam/rd/qa/classes/Rectangle.java) and [ArrayRectangles](src/main/java/com/epam/rd/qa/classes/ArrayRectangles.java) classes with the following features.
 
### General Level Task:

Develop [`Rectangle`](src/main/java/com/epam/rd/qa/classes/Rectangle.java) class with following content:
- Private real fields `sideA` and `sideB` (sides А and В of the rectangle). 
- Constructor with two real parameters `a` and `b` to specify the sides of the rectangle. 
- Constructor with a real parameter side to specify the same values for sides A and B of the rectangle.
- Constructor without parameters (initializes side A with 4 and side B with 3). 
- Method `getSideA`, returns value of the side А.
- Method `getSideВ`, returns value of the side В.
- Method `area`, calculates and returning the area value.
- Method `perimeter`, calculates and returning the perimeter value.
- Method `isSquare`, checks whether current rectangle is shape square or not. Returns `true` if the shape is square and `false` in another case.
- Method `replaceSides`, swaps rectangle sides. 
- Override `equals` and `hashCode` methods.
- Override `toString` method (optional, not tested).

### Advanced Level Requirements:

Complete the General Level Task

Develop [`ArrayRectangles`](src/main/java/com/epam/rd/qa/classes/ArrayRectangles.java) class with the following content:

- Private field `rectangleArray` - array of rectangles
- Constructor that creates a null-elements array of the given length. 
  It should throw an `IllegalArgumentException` if the length is negative or zero. 
- Constructor that receives an arbitrary number of `Rectangle` objects 
  or an array of `Rectangle` objects. It should throw `IllegalArgumentException` 
  if the array is `null` or the array length is zero.
  > It's guaranteed that the array does not contain null values. 
- Method `addRectangle` that adds a `Rectangle` object to the array at the nearest empty place and returns `true` or `false` if there is no free space in the array.
- Method `size` that returns the actual amount of elements in the array. 
  If `addRectangle` returns `true`, then this method should return new amount of elements. 
- Method `indexMaxArea` that returns the index of the first rectangle with the maximum area in the array.
- Method `indexMinPerimeter` that returns the index of the first rectangle with the minimum perimeter in the array.
- Method `numberSquares` that returns the number of squares in the array. 

