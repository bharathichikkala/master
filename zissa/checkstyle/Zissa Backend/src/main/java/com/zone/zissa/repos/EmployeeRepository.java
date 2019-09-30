package com.zone.zissa.repos;

import com.zone.zissa.model.Employee;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/** The EmployeeRepository Interface for the Employee database table. */
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  /**
   * The findByUserName method.
   *
   * @param userName the user name
   * @return Employee
   */
  Optional<Employee> findByUserName(String userName);
}
