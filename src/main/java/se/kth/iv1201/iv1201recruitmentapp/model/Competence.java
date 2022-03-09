package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The competence object model as represented by the
 * corresponding database table.
 */
@Entity
@Table(name = "competence")
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "competence")
    private Set<CompetenceProfile> competenceProfiles = new LinkedHashSet<>();

    public Set<CompetenceProfile> getCompetenceProfiles() {
        return competenceProfiles;
    }

    public void setCompetenceProfiles(Set<CompetenceProfile> competenceProfiles) {
        this.competenceProfiles = competenceProfiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}