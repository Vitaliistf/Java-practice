package com.epam.rd.autotasks;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        String ships1 = longToFormattedString(ships);
        String shots1 = longToFormattedString(shots);

        int i = shot.charAt(0)-65; //Subtracting 65 and 49 to get correct results
        int j = shot.charAt(1)-49;
        int index = i + j * 8;

        String temp = shots1.substring(0, index) + 1 + shots1.substring(index+1);
        shots = Long.parseUnsignedLong(temp, 2);

        return ships1.charAt(index) == '1';
    }

    private String longToFormattedString(long s){
        return String.format("%64s", Long.toBinaryString(s)).replace(' ', '0');
    }

    public String state() {
        StringBuilder result = new StringBuilder();
        String ships1 = longToFormattedString(ships);
        String shots1 = longToFormattedString(shots);

        for (int i = 0; i < 64; i++) {
            if(i % 8 == 0)
                result.append("\n");

            if(ships1.charAt(i) == '0' && shots1.charAt(i) == '0')
                result.append(".");
            else if (ships1.charAt(i) == '0' && shots1.charAt(i) == '1')
                result.append("×");
            else if (ships1.charAt(i) == '1' && shots1.charAt(i) == '0')
                result.append("☐");
            else if (ships1.charAt(i) == '1' && shots1.charAt(i) == '1')
                result.append("☒");
        }
        return result.toString();
    }
}
