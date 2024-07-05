package com.highway.batch.config;

import com.highway.batch.customer.model.CustomerUpdate;
import com.highway.batch.mapper.CustomerFieldSetMapper;
import com.highway.batch.mapper.CustomerLineTokenizer;
import com.highway.batch.validator.CustomerItemValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

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

    @Bean
    public JdbcBatchItemWriter<CustomerUpdate> customerNameUpdateItemWriter(DataSource dataSource){
        String sql = """
                UPDATE CUSTOMER
                SET FIRST_NAME = COALESCE(:firstName, FIRST_NAME),
                MIDDLE_NAME = COALESCE(:middleName, MIDDLE_NAME),
                LAST_NAME = COALESCE(:lastName, LAST_NAME) 
                WHERE CUSTOMER_ID = :customerId
                """;
       return  new JdbcBatchItemWriterBuilder<CustomerUpdate>()
               .beanMapped()
               .sql(sql)
               .dataSource(dataSource)
               .build();

    }
    @Bean
    public JdbcBatchItemWriter<CustomerUpdate> customerAddressUpdateItemWriter(DataSource dataSource){
        String sql= """
                UPDATE CUSTOMER SET
                ADDRESS1 = COALESCE(:address1, ADDRESS1),
                ADDRESS2 = COALESCE(:address2, ADDRESS2),
                CITY = COALESCE(:city, CITY),
                STATE = COALESCE(:state, STATE),
                POSTAL_CODE = COALESCE(:postalCode, POSTAL_CODE)
                WHERE CUSTOMER_ID = :customerId
                """;
        return  new JdbcBatchItemWriterBuilder<CustomerUpdate>()
                .dataSource(dataSource)
                .sql(sql)
                .beanMapped()
                .build();
    }
    @Bean
    public JdbcBatchItemWriter<CustomerUpdate> customerContactUpdateItemWriter(DataSource dataSource){
        String sql = """
                UPDATE CUSTOMER SET
                EMAIL_ADDRESS = COALESCE(:emailAddress, EMAIL_ADDRESS),
                HOME_PHONE = COALESCE(:homePhone, HOME_PHONE),
                CELL_PHONE = COALESCE(:cellPhone, CELL_PHONE),
                WORK_PHONE = COALESCE(:workPhone, WORK_PHONE),
                NOTIFICATION_PREF = COALESCE(:notificationPreferences,NOTIFICATION_PREF)
                WHERE CUSTOMER_ID = :customerId
                """;
        return new JdbcBatchItemWriterBuilder<CustomerUpdate>()
                .beanMapped()
                .dataSource(dataSource)
                .sql(sql)
                .build();
    }
}
