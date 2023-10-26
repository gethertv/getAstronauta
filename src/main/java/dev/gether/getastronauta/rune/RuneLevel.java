package dev.gether.getastronauta.rune;

import eu.okaeri.configs.OkaeriConfig;

public class RuneLevel extends OkaeriConfig {

    private String name;
    private double cost;
    private double value;

    public RuneLevel(String name, double cost, double value) {
        this.name = name;
        this.cost = cost;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double getValue() {
        return value;
    }
}
