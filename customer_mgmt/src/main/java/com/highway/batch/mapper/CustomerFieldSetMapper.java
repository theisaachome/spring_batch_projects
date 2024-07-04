package com.highway.batch.mapper;
import com.highway.batch.customer.model.CustomerAddressUpdate;
import com.highway.batch.customer.model.CustomerContactUpdate;
import com.highway.batch.customer.model.CustomerNameUpdate;
import com.highway.batch.customer.model.CustomerUpdate;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;


public class CustomerFieldSetMapper implements FieldSetMapper<CustomerUpdate> {
    @Override
    public CustomerUpdate mapFieldSet(FieldSet fieldSet) throws BindException {
         switch (fieldSet.readInt("recordId")) {
             case 1: return new CustomerNameUpdate(
                    fieldSet.readLong("customerId"),
                    fieldSet.readString("firstName"),
                    fieldSet.readString("middleName"),
                    fieldSet.readString("lastName"));
            case 2: return new CustomerAddressUpdate(
                    fieldSet.readLong("customerId"),
                    fieldSet.readString("address1"),
                    fieldSet.readString("address2"),
                    fieldSet.readString("city"),
                    fieldSet.readString("state"),
                    fieldSet.readString("postalCode"));
             case 3: {
               String rawPreference = fieldSet.readString("notificationPreference");
                Integer notificationPreference = null;
                if(StringUtils.hasText(rawPreference)){
                    notificationPreference = Integer.parseInt(rawPreference);
                }
                new CustomerContactUpdate(
                        fieldSet.readLong("customerId"),
                        fieldSet.readString("emailAddress"),
                        fieldSet.readString("homePhone"),
                        fieldSet.readString("cellPhone"),
                        fieldSet.readString("workPhone"),
                        notificationPreference);
            }
             default: throw new IllegalArgumentException(
                    "Invalid record type was found:" +
                            fieldSet.readInt("recordId"));
        }
    }
}
