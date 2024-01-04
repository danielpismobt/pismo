package io.pismo.transaction.controllers;

import io.pismo.transaction.dtos.account.CreateAccountDTO;
import io.pismo.transaction.dtos.account.ReadAccountDTO;
import io.pismo.transaction.helpers.URIHelper;
import io.pismo.transaction.services.AccountService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Tag(name = "Accounts", description = "Operations related to accounts")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts/{id}")
    @Operation(summary = "Get account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved account", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadAccountDTO.class),
                    examples = @ExampleObject(
                            name = "Success",
                            value = "{ \"id\": 1, \"document_number\": \"12345678\"}"
                    ))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadAccountDTO.class),
                    examples = @ExampleObject(
                            name = "AccountNotFound",
                            value = "Error: response status is 404"
                    )))
    })
    public ResponseEntity<ReadAccountDTO> getAccountById(@PathVariable Long id) {
        ReadAccountDTO readAccountDTO = accountService.getAccountById(id);
        if (readAccountDTO != null) {
            return ResponseEntity.ok(readAccountDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/accounts")
    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadAccountDTO.class),
                    examples = @ExampleObject(
                            name = "Success",
                            value = "{ \"id\": 1, \"document_number\": \"12345678\"}"
                    ))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReadAccountDTO.class),
                    examples = @ExampleObject(
                            name = "BadRequest",
                            value = "{ \"status\": 400}"
                    )))
    })
    public ResponseEntity<ReadAccountDTO> createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) {
        ReadAccountDTO readAccountDto = accountService.createAccount(createAccountDTO);
        URI location = URIHelper.getLocationForCreatedResource(readAccountDto.getId());
        return ResponseEntity.created(location).body(readAccountDto);
    }

}
