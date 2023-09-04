package com.infosys.controller;

import com.infosys.dto.EmployeeDTO;
import com.infosys.dto.EmployeeWrapper;
import com.infosys.dto.ProfileDTO;
import com.infosys.dto.SkillDTO;
import com.infosys.exception.InfyEmployeeException;
import com.infosys.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    Environment environment;

    // endpoint to send otp over email if the email is valid
    @GetMapping("/forgotPassword/{emailId}")
    public ResponseEntity<String> forgotPassword(@PathVariable String emailId) throws Exception {
        String otp = employeeService.sendEmail(emailId);
        return new ResponseEntity<>(otp, HttpStatus.OK);
    }

    //endpoint to reset Password after successful validation of otp
    @PutMapping("/resetPassword/{emailId}")
    public ResponseEntity<String> resetPassword(@PathVariable String emailId, @RequestBody String password)
            throws InfyEmployeeException {
        employeeService.resetPassword(emailId, password);
        String message = environment.getProperty("API.PASSWORD_UPDATE_SUCCESS");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // endpoint to fetch the list of skills
    @GetMapping("/skills")
    public ResponseEntity<List<SkillDTO>> getSkills() throws InfyEmployeeException {
        List<SkillDTO> skillDTOList = employeeService.getSkills();
        return new ResponseEntity<>(skillDTOList, HttpStatus.OK);
    }

    // endpoint to add a particular skill
    @PostMapping("/addSkill")
    public ResponseEntity<String> addSkill(@RequestBody SkillDTO skillDTO) throws InfyEmployeeException {
        employeeService.addSkill(skillDTO);
        String message = environment.getProperty("API.SKILL_INSERT_SUCCESS");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // authentication endpoint
    @GetMapping("/auth/{employeeId}")
    public ResponseEntity<EmployeeDTO> login(@PathVariable Integer employeeId)
            throws InfyEmployeeException {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByEmployeeId(employeeId);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    // Employee registration endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody EmployeeDTO employeeDTO)
            throws InfyEmployeeException {
        employeeService.signup(employeeDTO);
        String message = environment.getProperty("API.EMPLOYEE_INSERT_SUCCESS");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Add employee profile endpoint
    @PostMapping("/cp/{employeeId}")
    public ResponseEntity<String> addEmployeeProfile(@PathVariable Integer employeeId, @RequestBody ProfileDTO profileDTO)
            throws InfyEmployeeException {
        employeeService.addEmployeeProfile(employeeId, profileDTO);
        String message = environment.getProperty("API.PROFILE_INSERT_SUCCESS");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Update employee profile endpoint
    @PutMapping("/up/{employeeId}")
    public ResponseEntity<String> updateEmployeeProfile(@PathVariable Integer employeeId, @RequestBody ProfileDTO profileDTO)
            throws InfyEmployeeException {
        employeeService.updateEmployeeProfile(employeeId, profileDTO);
        String message = environment.getProperty("API.PROFILE_UPDATE_SUCCESS");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // to fetch employee based on employee id endpoint
    @GetMapping("/id/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmployeeId(@PathVariable Integer employeeId)
            throws InfyEmployeeException {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByEmployeeId(employeeId);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    // to fetch employees list based on primary skill endpoint
    @GetMapping("/skill/{primarySkill}")
    public ResponseEntity<List<EmployeeWrapper>> getAllEmployeesByPrimarySkill(@PathVariable String primarySkill)
            throws InfyEmployeeException {
        List<EmployeeWrapper> employeeWrapperList = employeeService.getAllEmployeesByPrimarySkill(primarySkill);
        return new ResponseEntity<>(employeeWrapperList, HttpStatus.OK);
    }

    // endpoint to fetch employees based on particular unit
    @GetMapping("/unit/{unit}")
    public ResponseEntity<List<EmployeeWrapper>> getAllEmployeesByUnit(@PathVariable String unit)
            throws InfyEmployeeException {
        List<EmployeeWrapper> employeeWrapperList = employeeService.getAllEmployeesByUnit(unit);
        return new ResponseEntity<>(employeeWrapperList, HttpStatus.OK);
    }

    // endpoint to get Profile of an Employee by employee id
    @GetMapping("/gp/{employeeId}")
    public ResponseEntity<ProfileDTO> getProfileByEmployeeId(@PathVariable Integer employeeId)
            throws InfyEmployeeException {
        ProfileDTO profileDTO = employeeService.getProfileByEmployeeId(employeeId);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    // endpoint to get all the registered employees, whoever have profile
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() throws InfyEmployeeException {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    // endpoint to get number of employees in each unit
    @GetMapping("/countByUnit")
    public ResponseEntity<List<Object[]>> countProfileInUnit() throws InfyEmployeeException {
        List<Object[]> objects = employeeService.countProfileInUnit();
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    // endpoint to get number of employees in each dc
    @GetMapping("/countByDc")
    public ResponseEntity<List<Object[]>> countProfileInDc() throws InfyEmployeeException {
        List<Object[]> objects = employeeService.countProfileInDc();
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    // endpoint to add Employees in bulk, only for testing purpose of application
    // should be removed before deployment
    @PostMapping("/addAllEmployee")
    public ResponseEntity<String> addAllEmployee(@RequestBody List<EmployeeDTO> employeeDTOList)
        throws InfyEmployeeException {
        employeeService.signupAll(employeeDTOList);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    // endpoint to add Employees profile in bulk, only for testing purpose of application
    // should be removed before deployment
    @PostMapping("/addAllProfile/{employeeId}")
    public ResponseEntity<String> addAllProfile(@PathVariable Integer employeeId,
                                                @RequestBody List<ProfileDTO> profileDTOList) throws InfyEmployeeException {
        employeeService.addEmployeeProfileAll(employeeId, profileDTOList);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

}
