package com.example.forestfire.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "simulation")
public class SimulationProperties {
	private int width = 10;
    private int height = 10;
    private double propagationProbability = 0.3;
    private List<String> initialFirePositions;

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public double getPropagationProbability() {
        return propagationProbability;
    }
    public void setPropagationProbability(double propagationProbability) {
        this.propagationProbability = propagationProbability;
    }
    public List<String> getInitialFirePositions() {
        return initialFirePositions;
    }
    public void setInitialFirePositions(List<String> initialFirePositions) {
        this.initialFirePositions = initialFirePositions;
    }
}
