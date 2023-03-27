package com.lucas.restspringboot.models;

public class Hello {

    private final Long id;

    private final String content;

    public Hello(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
