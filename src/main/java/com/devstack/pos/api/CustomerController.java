package com.devstack.pos.api;

import com.devstack.pos.dto.request.RequestCustomerDto;
import com.devstack.pos.service.CustomerService;
import com.devstack.pos.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/customers") //consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //postman body
    /*{
    "name": "sudesh sudeera",
    "address": "Wellwaya",
    "salary": 100000
    }*/

    @PostMapping // to save anything with data
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> createCustomer(@RequestBody RequestCustomerDto customerDto){
        return new ResponseEntity<>(
                new StandardResponse(201,"customer saved!",customerService.createCustomer(customerDto)),
                HttpStatus.CREATED
        );
    }
    @PutMapping(params = "id") // to update anything, disable
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<StandardResponse> updateCustomer(
            @RequestParam int id,
            @RequestBody RequestCustomerDto customerDto
    ) throws ClassNotFoundException {

        return new ResponseEntity<>(
                new StandardResponse(201,"customer updated!",customerService.updateCustomer(customerDto, id)),
                HttpStatus.CREATED
        );
    }
    @DeleteMapping(params = "id") // to delete anything
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> deleteCustomer(
            @RequestParam int id
    ) throws ClassNotFoundException {

        customerService.deleteCustomer(id);
        return new ResponseEntity<>(
                new StandardResponse(204,"customer deleted!",null),
                HttpStatus.NO_CONTENT
        );
    }
    @GetMapping("/{id}") //to request data, get data // localhost:8001/api/v1/customers/12345 (Query params = localhost:8001/api/v1/customers?id=12345)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MANAGER')")
    public ResponseEntity<StandardResponse> findCustomer(@PathVariable long id) throws ClassNotFoundException {
        return new ResponseEntity<>(
                new StandardResponse(200,"customer data!",customerService.findCustomer(id)),
                HttpStatus.OK
        );
    }
    @GetMapping(value = "/list", params = {"page","size","searchText"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MANAGER')")
    public ResponseEntity<StandardResponse> getAllCustomers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String searchText
    ){
        return new ResponseEntity<>(
                new StandardResponse(200,"customer list!",
                        customerService.searchAllCustomers(page,size,searchText)),
                HttpStatus.OK
        );
    }
}
