package com.flashcards.model;

import java.time.LocalDateTime;

public abstract class Card {
    // Fields of the Card class
    // id as primary key
    private Long id;
    private String question;
    private String subject;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;

    // Constructor for the Card class
    public Card(String question, String subject) {
        this.question = question;
        this.subject = subject;
        this.createdAt = LocalDateTime.now();
    }

    // Abstract method to be implemented by subclasses
    public abstract AnswerResult checkAnswer(String userInput);

    // Getters for the Card class
    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public abstract String getType();
}
