package io.pismo.transaction.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private OperationType operationType;

    private Double amount;

    private LocalDateTime eventDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount, boolean isPayment) {
        if (isPayment) {
            this.amount = amount;
        }
        else{
            this.amount = amount * (-1);
        }
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
