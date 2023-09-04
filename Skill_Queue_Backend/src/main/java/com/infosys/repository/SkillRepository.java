package com.infosys.repository;

import com.infosys.entity.Employee;
import com.infosys.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, String> {
    @Query("SELECT s FROM Skill s WHERE LOWER(s.skillName) = LOWER(:skillName)")
    public Optional<Skill> findBySkillName(@Param("skillName") String skillName);
    @Query("SELECT unit, COUNT(*) FROM Profile GROUP BY unit")
    public Iterable<Object[]> countProfileInUnit();
    @Query("SELECT dc, COUNT(*) FROM Profile GROUP BY dc")
    public Iterable<Object[]> countProfileInDc();
}
