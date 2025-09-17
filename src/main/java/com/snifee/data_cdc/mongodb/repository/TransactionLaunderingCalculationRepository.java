package com.snifee.data_cdc.mongodb.repository;

import com.snifee.data_cdc.mongodb.entity.TransactionLaunderingCalculation;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.UUID;

@Repository
public interface TransactionLaunderingCalculationRepository extends MongoRepository<TransactionLaunderingCalculation,String> {

    @NativeQuery(value = "{'bank': ?0}")
    TransactionLaunderingCalculation findByBank(String bank);
}
