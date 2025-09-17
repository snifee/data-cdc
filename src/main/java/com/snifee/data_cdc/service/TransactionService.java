package com.snifee.data_cdc.service;

import com.snifee.data_cdc.mongodb.entity.TransactionLaunderingCalculation;
import com.snifee.data_cdc.mongodb.repository.TransactionLaunderingCalculationRepository;
import com.snifee.data_cdc.postresql.entity.Transaction;
import io.debezium.data.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionLaunderingCalculationRepository repository;

    public void calculate(Transaction paylod){
        TransactionLaunderingCalculation calculation = repository.findByBank(paylod.getDestinationBank());
        if (calculation!=null){
            calculation.setCalculated_amount(calculation.getCalculated_amount()+ paylod.getAmountReceived());
            repository.save(calculation);
        }else{
            TransactionLaunderingCalculation newCalculation = new TransactionLaunderingCalculation();
            newCalculation.setCalculated_amount(paylod.getAmountReceived());
            newCalculation.setBank(paylod.getDestinationBank());

            repository.insert(newCalculation);
        }
    }
}
