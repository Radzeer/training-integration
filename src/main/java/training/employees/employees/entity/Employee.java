package training.employees.employees.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_name")
    private String name;

    private int yearOfBirth;

    //Relation field
    @OneToMany(mappedBy = "employee")
    private List<Address> addresses;

    public void addAddress(Address address){
        if (addresses == null){
            addresses = new ArrayList();
        }
        addresses.add(address);
        address.setEmployee(this);
    }
}
