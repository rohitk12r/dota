package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gg.bayes.challenge.pojo.HeroSpellsEntity;

public interface HeroSpellsRepository extends JpaRepository<HeroSpellsEntity, Long> {

	@Query("SELECT hs FROM HeroSpellsEntity hs, HeroKillsEntity hk WHERE hs.heroKills = hk AND hk.heroName=:heroName AND hk.matchId=:matchId")
	List<HeroSpellsEntity> join(@Param("heroName") String heroName, @Param("matchId") Long matchId);

}
