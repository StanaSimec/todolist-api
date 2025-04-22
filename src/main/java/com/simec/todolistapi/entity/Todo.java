package com.simec.todolistapi.entity;

public class Todo {
    private final long id;
    private final String title;
    private final String description;
    private final long userId;

    public Todo(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.userId = builder.userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getUserId() {
        return userId;
    }

    public static class Builder {
        private long id = 0;
        private String title = "";
        private String description = "";
        private long userId = 0;

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Todo build() {
            return new Todo(this);
        }
    }
}
