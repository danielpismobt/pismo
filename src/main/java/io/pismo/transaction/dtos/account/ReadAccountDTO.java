package io.pismo.transaction.dtos.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadAccountDTO {

        @JsonProperty("account_id")
        private Long id;
        @JsonProperty("document_number")
        private String documentNumber;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDocumentNumber() {
            return documentNumber;
        }

        public void setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
        }
}
