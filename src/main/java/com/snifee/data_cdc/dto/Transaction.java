package com.snifee.data_cdc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

        private UUID id;

        private Timestamp datetime;

        private String sourceBank;

        private String senderAccount;

        private String destinationBank;

        private String receiverAccount;

        private double amountReceived;

        private String receivingCurrency;

        private double amountPaid;

        private String paymentCurrency;

        private String paymentFormat;

        private boolean laundering;
}
