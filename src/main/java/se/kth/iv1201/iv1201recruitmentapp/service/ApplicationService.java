package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.*;
import se.kth.iv1201.iv1201recruitmentapp.repository.ApplicationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.AvailabilityRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceLocalizationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceProfileRepository;

import javax.persistence.OptimisticLockException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Service for retrieving application information.
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepository;

    @Autowired
    private CompetenceLocalizationRepository competenceLocalizationRepository;

    /**
     * Employs the corresponding database accesses to
     * create and return an application response dto
     * according to the given application id.
     *
     * @param applicationId The application id.
     * @return The application response dto.
     */
    public ApplicationResponseDto getApplicationData(int applicationId, Locale locale) {
        ApplicationResponseDto response = new ApplicationResponseDto();

        Application application = applicationRepository.getById(applicationId);
        List<Availability> availabilities = availabilityRepository.getAvailabilityByApplicationId(applicationId);
        List<CompetenceProfile> competenceProfiles = competenceProfileRepository.getCompetenceProfileById(applicationId);

        List<CompetenceProfileWrapper> competenceProfileWrappers = new LinkedList<CompetenceProfileWrapper>();

        for (CompetenceProfile competenceProfile : competenceProfiles) {
            CompetenceProfileWrapper competenceProfileWrapper = new CompetenceProfileWrapper();

            int id = competenceProfile.getCompetence().getId();
            String loc = locale.getLanguage();

            // TODO no isPresent for optional, shouldn't be a problem since we know there will be an id and a localization?
            competenceProfileWrapper.setCompetenceName(competenceLocalizationRepository.findByCompetenceIdAndLocale(id, loc).get().getCompetenceName());
            competenceProfileWrapper.setCompetenceProfile(competenceProfile);

            competenceProfileWrappers.add(competenceProfileWrapper);
        }

        response.setApplication(application);
        response.setAvailabilities(availabilities);
        response.setCompetenceProfileWrappers(competenceProfileWrappers);
        response.setStatusId(parseStatusIdFromName(application.getStatus()));

        return response;
    }

    /**
     * Updates the database with the new application
     * status as given.
     *
     * @param status The status.
     */
    public boolean updateApplicationStatus(int applicationId, String status) {
        Application application = applicationRepository.getById(applicationId);
        application.setStatus(status);
        try {
            applicationRepository.save(application);
            // Thread sleep
            return true;
        }
        catch (OptimisticLockException e) {
            return false;
        }
    }

    private String parseStatusIdFromName(String statusName) {
        if (statusName.equals("accepted")) return "1";
        if (statusName.equals("rejected")) return "2";
        return "3";
    }

}
