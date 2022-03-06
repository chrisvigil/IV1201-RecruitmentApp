package se.kth.iv1201.iv1201recruitmentapp.model;

/**
 * Wrapper class to solve the issue of coupling
 * the correct localized name to a certain
 * competence id in a competence profile.
 */
public class CompetenceProfileWrapper {
    private CompetenceProfile competenceProfile;
    private String competenceName;

    public CompetenceProfile getCompetenceProfile() {
        return competenceProfile;
    }

    public void setCompetenceProfile(CompetenceProfile competenceProfile) {
        this.competenceProfile = competenceProfile;
    }

    public String getCompetenceName() {
        return competenceName;
    }

    public void setCompetenceName(String competenceName) {
        this.competenceName = competenceName;
    }

}
