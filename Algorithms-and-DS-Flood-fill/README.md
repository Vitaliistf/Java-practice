# Flood Fill

## Description 
Implement [`com.epam.rd.autocode.floodfill.FloodFill`](src/main/java/com/epam/rd/autocode/floodfill/FloodFill.java) `getInstance` method.

It should return a `FloodFill` instance.

Flood fill means that it takes initial rectangular area containing **land** and **water** cells and imitates flooding it:
each flood step **water** cell converts neighbor **land** cells (*up*, *left*, *right* and *down*) to **water**.

It takes some steps to completely flood an area and after that algorithm stops.

You must implement `FloodFill` in recursive manner and make `flood` method call itself until all the area becomes water.

Method takes two parameters: 
- `String map` is an area representation using 
    char (█) for **land** cells, 
    char (░) for **water** cells and 
    char ("\n") for line breaks;
- `FloodLogger logger` is a current flood state logger.
    Call `logger.log(String floodState)` to report a flood state on each step. Make sure to call it **once** per `flood` method call.

## Example

Initial state:
```
██░█
████
█░██
████
```
Reported flood steps (4 recursive `flood` calls):
```
██░█
████
█░██
████

█░░░
█░░█
░░░█
█░██

░░░░
░░░░
░░░░
░░░█

░░░░
░░░░
░░░░
░░░░
```
