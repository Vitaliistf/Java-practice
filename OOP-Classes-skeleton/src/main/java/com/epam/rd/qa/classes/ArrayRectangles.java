package com.epam.rd.qa.classes;

public class ArrayRectangles {

    private Rectangle[] rectangleArray;

    public ArrayRectangles(int size) {
        if(size <= 0)
            throw new IllegalArgumentException();

        this.rectangleArray = new Rectangle[size];
    }

    public ArrayRectangles(Rectangle... rectangles) {
        if(rectangles == null || rectangles.length == 0)
            throw new IllegalArgumentException();

        this.rectangleArray = rectangles;
    }

    public boolean addRectangle(Rectangle rectangle) {
        for(int i = 0; i < rectangleArray.length; i++){
            if(rectangleArray[i] == null){
                rectangleArray[i] = rectangle;
                return true;
            }
        }
        return false;
    }

    public int size() {
        int count = 0;
        for(int i = 0; i < rectangleArray.length; i++){
            if(rectangleArray[i] != null)
                count++;
        }
        return count;
    }

    public int indexMaxArea() {
        double max_area = rectangleArray[0].area();
        int index = 0;

        for(int i = 0; i < rectangleArray.length; i++){
            if(max_area < rectangleArray[i].area()){
                max_area = rectangleArray[i].area();
                index = i;
            }
        }
        return index;
    }

    public int indexMinPerimeter() {
        double min_perimeter = rectangleArray[0].perimeter();
        int index = 0;

        for(int i = 0; i < rectangleArray.length; i++){
            if(min_perimeter > rectangleArray[i].area()){
                min_perimeter = rectangleArray[i].area();
                index = i;
            }
        }
        return index;
    }

    public int numberSquares() {
        int count = 0;

        for(int i = 0; i < rectangleArray.length; i++){
            if(rectangleArray[i].isSquare())
                count++;
        }
        return count;
    }
}
