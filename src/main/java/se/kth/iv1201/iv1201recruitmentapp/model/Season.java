package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The season object model as represented by the
 * corresponding database table.
 */
@Entity
@Table(name = "season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id", nullable = false)
    private Integer id;

    @Column(name = "season_start", nullable = false)
    private LocalDate seasonStart;

    @Column(name = "season_end", nullable = false)
    private LocalDate seasonEnd;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "season")
    private Set<Application> applications = new LinkedHashSet<>();

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(LocalDate seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

    public LocalDate getSeasonStart() {
        return seasonStart;
    }

    public void setSeasonStart(LocalDate seasonStart) {
        this.seasonStart = seasonStart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}