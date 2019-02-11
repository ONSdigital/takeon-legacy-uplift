package uk.gov.ons.collection.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.gov.ons.collection.entity.Employee;

// This is an Interface.
// No need Annotation here.
public interface EmployeeRepository extends CrudRepository<Employee, Long> { // Long: Type of Employee ID.

    Employee findByEmpNo(String empNo);

    List<Employee> findByFullNameLike(String fullName);

    List<Employee> findByHireDateGreaterThan(Date hireDate);

    @Query("SELECT coalesce(max(e.id), 0) FROM Employee e")
    Long getMaxId();

}