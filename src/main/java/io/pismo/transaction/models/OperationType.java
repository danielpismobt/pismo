package io.pismo.transaction.models;

import jakarta.persistence.*;

@Entity
public class OperationType {

    @Id
    private Long id;

    @Column(nullable = false)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPaymentTransaction(){
        return this.id == 4L;
    }
}