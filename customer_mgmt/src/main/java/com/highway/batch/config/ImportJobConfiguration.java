package com.highway.batch.config;

import com.highway.batch.customer.model.CustomerUpdate;
import com.highway.batch.mapper.CustomerFieldSetMapper;
import com.highway.batch.mapper.CustomerLineTokenizer;
import com.highway.batch.validator.CustomerItemValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
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
    public FieldSetMapper<CustomerUpdate> customerFieldSetMapper(){
        return new CustomerFieldSetMapper();
    }
    @Bean
    public CustomerLineTokenizer lineTokenizer() throws Exception {
        return new CustomerLineTokenizer();
    }
    @Bean
    @StepScope
    public FlatFileItemReader<CustomerUpdate> customerUpdateReader(
            @Value("#{jobParameters['customerUpdateFile']}")Resource resource) throws Exception {
        return new FlatFileItemReaderBuilder<CustomerUpdate>()
                .name("customerUpdateItemReader")
                .resource(resource)
                .lineTokenizer(lineTokenizer().getLineTokenizer())
                .fieldSetMapper(customerFieldSetMapper())
                .build();

    }

    @Bean
    public ValidatingItemProcessor<CustomerUpdate> customerValidatingItemProcessor(CustomerItemValidator validator){
        ValidatingItemProcessor<CustomerUpdate> processor = new ValidatingItemProcessor<>();
        processor.setValidator(validator);
        processor.setFilter(true);
        return processor;

    }
}
