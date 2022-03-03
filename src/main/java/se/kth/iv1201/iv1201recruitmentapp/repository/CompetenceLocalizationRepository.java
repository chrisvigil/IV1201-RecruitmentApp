package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.iv1201recruitmentapp.model.CompetenceLocalization;

import java.util.List;

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

}
