package com.snifee.data_cdc.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document(collection = "transaction_laundering_calculation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLaunderingCalculation {

    @Field(name = "calculated_amount")
    private double calculated_amount;

    @MongoId
    @Field(name = "bank")
    private String bank;
}
