package com.example.accesscontrolsystem.model;

public enum Positions {
    MANAGER("Manager"),
    REGULAR("Regular");
    private final String displayText;

    Positions(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }

}

