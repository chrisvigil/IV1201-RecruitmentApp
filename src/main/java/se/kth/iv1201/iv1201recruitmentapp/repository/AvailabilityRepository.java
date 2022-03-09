package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.iv1201.iv1201recruitmentapp.model.Availability;

import java.util.List;

/**
 * Defines database access for the availabilities.
 */
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {

    /**
     * Gets all the availabilities associated with
     * the specified application id.
     *
     * @param applicationId The application id.
     * @return The availabilities.
     */
    @Query(value = "SELECT av.availability_id, av.person_id, av.from_date, av.to_date FROM application AS app INNER JOIN season AS s ON app.season_id=s.season_id RIGHT OUTER JOIN availability AS av ON app.person_id=av.person_id WHERE app.application_id=?1 AND av.from_date>s.season_start AND av.to_date<s.season_end",
            nativeQuery = true)
    List<Availability> getAvailabilityByApplicationId(int applicationId);

}
