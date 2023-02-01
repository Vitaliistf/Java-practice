package com.epam.rd.autocode.floodfill;

import java.util.StringJoiner;

public interface FloodFill {
    void flood(final String map, final FloodLogger logger);

    static FloodFill getInstance() {
        return new FloodFill() {
            @Override
            public void flood(String map, FloodLogger logger) {
                logger.log(map);
                if(map.contains(String.valueOf(LAND))){
                    char[][] result = mapToCharArray(map);
                    map = map.replace("\n", "");
                    changeArray(map, result);
                    flood(charArrayToMap(result), logger);
                }
            }
            private char[][] mapToCharArray(String map){
                String[] strings = map.split("\n");
                char[][] result = new char[strings.length][];
                for (int i = 0; i < strings.length; i++) {
                    result[i] = strings[i].toCharArray();
                }
                return result;
            }
            private String charArrayToMap(char[][] array){
                StringJoiner result = new StringJoiner("\n");
                for (char[] chars : array) {
                    result.add(new String(chars));
                }
                return result.toString();
            }
            private void changeArray(String map, char[][] array){
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        if(map.charAt(i*array[i].length+j) == WATER){
                            if(i-1 >= 0)
                                array[i-1][j] = WATER;
                            if(i+1 < array.length)
                                array[i+1][j] = WATER;
                            if(j-1 >= 0)
                                array[i][j-1] = WATER;
                            if(j+1 < array[i].length)
                                array[i][j+1] = WATER;
                        }
                    }
                }
            }
        };
    }

    char LAND = '█';
    char WATER = '░';
}
