package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

import se.kth.iv1201.iv1201recruitmentapp.model.Application;
import se.kth.iv1201.iv1201recruitmentapp.model.Availability;
import se.kth.iv1201.iv1201recruitmentapp.model.CompetenceProfileWrapper;

import java.util.List;

/**
 * The application dto object containing all relevant
 * information for a single application.
 */
public class ApplicationResponseDto {
    private Application application;
    private List<Availability> availabilities;
    private List<CompetenceProfileWrapper> competenceProfileWrappers;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<CompetenceProfileWrapper> getCompetenceProfileWrappers() {
        return competenceProfileWrappers;
    }

    public void setCompetenceProfileWrappers(List<CompetenceProfileWrapper> competenceProfileWrappers) {
        this.competenceProfileWrappers = competenceProfileWrappers;
    }

}
