package backend.mapper;

import backend.entity.Customer;
import backend.payload.request.CustomerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerRequest request);

}
