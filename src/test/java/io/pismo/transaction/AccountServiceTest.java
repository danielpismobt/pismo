package io.pismo.transaction;

import io.pismo.transaction.dtos.account.CreateAccountDTO;
import io.pismo.transaction.dtos.account.ReadAccountDTO;
import io.pismo.transaction.models.Account;
import io.pismo.transaction.repositories.AccountRepository;
import io.pismo.transaction.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountService accountService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@ParameterizedTest
	@CsvSource({
			"123, 1",
			"456, 2",
			"789, 3",
			"998, 4",
			"654265, 5",
			"123412983, 6",
			"21153526, 7",
			"21731982, 8",
			"982139748, 9",
			"23131213, 10"
	})
	void testCreateAccount(String documentNumber, Long id) {
		CreateAccountDTO createAccountDTO = new CreateAccountDTO();
		createAccountDTO.setDocumentNumber(documentNumber);

		when(accountRepository.save(any(Account.class)))
				.thenAnswer(invocation -> {
					Account savedAccount = invocation.getArgument(0);
					savedAccount.setId(id);
					return savedAccount;
				});

		ReadAccountDTO readAccountDTO = accountService.createAccount(createAccountDTO);

		assertEquals(id, readAccountDTO.getId());
		assertEquals(documentNumber, readAccountDTO.getDocumentNumber());
	}
}
