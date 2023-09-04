package com.infosys.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    private String skillName;
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillName='" + skillName + '\'' +
                '}';
    }
}
