package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gg.bayes.challenge.pojo.HeroKillsEntity;
import gg.bayes.challenge.pojo.HeroSpellsEntity;

/**
 * This interface used for fetching {@link HeroSpellsEntity} object.
 * 
 * @author RohitSharma
 */
public interface HeroSpellsRepository extends JpaRepository<HeroSpellsEntity, Long> {

	/**
	 * This is used to fetch all {@link HeroSpellsEntity} with Join of the table
	 * {@link HeroKillsEntity}.
	 * 
	 * @param heroName
	 *            The Hero Name
	 * @param matchId
	 *            this is used for matching ID for {@link HeroKillsEntity} of
	 *            match ID
	 * @return {@link HeroSpellsEntity}
	 */
	@Query("SELECT hs FROM HeroSpellsEntity hs, HeroKillsEntity hk WHERE hs.heroKills = hk AND hk.heroName=:heroName AND hk.matchId=:matchId")
	List<HeroSpellsEntity> join(@Param("heroName") String heroName, @Param("matchId") Long matchId);

}
