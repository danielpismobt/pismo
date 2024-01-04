package io.pismo.transaction.services;

import io.pismo.transaction.dtos.transaction.CreateTransactionDTO;
import io.pismo.transaction.dtos.transaction.ReadTransactionDTO;
import io.pismo.transaction.exceptions.AccountNotFoundException;
import io.pismo.transaction.exceptions.OperationTypeNotFoundException;
import io.pismo.transaction.models.Account;
import io.pismo.transaction.models.OperationType;
import io.pismo.transaction.models.Transaction;
import io.pismo.transaction.repositories.AccountRepository;
import io.pismo.transaction.repositories.OperationTypeRepository;
import io.pismo.transaction.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    public ReadTransactionDTO createTransaction(CreateTransactionDTO createTransactionDTO) {

        Long accountId = createTransactionDTO.getAccountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));

        Long operationId = createTransactionDTO.getOperationTypeId();
        OperationType operationType = operationTypeRepository.findById(operationId)
                .orElseThrow(() -> new OperationTypeNotFoundException("Operation type not found with id: " + operationId));


        Transaction transaction = mapToTransaction(createTransactionDTO, account, operationType);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToReadTransactionDTO(savedTransaction);
    }

    private Transaction mapToTransaction(CreateTransactionDTO createTransactionDTO, Account account, OperationType operationType) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(createTransactionDTO.getAmount(), operationType.isPaymentTransaction());
        transaction.setEventDate(LocalDateTime.now());
        return transaction;
    }

    private ReadTransactionDTO mapToReadTransactionDTO(Transaction transaction) {
        ReadTransactionDTO readTransactionDTO = new ReadTransactionDTO();
        readTransactionDTO.setId(transaction.getId());
        readTransactionDTO.setAccountId(transaction.getAccount().getId());
        readTransactionDTO.setOperationTypeId(transaction.getOperationType().getId());
        readTransactionDTO.setAmount(transaction.getAmount());
        readTransactionDTO.setEventDate(transaction.getEventDate());
        return readTransactionDTO;
    }


}
