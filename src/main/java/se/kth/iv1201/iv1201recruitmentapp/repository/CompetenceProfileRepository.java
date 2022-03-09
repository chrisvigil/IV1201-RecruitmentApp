package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.iv1201.iv1201recruitmentapp.model.CompetenceProfile;

import java.util.List;

/**
 * Defines database access for the competence profiles.
 */
public interface CompetenceProfileRepository extends JpaRepository<CompetenceProfile, Integer> {

    /**
     * Gets all the competence profiles associated
     * with the specified application id.
     *
     * @param applicationId The application id.
     * @return The competence profiles.
     */
    @Query(value = "SELECT cp.competence_profile_id, cp.person_id, cp.competence_id, cp.years_of_experience FROM application AS app INNER JOIN competence_profile AS cp ON app.person_id=cp.person_id WHERE app.application_id=?1",
            nativeQuery = true)
    List<CompetenceProfile> getCompetenceProfileById(int applicationId);

}
