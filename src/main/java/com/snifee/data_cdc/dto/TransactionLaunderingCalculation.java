package com.snifee.data_cdc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLaunderingCalculation {

    private double calculated_amount;

    private String bank;
}
