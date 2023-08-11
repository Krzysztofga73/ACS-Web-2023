package com.example.accesscontrolsystem.model;


public enum Departments {
    HUMAN_RESOURCES("Human Resources"),
    IT("It"),
    ACCOUNTING_AND_FINANCE("Accounting and Finance"),
    MARKETING("Marketing"),
    RESEARCH_AND_DEVELOPMENT("Research and Development"),
    PRODUCTION("Production"),
    NONE("None");


    private final String displayText;

    Departments(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}