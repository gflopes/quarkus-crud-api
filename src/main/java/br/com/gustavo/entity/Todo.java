package br.com.gustavo.entity;

import java.time.LocalDate;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection = "todo")
public class Todo extends PanacheMongoEntity {

    private String name;
    private String description;
    private LocalDate date;
    private boolean isCompleted;

    public Todo() { }

    public Todo(final String name, final String description, final boolean isCompleted, final LocalDate date) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}