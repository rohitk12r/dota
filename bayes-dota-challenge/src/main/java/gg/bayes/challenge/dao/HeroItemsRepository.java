package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gg.bayes.challenge.pojo.HeroItemsEntity;
import gg.bayes.challenge.pojo.HeroKillsEntity;

/**
 * This interface used for fetching {@link HeroItemsEntity} object.
 * 
 * @author RohitSharma
 */
public interface HeroItemsRepository extends JpaRepository<HeroItemsEntity, Long> {

	/**
	 * This is used to fetch all {@link HeroItemsEntity} with Join of the table
	 * {@link HeroKillsEntity}.
	 * 
	 * @param heroName
	 *            The Hero Name
	 * @param matchId
	 *            this is used for matching ID for {@link HeroKillsEntity} of
	 *            match ID
	 * @return {@link HeroItemsEntity}
	 */
	@Query("SELECT hs FROM HeroItemsEntity hs, HeroKillsEntity hk WHERE hs.heroKills = hk AND hk.heroName=:heroName AND hk.matchId=:matchId")
	List<HeroItemsEntity> join(@Param("heroName") String heroName, @Param("matchId") Long matchId);

}
