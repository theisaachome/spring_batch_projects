package com.highway.batch.config;

import com.highway.batch.customer.model.CustomerUpdate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ImportJobConfiguration {

    @Bean
    public Job job() {
        return  null;
    }
    @Bean
    public Step importCustomerUpdates(){
        return null;
    }
    @Bean
    @StepScope
    public FlatFileItemReader<CustomerUpdate> customerUpdateReader(
            @Value("#{jobParameters['customerUpdateFile']}")Resource resource) {
        return new FlatFileItemReaderBuilder<CustomerUpdate>()
                .name("customerUpdateItemReader")
                .resource(resource)
                .lineTokenizer(null)
                .fieldSetMapper(null)
                .build();

    }
}
