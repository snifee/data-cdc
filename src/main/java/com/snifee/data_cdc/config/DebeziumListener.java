package com.snifee.data_cdc.config;

import com.snifee.data_cdc.postresql.entity.Transaction;
import com.snifee.data_cdc.service.TransactionService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class DebeziumListener {
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;


    private TransactionService transactionService;

    private final Executor executor;

    public DebeziumListener(Configuration transactionConfiguration, TransactionService transactionService){

        this.executor = Executors.newSingleThreadExecutor();

        this.debeziumEngine = DebeziumEngine
                .create(ChangeEventFormat.of(Connect.class))
                .using(transactionConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();

        this.transactionService = transactionService;
    }


    private void handleChangeEvent(RecordChangeEvent<SourceRecord> recordRecordChangeEvent) {
        SourceRecord sourceRecord = recordRecordChangeEvent.record();
        Struct sourceRecordChangeValue= (Struct) sourceRecord.value();

        System.out.println(sourceRecordChangeValue);

        if (sourceRecordChangeValue!=null){

            Struct paylodStruct = (Struct) sourceRecordChangeValue.get("after");
            if (paylodStruct!=null) {
                Transaction paylod = new Transaction();
                paylod.setId(UUID.fromString(paylodStruct.getString("id")));
                paylod.setDestinationBank(paylodStruct.getString("destination_bank"));
                paylod.setAmountReceived(paylodStruct.getFloat64("amount_received"));

                this.transactionService.calculate(paylod);
            }
        }
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }


}
