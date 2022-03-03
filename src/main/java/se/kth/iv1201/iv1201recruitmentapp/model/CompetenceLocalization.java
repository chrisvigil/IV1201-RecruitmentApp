package se.kth.iv1201.iv1201recruitmentapp.model;

import javax.persistence.*;

/**
 * The competence localization object model as represented
 * by the corresponding database table.
 */
@Entity
@Table(name = "competence_localization")
public class CompetenceLocalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_localization_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;

    @Column(name = "locale", nullable = false, length = 2)
    private String locale;

    @Column(name = "competence_name", nullable = false)
    private String competenceName;

    public String getCompetenceName() {
        return competenceName;
    }

    public void setCompetenceName(String competenceName) {
        this.competenceName = competenceName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}