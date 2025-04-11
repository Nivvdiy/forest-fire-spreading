package com.example.forestfire.service;

import com.example.forestfire.config.SimulationProperties;
import com.example.forestfire.model.Cell;
import com.example.forestfire.model.CellState;
import com.example.forestfire.model.Row;
import com.example.forestfire.model.SimulationResponse;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SimulationService {

    private final SimulationProperties properties;
    private final Random random = new Random();

    public SimulationService(SimulationProperties properties) {
        this.properties = properties;
    }
    
    public List<Row> getInitialGrid(){
        int width = properties.getWidth();
        int height = properties.getHeight();
        return getInitialGrid(height, width, false);
    }

    public List<Row> getInitialGrid(int height, int width, boolean randomStart) {
        List<Row> grid = new ArrayList<>();

        for (int y = 0; y < height; y++) {
        	Row row = new Row(y);
        	grid.add(row);
            for (int x = 0; x < width; x++) {
            	row.addCell(x, CellState.Idle);
            }
        }
        
        Collections.sort(grid, Comparator.comparingInt(Row::getY));
        List<Pair<Integer,Integer>> startingPositions = new ArrayList<>();

        if(randomStart) {
        	int initialFiresNumber = random.nextInt(1, Math.max((height*width)/100, 2));
        	for(int i=0; i<initialFiresNumber; i++) {
        		int x = random.nextInt(0, width);
        		int y = random.nextInt(0, height);
        		startingPositions.add(new Pair<Integer, Integer>(x, y));
        	}
        } else if (properties.getInitialFirePositions() != null && !properties.getInitialFirePositions().isEmpty()){
        	for (String pos : properties.getInitialFirePositions()) {
                String[] parts = pos.split(",");
                if (parts.length == 2) {
                    try {
                        int x = Integer.parseInt(parts[0].trim());
                        int y = Integer.parseInt(parts[1].trim());
                		startingPositions.add(new Pair<Integer, Integer>(x, y));
                    } catch (NumberFormatException e) {
                    	//Ignore incorrect position
                    }
                }
            }
        } else {
            int centerX = width / 2;
            int centerY = height / 2;
    		startingPositions.add(new Pair<Integer, Integer>(centerX, centerY));
        }
        
        if(startingPositions.isEmpty()) {
            grid.get(0).getCell(0).setState(CellState.OnFire);
        } else {
            for (Pair<Integer, Integer> position : startingPositions) {
            	int x = position.getValue0();
            	int y = position.getValue1();
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    grid.get(y).getCell(x).setState(CellState.OnFire);
                }
    		}
        }
        return grid;
    }

    public SimulationResponse getNextStep(List<Row> oldGrid) {
    	
    	Collections.sort(oldGrid, Comparator.comparingInt(Row::getY));
    	
        int height = oldGrid.size();
        int width = oldGrid.get(0).getWidth();
        List<Row> newGrid = new ArrayList<>();

        for (int y = 0; y < height; y++) {
        	Row row = new Row(y);
            newGrid.add(row);
            for (int x = 0; x < width; x++) {
                Cell oldCell = oldGrid.get(y).getCell(x);
                row.addCell(x, oldCell.getState());
            }
        }
        
        Collections.sort(newGrid, Comparator.comparingInt(Row::getY));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell oldCell = oldGrid.get(y).getCell(x);
                if (oldCell.getState() == CellState.OnFire) {
                	newGrid.get(y).getCell(x).setState(CellState.Burned);
                	
                    spreadFire(newGrid, x, y - 1);
                    spreadFire(newGrid, x, y + 1);
                    spreadFire(newGrid, x - 1, y);
                    spreadFire(newGrid, x + 1, y);
                }
            }
        }

        boolean finished = true;
        for (Row row : newGrid) {
            for (Cell c : row.getCells()) {
                if (c.getState() == CellState.OnFire) {
                    finished = false;
                    break;
                }
            }
            if (!finished) break;
        }

        return new SimulationResponse(newGrid, finished);
    }

    private void spreadFire(List<Row> grid, int x, int y) {
        int height = grid.size();
        int width = grid.get(0).getWidth();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        Cell target = grid.get(y).getCell(x);
        if (target.getState() == CellState.Idle) {
            if (random.nextDouble() < properties.getPropagationProbability()) {
                target.setState(CellState.OnFire);
            }
        }
    }
}
