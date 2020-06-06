package gg.bayes.challenge.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import gg.bayes.challenge.dao.HeroRepository;
import gg.bayes.challenge.pojo.HeroKillsEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HeroRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	HeroRepository heroRepository;

	@Test
	public void whenFindByMatchIdThenReturnHeroEntity() {
		HeroKillsEntity heroEntity = new HeroKillsEntity();

		heroEntity.setHeroName("rubick");
		heroEntity.setMatchId(1L);
		heroEntity.setKills(27);
		heroEntity.setTotal_damages(9669);
		entityManager.persist(heroEntity);
		entityManager.flush();

		List<HeroKillsEntity> heroEntityCollection = heroRepository.findByMatchId(1L);

		for (HeroKillsEntity hero : heroEntityCollection) {
			assertThat(hero.getHeroName()).isEqualTo(heroEntity.getHeroName());
			assertThat(hero.getKills()).isEqualTo(heroEntity.getKills());
			assertThat(hero.getTotal_damages()).isEqualTo(heroEntity.getTotal_damages());
		}
	}

	@After
	public void cleanUp() {
		heroRepository.deleteAll();
	}
}
