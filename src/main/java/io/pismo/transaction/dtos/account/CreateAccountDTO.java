package io.pismo.transaction.dtos.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateAccountDTO {

        @NotNull
        @NotEmpty
        @NotBlank
        @JsonProperty("document_number")
        private String documentNumber;

        public String getDocumentNumber() {
            return documentNumber;
        }

        public void setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
        }
}
