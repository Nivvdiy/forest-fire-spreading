package com.example.forestfire.model;

public class Cell {

    private int x;
    private int y;
    private CellState state;

    public Cell() {}

    public Cell(int x, int y, CellState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public CellState getState() {
        return state;
    }
    public void setState(CellState state) {
        this.state = state;
    }
}
