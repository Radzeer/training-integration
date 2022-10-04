package training.employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import training.employees.employees.dto.EmployeeDto;
import training.employees.employees.entity.Employee;
import training.employees.employees.repository.EmployeesRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryIT {

    @Autowired
    EmployeesRepository repository;

    @Test
    void testCreateEmployee(){
        var employee = new Employee();
        employee.setName("John Doe");
        repository.save(employee);

        var employeesList=repository.findEmployeeDtosByNameLike("J%");

        assertThat(employeesList)
                .extracting(EmployeeDto::getName)
                .contains("John Doe");
    }
}
