package com.simec.todolistapi.dto;

public class TodoResponseDto {
    private final String title;
    private final String description;

    public TodoResponseDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
