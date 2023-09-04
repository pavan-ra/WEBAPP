package com.infosys.repository;

import com.infosys.entity.Employee;
import com.infosys.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e.employeeId, e.employeeName, p.unit, p.benchPeriod, p.experienceInMonths " +
            "FROM Employee e JOIN e.profile p WHERE LOWER(p.primarySkill1) = LOWER(:primarySkill) " +
            "OR LOWER(p.primarySkill2) = LOWER(:primarySkill) OR LOWER(p.primarySkill3) = LOWER(:primarySkill)")
    public List<Object[]> findAllByProfilePrimarySkill(@Param("primarySkill") String primarySkill);
    @Query("SELECT e.employeeId, e.employeeName, p.unit, p.benchPeriod, p.experienceInMonths " +
            "FROM Employee e JOIN e.profile p WHERE LOWER(p.unit) = LOWER(:unit)")
    public List<Object[]> findAllByProfileUnit(@Param("unit") String unit);
    @Query("SELECT p FROM Employee e JOIN e.profile p WHERE e.employeeId = :employeeId")
    public Optional<Profile> findProfileByEmployeeId(@Param("employeeId") Integer employeeId);
    @Query("SELECT e FROM Employee e WHERE LOWER(e.emailId) = LOWER(:emailId)")
    public Optional<Employee> findByEmailId(@Param("emailId") String emailId);

}
