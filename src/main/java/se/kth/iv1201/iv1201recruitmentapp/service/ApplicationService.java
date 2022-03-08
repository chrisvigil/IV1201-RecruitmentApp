package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationRequestDto;
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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    /**
     * Employs the corresponding database accesses to
     * create and return an application response dto
     * according to the given application id, does
     * so in the language specified by locale.
     *
     * @param applicationId The application id.
     * @param locale The locale.
     * @return The application response dto.
     */
    public ApplicationResponseDto getApplicationData(int applicationId, Locale locale) {
        ApplicationResponseDto response = new ApplicationResponseDto();

        Application application = applicationRepository.getById(applicationId);
        List<Availability> availabilities = availabilityRepository.getAvailabilityByApplicationId(applicationId);
        List<CompetenceProfile> competenceProfiles = competenceProfileRepository.getCompetenceProfileById(applicationId);

        List<CompetenceProfileWrapper> competenceProfileWrappers = new LinkedList<CompetenceProfileWrapper>();

        for (CompetenceProfile competenceProfile : competenceProfiles) {
            int id = competenceProfile.getCompetence().getId();
            String loc = locale.getLanguage();

            CompetenceProfileWrapper competenceProfileWrapper = new CompetenceProfileWrapper();

            // TODO no isPresent for optional, shouldn't be a problem since we know there will be an id and a localization?
            competenceProfileWrapper.setCompetenceName(competenceLocalizationRepository.findByCompetenceIdAndLocale(id, loc).get().getCompetenceName());
            competenceProfileWrapper.setCompetenceProfile(competenceProfile);

            competenceProfileWrappers.add(competenceProfileWrapper);
        }

        Status status = makeStatusFromString(application.getStatus(), locale);

        response.setApplication(application);
        response.setAvailabilities(availabilities);
        response.setCompetenceProfileWrappers(competenceProfileWrappers);
        response.setStatus(status);

        return response;
    }

    /**
     * Updates the database with the new application
     * as given by the application request dto and
     * application id.
     *
     * @param applicationId The application id.
     * @param applicationRequestDto the application request dto.
     */
    public boolean updateApplicationStatus(int applicationId, ApplicationRequestDto applicationRequestDto) {
        Application application = applicationRepository.getById(applicationId);
        application.setStatus(applicationRequestDto.getStatus());
        try {
            applicationRepository.save(application);
            // Thread sleep
            return true;
        }
        catch (OptimisticLockException e) {
            return false;
        }
    }

    private Status makeStatusFromString(String statusValue, Locale locale) {
        Status status = new Status();
        status.setValue(statusValue);

        Locale def = new Locale(environment.getProperty("default.language"));

        if (status.getValue().equals(messageSource.getMessage("recruiter.application.option.accepted", null, def))) {
            status.setText(messageSource.getMessage("recruiter.application.option.accepted", null, locale));
        }
        else if (status.getValue().equals(messageSource.getMessage("recruiter.application.option.rejected", null, def))) {
            status.setText(messageSource.getMessage("recruiter.application.option.rejected", null, locale));
        }
        else if (status.getValue().equals(messageSource.getMessage("recruiter.application.option.unhandled", null, def))) {
            status.setText(messageSource.getMessage("recruiter.application.option.unhandled", null, locale));
        }
        else {
            status.setText(statusValue);
        }

        return status;
    }

}
