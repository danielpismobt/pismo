package io.pismo.transaction.controllers;

import io.pismo.transaction.dtos.transaction.CreateTransactionDTO;
import io.pismo.transaction.dtos.transaction.ReadTransactionDTO;
import io.pismo.transaction.helpers.URIHelper;
import io.pismo.transaction.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Tag(name = "Transactions", description = "Operations related to transactions")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadTransactionDTO.class),
                    examples = @ExampleObject(
                            name = "Success",
                            value = "    {\n" +
                                    "        \"account_id\": 1,\n" +
                                    "        \"amount\": 112.33,\n" +
                                    "        \"operation_type_id\": 2\n" +
                                    "    }"
                    ))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadTransactionDTO.class),
                    examples = @ExampleObject(
                            name = "BadRequest",
                            value = "{ \"status\": 400}"
                    ))),
            @ApiResponse(responseCode = "404", description = "Any of the ids were not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadTransactionDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "AccountIdNotFound",
                                    value = "{\n" +
                                            "    \"timestamp\": \"...\",\n" +
                                            "    \"status\": 404,\n" +
                                            "    \"error\": \"Account Not Found\",\n" +
                                            "    \"message\": \"Account not found with id: <id>\"\n" +
                                            "}"),
                            @ExampleObject(
                                    name = "OperationTypeIdNotFound",
                                    value = "{\n" +
                                            "    \"timestamp\": \"...\",\n" +
                                            "    \"status\": 404,\n" +
                                            "    \"error\": \"Operation type Not Found\",\n" +
                                            "    \"message\": \"Operation type not found with id: <id>\"\n" +
                                            "}")
                    }))
    })
    public ResponseEntity<ReadTransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        ReadTransactionDTO readTransactionDTO = transactionService.createTransaction(createTransactionDTO);
        URI location = URIHelper.getLocationForCreatedResource(readTransactionDTO.getId());
        return ResponseEntity.created(location).body(readTransactionDTO);
    }
}
