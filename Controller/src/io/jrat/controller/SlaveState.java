package io.jrat.controller;

import java.awt.Color;

public enum SlaveState {

    DEFAULT(null),
    CONNECTED(new Color(121, 255, 117)),
    DISCONNECTED(new Color(255, 116, 111));

    private Color color;

    SlaveState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
