package com.example.forestfire.service;

import com.example.forestfire.config.SimulationProperties;
import com.example.forestfire.model.Cell;
import com.example.forestfire.model.CellState;
import com.example.forestfire.model.Row;
import com.example.forestfire.model.SimulationResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationServiceTest {

    @Test
    void getInitialGrid_dimensionsAndFire() {
        SimulationProperties props = new SimulationProperties();
        props.setWidth(5);
        props.setHeight(4);
        SimulationService service = new SimulationService(props);

        List<Row> grid = service.getInitialGrid();

        assertEquals(4, grid.size());
        for (Row row : grid) {
            assertEquals(5, row.getWidth());
        }

        long fireCount = grid.stream()
                .flatMap(r -> r.getCells().stream())
                .filter(c -> c.getState() == CellState.OnFire)
                .count();
        assertTrue(fireCount >= 1);
    }

    @Test
    void getNextStep_burnsAndSpreads() {
        SimulationProperties props = new SimulationProperties();
        props.setWidth(3);
        props.setHeight(3);
        props.setPropagationProbability(1.0); // deterministic spreading
        SimulationService service = new SimulationService(props);

        List<Row> grid = service.getInitialGrid(); // center cell on fire

        SimulationResponse response = service.getNextStep(grid);
        List<Row> next = response.getGrid();

        assertEquals(CellState.Burned, next.get(1).getCell(1).getState());

        Cell up = next.get(0).getCell(1);
        Cell down = next.get(2).getCell(1);
        Cell left = next.get(1).getCell(0);
        Cell right = next.get(1).getCell(2);

        assertEquals(CellState.OnFire, up.getState());
        assertEquals(CellState.OnFire, down.getState());
        assertEquals(CellState.OnFire, left.getState());
        assertEquals(CellState.OnFire, right.getState());
    }
}
