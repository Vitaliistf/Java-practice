package com.epam.rd.autocode.concurrenttictactoe;

public interface Player extends Runnable{
    static Player createPlayer(final TicTacToe ticTacToe, final char mark, PlayerStrategy strategy) {
        return new Player() {
            private static final char WHITESPACE = ' ';
            @Override
            public void run() {
                while(isEnded()) {
                    synchronized (ticTacToe) {
                        if (canMakeTurn()) {
                            Move move = strategy.computeMove(mark, ticTacToe);
                            ticTacToe.setMark(move.row, move.column, mark);
                        }
                    }
                }
            }
            private boolean canMakeTurn() {
                return isEnded() && ticTacToe.lastMark() != mark;
            }
            private boolean isEnded() {
                char[][] table = ticTacToe.table();
                //Checking rows and cols
                for(int i = 0; i < 3; i++) {
                    if(table[i][0] == table[i][1] && table[i][1] == table[i][2]) {
                        return table[i][0] == WHITESPACE;
                    } else if(table[0][i] == table[1][i] && table[1][i] == table[2][i]) {
                        return table[0][i] == WHITESPACE;
                    }
                }
                //Checking diagonals
                if(table[0][0] == table[1][1] && table[1][1] == table[2][2]) {
                    return table[0][0] == WHITESPACE;
                } else if(table[0][2] == table[1][1] && table[1][1] == table[2][0]) {
                    return table[0][2] == WHITESPACE;
                }
                return true;
            }
        };
    }
}
