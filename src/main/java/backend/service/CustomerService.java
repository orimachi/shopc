package backend.service;

import backend.payload.request.CustomerRequest;


public interface CustomerService{
    Boolean existsByUsername(String username);

    String createNewCustomer(CustomerRequest registerRequest);
}
