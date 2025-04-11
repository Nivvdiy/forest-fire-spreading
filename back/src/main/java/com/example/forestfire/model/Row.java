package com.example.forestfire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Row {
	
	private int y;
	private List<Cell> cells = new ArrayList<>();
	
	public Row() {}
	
	public Row(int y) {
		this.y = y;
	}
	
	public void addCell(int x, CellState state) {
		cells.add(new Cell(x, this.y, state));
		Collections.sort(cells, Comparator.comparingInt(Cell::getX));
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public Cell getCell(int x) {
		return cells.get(x);
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return cells.size();
	}

}
