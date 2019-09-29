package com.websocket.springboot.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Users implements Serializable {
    private String  name;

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                '}';
    }
}
