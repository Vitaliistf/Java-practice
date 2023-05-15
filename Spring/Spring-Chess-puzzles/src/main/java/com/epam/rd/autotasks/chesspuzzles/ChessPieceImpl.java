package com.epam.rd.autotasks.chesspuzzles;

public class ChessPieceImpl implements ChessPiece{
    private final char figure;
    private final Cell cell;

    public ChessPieceImpl(Cell cell, char figure) {
        this.figure = figure;
        this.cell = cell;
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public char toChar() {
        return figure;
    }
}
