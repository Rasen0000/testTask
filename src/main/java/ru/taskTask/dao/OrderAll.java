package ru.taskTask.dao;

import javax.persistence.*;

@Entity

public class OrderAll {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String client;

    private String date;

    private String address;

    public OrderAll() {
    }

    public OrderAll(String client, String date, String address) {
        this.client = client;
        this.date = date;
        this.address = address;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
