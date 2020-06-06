package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gg.bayes.challenge.pojo.HeroKillsEntity;

/**
 * This interface used for fetching {@link HeroKillsEntity} object.
 * 
 * @author RohitSharma
 */
public interface HeroRepository extends JpaRepository<HeroKillsEntity, Long> {

	/**
	 * It's used to fetch all hero which is matching with {@value matchId}
	 * 
	 * @param matchId
	 *            this is used for matching ID for fetching of all Heroes
	 * @return {@link HeroKillsEntity}
	 */
	List<HeroKillsEntity> findByMatchId(Long matchId);
}
