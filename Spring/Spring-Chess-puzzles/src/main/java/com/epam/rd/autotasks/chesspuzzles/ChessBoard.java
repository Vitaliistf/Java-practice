package com.epam.rd.autotasks.chesspuzzles;

import java.util.Collection;

public interface ChessBoard {
    static ChessBoard of(Collection<ChessPiece> pieces){
        return new ChessBoard() {
            @Override
            public String state() {
                char[][] state = new char[8][8];
                for(ChessPiece piece : pieces) {
                    Cell cell = piece.getCell();
                    int x = cell.number-1;
                    int y = (int) cell.letter - 65;
                    state[x][y] = piece.toChar();
                }
                return charArrayToString(state);
            }
            private String charArrayToString(char[][] arr) {
                StringBuilder result = new StringBuilder();
                for(int i = 7; i >= 0; i--) {
                    for(int j = 0; j < arr.length; j++) {
                        if(arr[i][j] == '\u0000') {
                            result.append('.');
                        } else {
                            result.append(arr[i][j]);
                        }
                    }
                    result.append("\n");
                }
                return result.deleteCharAt(result.length()-1).toString();
            }
        };
    }

    String state();
}
