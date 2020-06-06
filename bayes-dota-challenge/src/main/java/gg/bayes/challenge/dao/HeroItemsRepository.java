package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gg.bayes.challenge.pojo.HeroItemsEntity;

public interface HeroItemsRepository extends JpaRepository<HeroItemsEntity, Long> {

	@Query("SELECT hs FROM HeroItemsEntity hs, HeroKillsEntity hk WHERE hs.heroKills = hk AND hk.heroName=:heroName AND hk.matchId=:matchId")
	List<HeroItemsEntity> join(@Param("heroName") String heroName, @Param("matchId") Long matchId);

}
