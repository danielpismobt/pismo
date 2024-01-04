package io.pismo.transaction.services;

import io.pismo.transaction.dtos.account.CreateAccountDTO;
import io.pismo.transaction.dtos.account.ReadAccountDTO;
import io.pismo.transaction.models.Account;
import io.pismo.transaction.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public ReadAccountDTO createAccount(CreateAccountDTO createAccountDTO) {
        Account account = mapToAccount(createAccountDTO);
        Account createdAccount = accountRepository.save(account);
        return mapToReadAccountDTO(createdAccount);
    }

    public ReadAccountDTO getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(this::mapToReadAccountDTO)
                .orElse(null);
    }

    private ReadAccountDTO mapToReadAccountDTO(Account account) {
        ReadAccountDTO readAccountDTO = new ReadAccountDTO();
        readAccountDTO.setId(account.getId());
        readAccountDTO.setDocumentNumber(account.getDocumentNumber());
        return readAccountDTO;
    }

    private Account mapToAccount(CreateAccountDTO createAccountDTO) {
        Account account = new Account();
        account.setDocumentNumber(createAccountDTO.getDocumentNumber());
        return account;
    }

}
