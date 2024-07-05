package com.highway.batch.customer;

import com.highway.batch.customer.model.CustomerAddressUpdate;
import com.highway.batch.customer.model.CustomerContactUpdate;
import com.highway.batch.customer.model.CustomerNameUpdate;
import com.highway.batch.customer.model.CustomerUpdate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.classify.Classifier;

public class CustomerUpdateClassifier implements Classifier<CustomerUpdate,ItemWriter<? extends  CustomerUpdate>> {
   private final JdbcBatchItemWriter<CustomerUpdate> recordType1ItemWriter;
   private final JdbcBatchItemWriter<CustomerUpdate> recordType2ItemWriter;
   private final JdbcBatchItemWriter<CustomerUpdate> recordType3ItemWriter;

    public CustomerUpdateClassifier(JdbcBatchItemWriter<CustomerUpdate> recordType1ItemWriter, JdbcBatchItemWriter<CustomerUpdate> recordType2ItemWriter, JdbcBatchItemWriter<CustomerUpdate> recordType3ItemWriter) {
        this.recordType1ItemWriter = recordType1ItemWriter;
        this.recordType2ItemWriter = recordType2ItemWriter;
        this.recordType3ItemWriter = recordType3ItemWriter;
    }

    @Override
    public ItemWriter<? extends CustomerUpdate> classify(CustomerUpdate customerUpdate) {
        if(customerUpdate instanceof CustomerNameUpdate){
            return recordType1ItemWriter;
        }else if(customerUpdate instanceof CustomerAddressUpdate){
            return recordType2ItemWriter;
        }else if(customerUpdate instanceof CustomerContactUpdate) {
            return recordType3ItemWriter;
        }else {
            throw new IllegalArgumentException("Unsupported customer update: " + customerUpdate.getClass().getCanonicalName());
        }
    }
}
