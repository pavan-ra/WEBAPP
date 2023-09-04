package com.infosys.service;

import com.infosys.dto.*;
import com.infosys.entity.Employee;
import com.infosys.entity.Profile;
import com.infosys.entity.Skill;
import com.infosys.exception.InfyEmployeeException;
import com.infosys.repository.EmployeeRepository;
import com.infosys.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    EmailService emailService;

    // method to send an otp if the email is valid
    @Override
    public String sendEmail(String to) throws Exception {
        Optional<Employee> optional = employeeRepository.findByEmailId(to);
        if (optional.isEmpty())
            throw new InfyEmployeeException("Service.INVALID_EMAIL");

        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        String otp = Integer.toString(randomNumber);

        String subject = "Reset Your Password - [SkillQueue]";
        emailService.sendOtp(to, subject, otp);

        return otp;
    }

    // method to update password after successful otp validation
    @Override
    public void resetPassword(String emailId, String password) throws InfyEmployeeException {
        Employee employee = employeeRepository.findByEmailId(emailId).get();
        employee.setPassword(passwordEncoder.encode(password));
        employeeRepository.save(employee);
    }

    // method to get the list of skills
    @Override
    public List<SkillDTO> getSkills() throws InfyEmployeeException {
        Iterable<Skill> iterable = skillRepository.findAll();
        List<SkillDTO> skillDTOList = new ArrayList<>();
        for (Skill skill : iterable) {
            SkillDTO skillDTO = new SkillDTO();
            skillDTO.setSkillName(skill.getSkillName());
            skillDTOList.add(skillDTO);
        }
        return skillDTOList;
    }

    // method to add a skill
    @Override
    public void addSkill(SkillDTO skillDTO) throws InfyEmployeeException {
        Optional<Skill> optional = skillRepository.findBySkillName(skillDTO.getSkillName());
        if (optional.isEmpty()) {
            Skill skill = new Skill();
            skill.setSkillName(skillDTO.getSkillName());
            skillRepository.save(skill);
        }
    }

    // method is provided by UserDetailsService interface to fetch username password from database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findById(Integer.parseInt(username)).get();
        UserDetails user = User.builder().username(employee.getEmailId()).password(employee.getPassword()).build();
        return user;
    }

    // method to register employee
    @Override
    public void signup(EmployeeDTO employeeDTO) throws InfyEmployeeException {
        Optional<Employee> optional = employeeRepository.findById(employeeDTO.getEmployeeId());
        if (optional.isPresent())
            throw new InfyEmployeeException("Service.EMPLOYEE_ALREADY_EXISTS");
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setEmailId(employeeDTO.getEmailId());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setProfile(null);
        employeeRepository.save(employee);

    }

    // method to add employee profile
    @Override
    public void addEmployeeProfile(Integer employeeId, ProfileDTO profileDTO) throws InfyEmployeeException {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        Employee employee = optional.get();
        if (employee.getProfile() != null)
            throw new InfyEmployeeException("Service.PROFILE_ALREADY_EXISTS");

        Profile profile = new Profile();
        profile.setDc(profileDTO.getDc());
        profile.setUnit(profileDTO.getUnit());
        profile.setSkills(profileDTO.getSkills());
        profile.setPrimarySkill1(profileDTO.getPrimarySkill1());
        profile.setPrimarySkill2(profileDTO.getPrimarySkill2());
        profile.setPrimarySkill3(profileDTO.getPrimarySkill3());
        profile.setCertifications(profileDTO.getCertifications());
        profile.setBenchPeriod(profileDTO.getBenchPeriod());
        profile.setExperienceInMonths(profileDTO.getExperienceInMonths());
        profile.setProfileLink(profileDTO.getProfileLink());

        employee.setProfile(profile);
        employeeRepository.save(employee);
    }

    //method to update employee profile
    @Override
    public void updateEmployeeProfile(Integer employeeId, ProfileDTO profileDTO) throws InfyEmployeeException {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        Employee employee = optional.get();

        Profile profile = employee.getProfile();
        profile.setDc(profileDTO.getDc());
        profile.setUnit(profileDTO.getUnit());
        profile.setSkills(profileDTO.getSkills());
        profile.setPrimarySkill1(profileDTO.getPrimarySkill1());
        profile.setPrimarySkill2(profileDTO.getPrimarySkill2());
        profile.setPrimarySkill3(profileDTO.getPrimarySkill3());
        profile.setCertifications(profileDTO.getCertifications());
        profile.setBenchPeriod(profileDTO.getBenchPeriod());
        profile.setExperienceInMonths(profileDTO.getExperienceInMonths());
        profile.setProfileLink(profileDTO.getProfileLink());
        employee.setProfile(profile);
        employeeRepository.save(employee);

    }

    // method to get employee by employee id
    @Override
    public EmployeeDTO getEmployeeByEmployeeId(Integer employeeId) throws InfyEmployeeException {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        Employee employee = optional.orElseThrow(() -> new InfyEmployeeException("Service.EMPLOYEE_NOT_FOUND"));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setEmailId(employee.getEmailId());
        employeeDTO.setPassword(null);

        if (employee.getProfile() == null) {
            employeeDTO.setProfileDTO(null);
        } else {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setProfileId(employee.getProfile().getProfileId());
            profileDTO.setDc(employee.getProfile().getDc());
            profileDTO.setUnit(employee.getProfile().getUnit());
            profileDTO.setSkills(employee.getProfile().getSkills());
            profileDTO.setPrimarySkill1(employee.getProfile().getPrimarySkill1());
            profileDTO.setPrimarySkill2(employee.getProfile().getPrimarySkill2());
            profileDTO.setPrimarySkill3(employee.getProfile().getPrimarySkill3());
            profileDTO.setCertifications(employee.getProfile().getCertifications());
            profileDTO.setBenchPeriod(employee.getProfile().getBenchPeriod());
            profileDTO.setExperienceInMonths(employee.getProfile().getExperienceInMonths());
            profileDTO.setProfileLink(employee.getProfile().getProfileLink());
            employeeDTO.setProfileDTO(profileDTO);
        }
        return employeeDTO;
    }

    // get employees for a particular primary skill
    @Override
    public List<EmployeeWrapper> getAllEmployeesByPrimarySkill(String primarySkill)
            throws InfyEmployeeException {
        List<Object[]> iterable = employeeRepository.findAllByProfilePrimarySkill(primarySkill);
        List<EmployeeWrapper> employeeWrapperList = new ArrayList<>();
        for (Object[] object : iterable) {
            EmployeeWrapper employeeWrapper = new EmployeeWrapper();
            employeeWrapper.setEmployeeId((Integer) object[0]);
            employeeWrapper.setEmployeeName((String) object[1]);
            employeeWrapper.setUnit((String) object[2]);
            employeeWrapper.setBenchPeriod((Integer) object[3]);
            employeeWrapper.setExperienceInMonths((Integer) object[4]);
            employeeWrapperList.add(employeeWrapper);
        }
        return employeeWrapperList;
    }

    // get employees for a particular unit
    @Override
    public List<EmployeeWrapper> getAllEmployeesByUnit(String unit)
            throws InfyEmployeeException {
        List<Object[]> list = employeeRepository.findAllByProfileUnit(unit);
        List<EmployeeWrapper> employeeWrapperList = new ArrayList<>();
        for (Object[] object : list) {
            EmployeeWrapper employeeWrapper = new EmployeeWrapper();
            employeeWrapper.setEmployeeId((Integer) object[0]);
            employeeWrapper.setEmployeeName((String) object[1]);
            employeeWrapper.setUnit((String) object[2]);
            employeeWrapper.setBenchPeriod((Integer) object[3]);
            employeeWrapper.setExperienceInMonths((Integer) object[4]);
            employeeWrapperList.add(employeeWrapper);
        }
        return employeeWrapperList;
    }

    // method to get profile by employee id
    @Override
    public ProfileDTO getProfileByEmployeeId(Integer employeeId) throws InfyEmployeeException {
        Optional<Profile> optional = employeeRepository.findProfileByEmployeeId(employeeId);
        Profile profile = optional.orElseThrow(() -> new InfyEmployeeException("Service.PROFILE_NOT_FOUND"));
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileId(profile.getProfileId());
        profileDTO.setDc(profile.getDc());
        profileDTO.setUnit(profile.getUnit());
        profileDTO.setSkills(profile.getSkills());
        profileDTO.setPrimarySkill1(profile.getPrimarySkill1());
        profileDTO.setPrimarySkill2(profile.getPrimarySkill2());
        profileDTO.setPrimarySkill3(profile.getPrimarySkill3());
        profileDTO.setCertifications(profile.getCertifications());
        profileDTO.setBenchPeriod(profile.getBenchPeriod());
        profileDTO.setExperienceInMonths(profile.getExperienceInMonths());
        profileDTO.setProfileLink(profile.getProfileLink());
        return profileDTO;
    }

    // method to get all the registered employees with profile data also
    @Override
    public List<EmployeeDTO> getAllEmployees() throws InfyEmployeeException {
        Iterable<Employee> iterable = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee: iterable) {
            if (employee.getProfile() != null) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setEmployeeId(employee.getEmployeeId());
                employeeDTO.setEmployeeName(employee.getEmployeeName());
                employeeDTO.setEmailId(employee.getEmailId());
                employeeDTO.setPassword(null);

                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setProfileId(employee.getProfile().getProfileId());
                profileDTO.setUnit(employee.getProfile().getUnit());
                profileDTO.setSkills(employee.getProfile().getSkills());
                profileDTO.setPrimarySkill1(employee.getProfile().getPrimarySkill1());
                profileDTO.setPrimarySkill2(employee.getProfile().getPrimarySkill2());
                profileDTO.setPrimarySkill3(employee.getProfile().getPrimarySkill3());
                profileDTO.setCertifications(employee.getProfile().getCertifications());
                profileDTO.setBenchPeriod(employee.getProfile().getBenchPeriod());
                profileDTO.setExperienceInMonths(employee.getProfile().getExperienceInMonths());
                profileDTO.setProfileLink(employee.getProfile().getProfileLink());
                employeeDTO.setProfileDTO(profileDTO);
                employeeDTOList.add(employeeDTO);
            }
        }
        return employeeDTOList;
    }

    // method to count number of employees in each unit
    @Override
    public List<Object[]> countProfileInUnit() throws InfyEmployeeException {
        Iterable<Object[]> iterable = skillRepository.countProfileInUnit();
        List<Object[]> objects = new ArrayList<>();
        for (Object[] object : iterable) {
            objects.add(object);
        }
        return objects;
    }

    // method to count number of employees in each dc
    @Override
    public List<Object[]> countProfileInDc() throws InfyEmployeeException {
        Iterable<Object[]> iterable = skillRepository.countProfileInDc();
        List<Object[]> objects = new ArrayList<>();
        for (Object[] object : iterable) {
            objects.add(object);
        }
        return objects;
    }

    // method created only to register multiple employee, delete before deployment phase
    @Override
    public void signupAll(List<EmployeeDTO> employeeDTOList) throws InfyEmployeeException {

        for (EmployeeDTO employeeDTO : employeeDTOList) {
            Employee employee = new Employee();
            employee.setEmployeeId(employeeDTO.getEmployeeId());
            employee.setEmployeeName(employeeDTO.getEmployeeName());
            employee.setEmailId(employeeDTO.getEmailId());
            employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
            employee.setProfile(null);
            employeeRepository.save(employee);
        }

    }

    // method created only to add multiple employees profile, delete before deployment phase
    @Override
    public void addEmployeeProfileAll(Integer employeeId, List<ProfileDTO> profileDTOList) throws InfyEmployeeException {
        Integer id = employeeId;

        for (ProfileDTO profileDTO : profileDTOList) {
            Employee employee = employeeRepository.findById(id).get();
            Profile profile = new Profile();
            profile.setDc(profileDTO.getDc());
            profile.setUnit(profileDTO.getUnit());
            profile.setSkills(profileDTO.getSkills());
            profile.setPrimarySkill1(profileDTO.getPrimarySkill1());
            profile.setPrimarySkill2(profileDTO.getPrimarySkill2());
            profile.setPrimarySkill3(profileDTO.getPrimarySkill3());
            profile.setCertifications(profileDTO.getCertifications());
            profile.setBenchPeriod(profileDTO.getBenchPeriod());
            profile.setExperienceInMonths(profileDTO.getExperienceInMonths());
            profile.setProfileLink(profileDTO.getProfileLink());

            employee.setProfile(profile);
            employeeRepository.save(employee);
            id = id + 1;
        }
    }

}