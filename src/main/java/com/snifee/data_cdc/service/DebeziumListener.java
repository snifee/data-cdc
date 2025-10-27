package com.snifee.data_cdc.service;

import com.snifee.data_cdc.dto.Transaction;
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

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DebeziumListener {
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    private final ExecutorService executorService;

    public DebeziumListener(Configuration debeziumListenerConfiguration){
        this.executorService = Executors.newSingleThreadExecutor();

        this.debeziumEngine = DebeziumEngine
                .create(ChangeEventFormat.of(Connect.class))
                .using(debeziumListenerConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();
    }


    private void handleChangeEvent(RecordChangeEvent<SourceRecord> recordRecordChangeEvent) {
        SourceRecord sourceRecord = recordRecordChangeEvent.record();
        Struct sourceRecordChangeValue= (Struct) sourceRecord.value();

        if (sourceRecordChangeValue!=null){
//            Struct paylodStruct = (Struct) sourceRecordChangeValue.get("after");
            System.out.println("data produced + "+ sourceRecord);
//            if (paylodStruct!=null) {
//                Transaction paylod = new Transaction();
//
//            }
        }
    }

    @PostConstruct
    private void start() {
        this.executorService.execute(this.debeziumEngine);
        log.info("Debezium Engine started");
    }
    @PreDestroy
    private void stop() {
        try{
            if (this.debeziumEngine != null) {
                this.executorService.shutdown();
                while(this.executorService.awaitTermination(10, TimeUnit.SECONDS)){
                    log.info("wait for 10 second before termination");
                }
            }
        }catch (InterruptedException e){
            log.info("error when shutdown the execution service");
        }
        log.info("Debezium Engine stopped");
    }


}
