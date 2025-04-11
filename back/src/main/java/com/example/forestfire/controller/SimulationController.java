package com.example.forestfire.controller;

import com.example.forestfire.model.Row;
import com.example.forestfire.model.SimulationResponse;
import com.example.forestfire.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "http://localhost:5173")
public class SimulationController {

    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping("/init")
    public List<Row> getInitialGrid() {
        return simulationService.getInitialGrid();
    }
    
    @GetMapping("/initForce")
    public List<Row> getInitialGrid(@RequestParam int height, @RequestParam int width) {
        return simulationService.getInitialGrid(height, width, true);
    }

    @PostMapping("/next")
    public SimulationResponse getNextStep(@RequestBody List<Row> currentGrid) {
        return simulationService.getNextStep(currentGrid);
    }
}
