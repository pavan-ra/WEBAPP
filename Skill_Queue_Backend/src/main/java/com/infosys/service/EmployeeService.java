package com.infosys.service;

import com.infosys.dto.EmployeeDTO;
import com.infosys.dto.EmployeeWrapper;
import com.infosys.dto.ProfileDTO;
import com.infosys.dto.SkillDTO;
import com.infosys.exception.InfyEmployeeException;

import java.util.List;

public interface EmployeeService {
    public String sendEmail(String to) throws Exception;
    List<SkillDTO> getSkills() throws InfyEmployeeException;
    void addSkill(SkillDTO skillDTO) throws InfyEmployeeException;
    public void signup(EmployeeDTO employeeDTO) throws InfyEmployeeException;
    public void signupAll(List<EmployeeDTO> employeeDTOList) throws InfyEmployeeException;
    public void addEmployeeProfileAll(Integer employeeId, List<ProfileDTO> profileDTOList)
        throws InfyEmployeeException;
    public void addEmployeeProfile(Integer employeeId, ProfileDTO profileDTO) throws InfyEmployeeException;
    public void updateEmployeeProfile(Integer employeeId, ProfileDTO profileDTO) throws InfyEmployeeException;
    public EmployeeDTO getEmployeeByEmployeeId(Integer employeeId) throws InfyEmployeeException;
    public List<EmployeeWrapper> getAllEmployeesByPrimarySkill(String primarySkill)
            throws InfyEmployeeException;
    public List<EmployeeWrapper> getAllEmployeesByUnit(String unit)
            throws InfyEmployeeException;

    public ProfileDTO getProfileByEmployeeId(Integer employeeId) throws InfyEmployeeException;
    public List<EmployeeDTO> getAllEmployees() throws InfyEmployeeException;
    public List<Object[]> countProfileInUnit() throws InfyEmployeeException;
    public List<Object[]> countProfileInDc() throws InfyEmployeeException;
    public void resetPassword(String emailId, String password) throws InfyEmployeeException;

}

