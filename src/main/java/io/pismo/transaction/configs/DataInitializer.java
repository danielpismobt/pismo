package io.pismo.transaction.configs;

import io.pismo.transaction.models.OperationType;
import io.pismo.transaction.repositories.OperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Override
    public void run(String... args) {
        OperationType compraAVista = new OperationType();
        compraAVista.setId(1L);
        compraAVista.setDescription("COMPRA A VISTA");

        OperationType compraParcelada = new OperationType();
        compraParcelada.setId(2L);
        compraParcelada.setDescription("COMPRA PARCELADA");

        OperationType saque = new OperationType();
        saque.setId(3L);
        saque.setDescription("SAQUE");

        OperationType pagamento = new OperationType();
        pagamento.setId(4L);
        pagamento.setDescription("PAGAMENTO");

        operationTypeRepository.saveAll(Arrays.asList(compraAVista, compraParcelada, saque, pagamento));
    }
}
