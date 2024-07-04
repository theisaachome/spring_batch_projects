package com.highway.batch.validator;

import com.highway.batch.customer.model.CustomerUpdate;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerItemValidator implements Validator<CustomerUpdate> {

    protected static final String FIND_CUSTOMER="SELECT * FROM CUSTOMER WHERE id:id";
    // find customer from database by id
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerItemValidator(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void validate(CustomerUpdate value) throws ValidationException {
        Map<String, Object> params = Collections.singletonMap("id",value.getCustomerId());
        Long count = jdbcTemplate.queryForObject(FIND_CUSTOMER,params,Long.class);
        if(count!=null && count==0){
            throw new ValidationException(String.format("Customer with id %s not found",value.getCustomerId()));
        }
    }
}
