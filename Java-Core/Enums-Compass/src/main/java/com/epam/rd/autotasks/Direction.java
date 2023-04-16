package com.epam.rd.autotasks;

public enum Direction {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

    Direction(final int degrees) {
        this.degrees = degrees;
    }

    private int degrees;

    public static Direction ofDegrees(int degrees) {
        if(degrees < 0)
            degrees += 360;
        else if (degrees > 359)
            degrees %= 360;

        for (Direction dir : Direction.values()) {
            if(dir.degrees == degrees){
                return dir;
            }
        }
        return null;
    }

    public static Direction closestToDegrees(int degrees) {
        int minDegrees = 360;
        Direction result = N;

        if(degrees < 0)
            degrees += 360;
        else if (degrees > 359)
            degrees %= 360;

        for (Direction dir : Direction.values()) {
            if(Math.abs(dir.degrees - degrees) <= minDegrees){
                minDegrees = Math.abs(dir.degrees - degrees);
                result = dir;
            }
        }
        return result;
    }

    public Direction opposite() {
        return Direction.ofDegrees(this.degrees % 360 - 180);
    }

    public int differenceDegreesTo(Direction direction) {
        if(direction.degrees < 0) {
            direction.degrees += 360;
        } else if (direction.degrees > 359) {
            direction.degrees %= 360;
        }

        if(this == N && direction.degrees > 180) {
            return direction.degrees % 90;
        } else {
            return Math.abs(direction.degrees - this.degrees);
        }
    }
}
