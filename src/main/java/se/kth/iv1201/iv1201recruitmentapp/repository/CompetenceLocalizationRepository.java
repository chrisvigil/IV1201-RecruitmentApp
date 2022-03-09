package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.iv1201.iv1201recruitmentapp.model.CompetenceLocalization;

import java.util.List;
import java.util.Optional;

/**
 * Defines database access for the competence localizations.
 */
public interface CompetenceLocalizationRepository extends JpaRepository<CompetenceLocalization, Integer> {

    /**
     * Gets the correct competence localizations
     * according to the given competence locale.
     *
     * @param locale The locale.
     * @return The competence localizations.
     */
    List<CompetenceLocalization> findAllByLocale(String locale);

    /**
     * Gets the correct competence localization
     * according to the given competence id
     * and locale.
     *
     * @param competenceId The competence id.
     * @param locale The locale.
     * @return The competence localization.
     */
    @Query(value = "SELECT competence_localization_id, competence_id, locale, competence_name FROM competence_localization WHERE competence_id=?1 AND locale=?2",
            nativeQuery = true)
    Optional<CompetenceLocalization> findByCompetenceIdAndLocale(int competenceId, String locale);

}
