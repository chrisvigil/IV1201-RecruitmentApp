package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.exception.ApplicationNonexistentException;
import se.kth.iv1201.iv1201recruitmentapp.model.*;
import se.kth.iv1201.iv1201recruitmentapp.repository.ApplicationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.AvailabilityRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceLocalizationRepository;
import se.kth.iv1201.iv1201recruitmentapp.repository.CompetenceProfileRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
     * If the application does not exist, null is
     * returned to the caller.
     *
     * @param applicationId The application id.
     * @param locale The locale.
     * @return The application response dto.
     */
    public ApplicationResponseDto getApplicationData(int applicationId, Locale locale) {
        ApplicationResponseDto response = new ApplicationResponseDto();

        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isEmpty()) {
            return null;
        }

        Application application = optionalApplication.get();
        List<Availability> availabilities = availabilityRepository.getAvailabilityByApplicationId(applicationId);
        List<CompetenceProfile> competenceProfiles = competenceProfileRepository.getCompetenceProfileById(applicationId);
        List<CompetenceProfileWrapper> competenceProfileWrapperList = new LinkedList<CompetenceProfileWrapper>();

        for (CompetenceProfile competenceProfile : competenceProfiles) {
            int id = competenceProfile.getCompetence().getId();
            String loc = locale.getLanguage();

            CompetenceProfileWrapper competenceProfileWrapper = new CompetenceProfileWrapper();

            Optional<CompetenceLocalization> competenceLocalization = competenceLocalizationRepository.findByCompetenceIdAndLocale(id, loc);

            String competenceName;
            if (competenceLocalization.isPresent()) {
                competenceName = competenceLocalization.get().getCompetenceName();
            }
            else {
                competenceName = competenceLocalizationRepository.findByCompetenceIdAndLocale(id, environment.getProperty("default.language")).get().getCompetenceName();
            }

            competenceProfileWrapper.setCompetenceName(competenceName);
            competenceProfileWrapper.setCompetenceProfile(competenceProfile);

            competenceProfileWrapperList.add(competenceProfileWrapper);
        }

        StatusWrapper statusWrapper = makeStatusFromString(application.getStatus(), locale);

        response.setApplication(application);
        response.setAvailabilities(availabilities);
        response.setCompetenceProfileWrappers(competenceProfileWrapperList);
        response.setStatusWrapper(statusWrapper);

        return response;
    }

    /**
     * Updates the database with the new application
     * as given by the application request dto and
     * application id.
     *
     * @param applicationId The application id.
     * @param applicationRequestDto the application request dto.
     * @throws ApplicationNonexistentException If the application no longer exists in the database.
     * @return True if successfully updated status in database,
     *         false if the application was changed between reading and saving.
     */
    public boolean updateApplicationStatus(int applicationId, ApplicationRequestDto applicationRequestDto)
            throws ApplicationNonexistentException {

        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isEmpty()) {
            throw new ApplicationNonexistentException("Application is no longer in the database");
        }

        Application application = optionalApplication.get();

        if (application.getVersion() == applicationRequestDto.getOldVersion()) {
            application.setStatus(applicationRequestDto.getStatus());
            applicationRepository.save(application);
            return true;
        }
        else {
            return false;
        }
    }

    private StatusWrapper makeStatusFromString(String statusValue, Locale locale) {
        StatusWrapper statusWrapper = new StatusWrapper();
        statusWrapper.setValue(statusValue);

        Locale def = new Locale(environment.getProperty("default.language"));

        if (statusWrapper.getValue().equals(messageSource.getMessage("recruiter.application.option.accepted", null, def))) {
            statusWrapper.setText(messageSource.getMessage("recruiter.application.option.accepted", null, locale));
        } else if (statusWrapper.getValue().equals(messageSource.getMessage("recruiter.application.option.rejected", null, def))) {
            statusWrapper.setText(messageSource.getMessage("recruiter.application.option.rejected", null, locale));
        } else if (statusWrapper.getValue().equals(messageSource.getMessage("recruiter.application.option.unhandled", null, def))) {
            statusWrapper.setText(messageSource.getMessage("recruiter.application.option.unhandled", null, locale));
        } else {
            statusWrapper.setText(statusValue);
        }

        return statusWrapper;
    }

}
