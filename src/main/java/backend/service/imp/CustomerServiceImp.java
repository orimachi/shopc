package backend.service.imp;

import backend.mapper.CustomerMapper;
import backend.entity.Customer;
import backend.entity.Role;
import backend.enums.EMessage;
import backend.enums.ERole;
import backend.payload.request.CustomerRequest;
import backend.repository.CustomerRepository;
import backend.repository.RoleRepository;
import backend.service.CustomerService;
import backend.utils.app.MethodUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomerMapper customerMapper;

    private final RoleRepository roleRepository;

    @Override
    public Boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    @Override
    public String createNewCustomer(CustomerRequest registerRequest) {
        if (Boolean.TRUE.equals(customerRepository.existsByUsername(registerRequest.getUsername()))) {
            return MethodUtils.formatMessage(EMessage.USERNAME_ALREADY_EXIST,registerRequest.getUsername());
        }

        Customer newCustomer = customerMapper.toCustomer(registerRequest);

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(registerRequest.getRole() == null) {
            Role userRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException(MethodUtils.formatMessage(EMessage.ROLE_MESSAGE_NOT_FOUND,ERole.CUSTOMER)));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin": Role adminRole = roleRepository.findByName(ERole.ADMIN)
                            .orElseThrow(() -> new RuntimeException(MethodUtils.formatMessage(EMessage.ROLE_MESSAGE_NOT_FOUND,ERole.ADMIN)));
                        roles.add(adminRole);
                        break;
                    case "manager":
                        Role modRole = roleRepository.findByName(ERole.MANAGER)
                                .orElseThrow(() -> new RuntimeException(MethodUtils.formatMessage(EMessage.ROLE_MESSAGE_NOT_FOUND,ERole.MANAGER)));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.CUSTOMER)
                                .orElseThrow(() -> new RuntimeException(MethodUtils.formatMessage(EMessage.ROLE_MESSAGE_NOT_FOUND,ERole.CUSTOMER)));
                        roles.add(userRole);
                }
            });
        }

        newCustomer.setRoles(roles);
        newCustomer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        customerRepository.save(newCustomer);

        return "User registered successfully!";
    }


}
