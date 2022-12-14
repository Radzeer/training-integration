package training.employees.employees.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import training.employees.employees.dto.*;
import training.employees.employees.service.EmployeesService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private EmployeesService service;

    @GetMapping
    // @ResponseBody - nem kell kitenni, mert a @RestController minden metódusra
    // automatikusan ráteszi
    public List<EmployeeDto> listEmployees(@RequestParam("prefix") Optional<String> prefix,
            Principal principal) {
        log.info("List employees");
        log.debug("List employees with prefix {}", prefix);
        log.info("Logged user: {}", principal.getName());
        return service.listEmployees(prefix);
    }

    @GetMapping("{id}")
    public EmployeeDetailsDto findEmployeeById(@PathVariable("id") long id) {
        return service.findEmployeeById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new employee", description = "Create a new employee with name and year of birth")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeDetailsDto> createEmployee(@Valid @RequestBody CreateEmployeeCommand command,
                                                             UriComponentsBuilder uri) {
        var employee = service.createEmployee(command);
        return ResponseEntity
                .created(uri.path("/api/employees/{id}").buildAndExpand(employee.getId()).toUri())
                .body(employee);
    }

    @PutMapping("{id}")
    public EmployeeDetailsDto updateEmployee(@PathVariable("id") long id,
                                             @RequestBody UpdateEmployeeCommand command) {
        return service.updateEmployee(id, command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id) {
        service.deleteEmployee(id);
    }

    @PostMapping("{employeeId}/addresses")
    public ResponseEntity createAddress(@PathVariable("employeeId") long employeeId, @Valid @RequestBody CreateAddressCommand command,
                                        UriComponentsBuilder uri){
        var address= service.createAddress(employeeId,command);
        return ResponseEntity
                .created(uri.path("/api/employees/{employeeId}/addresses/{addressId}").buildAndExpand(employeeId,address.getId()).toUri())
                .body(address);
    }

    @GetMapping("{employeeId}/addresses")
    // @ResponseBody - nem kell kitenni, mert a @RestController minden metódusra
    // automatikusan ráteszi
    public List<AddressDto> listAddresses(@PathVariable("employeeId") long employeeId) {
        return service.listAdresses(employeeId);
    }
}
