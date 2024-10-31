package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_info")
public class InvoiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    public InvoiceInfo() {
    }

    public InvoiceInfo(Integer id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
