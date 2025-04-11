package com.example.forestfire.model;

import java.util.List;

public class SimulationResponse {
    private List<Row> grid;
    private boolean finished;

    public SimulationResponse() {
    }

    public SimulationResponse(List<Row> grid, boolean finished) {
        this.grid = grid;
        this.finished = finished;
    }

    public List<Row> getGrid() {
        return grid;
    }

    public void setGrid(List<Row> grid) {
        this.grid = grid;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
