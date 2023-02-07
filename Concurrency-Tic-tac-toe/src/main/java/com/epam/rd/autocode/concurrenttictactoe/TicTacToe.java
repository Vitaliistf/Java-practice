package com.epam.rd.autocode.concurrenttictactoe;

public interface TicTacToe {

    /**
     * Sets a mark in cell with specified coordinates.
     * @param x - x coordinate.
     * @param y - y coordinate.
     * @param mark - mark to set.
     */
    void setMark(int x, int y, char mark);

    /**
     * Returns a COPY of current table with marks.
     * Note, edit of that copy should not affect the source TicTacToe object.
     * @return a copy of current table.
     */
    char[][] table();

    /**
     * Returns last mark that was set in a table.
     * @return last mark that was set in a table.
     */
    char lastMark();

    static TicTacToe buildGame() {
        return new TicTacToe() {
            private static final char WHITESPACE = ' ';
            private final char[][] table = new char[][]{{WHITESPACE, WHITESPACE, WHITESPACE},
                                                        {WHITESPACE, WHITESPACE, WHITESPACE},
                                                        {WHITESPACE, WHITESPACE, WHITESPACE}};
            private char lastMark;

            @Override
            public void setMark(int x, int y, char mark) {
                if(table[x][y] == WHITESPACE){
                    table[x][y] = mark;
                    lastMark = mark;
                } else {
                    throw new IllegalArgumentException();
                }
            }

            @Override
            public char[][] table() {
                char[][] tableCopy = new char[3][3];
                for (int i = 0; i < table.length; i++) {
                    System.arraycopy(table[i], 0, tableCopy[i], 0, table.length);
                }
                return tableCopy;
            }

            @Override
            public char lastMark() {
                return lastMark;
            }
        };
    }
}
