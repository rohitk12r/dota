package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gg.bayes.challenge.pojo.HeroDamageEntity;

public interface HeroDamageRepository extends JpaRepository<HeroDamageEntity, Long> {

	@Query("SELECT hs FROM HeroDamageEntity hs, HeroKillsEntity hk WHERE hs.heroKills = hk AND hk.heroName=:heroName AND hk.matchId=:matchId")
	List<HeroDamageEntity> join(@Param("heroName") String heroName, @Param("matchId") Long matchId);


}
