package io.pismo.transaction;

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
import io.pismo.transaction.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private OperationTypeRepository operationTypeRepository;

	@InjectMocks
	private TransactionService transactionService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@ParameterizedTest
	@CsvSource({
			"1, 4, 123456789, 1.0, 1",
			"2, 4, 12312341, 2.5, 2",
			"3, 4, 1928371829, 3.0, 5",
			"4, 4, 129038, 4.5, 9",
			"5, 4, 891237, 5.0, 91"
	})
	void testCreateTransactionWithPaymentOperation(Long accountId, Long operationTypeId, String documentNumber, Double amount, Long transactionId) {
		CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
		createTransactionDTO.setAccountId(accountId);
		createTransactionDTO.setOperationTypeId(operationTypeId);
		createTransactionDTO.setAmount(amount);

		when(accountRepository.findById(accountId))
				.thenReturn(Optional.of(new Account(){{
					setId(accountId);
					setDocumentNumber(documentNumber);
				}}));

		when(operationTypeRepository.findById(operationTypeId))
				.thenReturn(Optional.of(new OperationType(){{
					setId(operationTypeId);
				}}));

		when(transactionRepository.save(any(Transaction.class)))
				.thenAnswer(invocation -> {
					Transaction savedTransaction = invocation.getArgument(0);
					savedTransaction.setId(transactionId);
					return savedTransaction;
				});

		ReadTransactionDTO readTransactionDTO = transactionService.createTransaction(createTransactionDTO);

		assertEquals(transactionId, readTransactionDTO.getId());
		assertEquals(accountId, readTransactionDTO.getAccountId());
		assertEquals(operationTypeId, readTransactionDTO.getOperationTypeId());
		assertEquals(amount, readTransactionDTO.getAmount());
	}

	@ParameterizedTest
	@CsvSource({
			"1, 1, 123456789, 1.0, 1",
			"2, 2, 12312341, 2.5, 2",
			"3, 3, 1928371829, 3.0, 5",
			"4, 2, 129038, 4.5, 9",
			"5, 1, 891237, 5.0, 91"
	})
	void testCreateTransactionWithNonPaymentOperation(Long accountId, Long operationTypeId, String documentNumber, Double amount, Long transactionId) {
		CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
		createTransactionDTO.setAccountId(accountId);
		createTransactionDTO.setOperationTypeId(operationTypeId);
		createTransactionDTO.setAmount(amount);

		when(accountRepository.findById(accountId))
				.thenReturn(Optional.of(new Account(){{
					setId(accountId);
					setDocumentNumber(documentNumber);
				}}));

		when(operationTypeRepository.findById(operationTypeId))
				.thenReturn(Optional.of(new OperationType(){{
					setId(operationTypeId);
				}}));

		when(transactionRepository.save(any(Transaction.class)))
				.thenAnswer(invocation -> {
					Transaction savedTransaction = invocation.getArgument(0);
					savedTransaction.setId(transactionId);
					return savedTransaction;
				});

		ReadTransactionDTO readTransactionDTO = transactionService.createTransaction(createTransactionDTO);

		assertEquals(transactionId, readTransactionDTO.getId());
		assertEquals(accountId, readTransactionDTO.getAccountId());
		assertEquals(operationTypeId, readTransactionDTO.getOperationTypeId());
		assertEquals(amount, readTransactionDTO.getAmount()*-1);
	}

	@ParameterizedTest
	@ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
	void testCreateTransactionWithNonExistingAccount(Long accountId) {
		CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
		createTransactionDTO.setAccountId(accountId);
		createTransactionDTO.setOperationTypeId(1L);
		createTransactionDTO.setAmount(100.0);

		when(accountRepository.findById(accountId))
				.thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> transactionService.createTransaction(createTransactionDTO));
	}

	@ParameterizedTest
	@ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
	void testCreateTransactionWithNonExistingOperation(Long operationId) {
		CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
		createTransactionDTO.setAccountId(1L);
		createTransactionDTO.setOperationTypeId(operationId);
		createTransactionDTO.setAmount(100.0);

		when(accountRepository.findById(1L))
				.thenReturn(Optional.of(new Account(){{
					setId(1L);
					setDocumentNumber("123456");
				}}));

		when(operationTypeRepository.findById(operationId))
				.thenReturn(Optional.empty());

		assertThrows(OperationTypeNotFoundException.class, () -> transactionService.createTransaction(createTransactionDTO));
	}
}