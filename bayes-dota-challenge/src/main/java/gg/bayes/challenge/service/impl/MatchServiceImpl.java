package gg.bayes.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.bayes.challenge.dao.HeroDamageRepository;
import gg.bayes.challenge.dao.HeroItemsRepository;
import gg.bayes.challenge.dao.HeroRepository;
import gg.bayes.challenge.dao.HeroSpellsRepository;
import gg.bayes.challenge.pojo.HeroDamageEntity;
import gg.bayes.challenge.pojo.HeroItemsEntity;
import gg.bayes.challenge.pojo.HeroKillsEntity;
import gg.bayes.challenge.pojo.HeroSpellsEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import gg.bayes.challenge.utils.ReaderUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * This class holds all events business logic
 * 
 * @author RohitSharma
 *
 */
@Slf4j
@Service
@Transactional
public class MatchServiceImpl implements MatchService {

	/**
	 * It is counting for match.
	 */
	private static Long matchId = 1L;

	/**
	 * Initialization for this Object
	 */
	@Autowired
	public MatchServiceImpl() {
		// no const
	}

	/**
	 * This is used to connect database to fetch HeroEntity
	 */
	@Autowired
	private HeroRepository heroRepository;

	@Autowired
	private HeroSpellsRepository heroSpellsRepository;

	@Autowired
	private HeroItemsRepository heroItemsRepository;

	@Autowired
	private HeroDamageRepository heroDamageRepository;

	/**
	 * This is utils class for reading file and manipulation events.
	 */
	@Autowired
	private ReaderUtils readerUtils;

	/**
	 * This method used for insert events in to Database.
	 * 
	 * @param payload
	 *            this is content of events
	 * @return {@link Long}
	 */
	@Override
	public Long ingestMatch(String payload) {
		Set<HeroKillsEntity> heroEntities = readerUtils.readPayload(payload, matchId);
		try {
			heroRepository.saveAll(heroEntities);
			matchId++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		readerUtils.clean();
		return matchId-1;
	}

	/**
	 * This method used to fetch all heroes and how many times he kills.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @return {@link HeroKills}
	 */
	@Override
	public List<HeroKills> getHeroKillsByMatchId(Long matchId) {
		List<HeroKillsEntity> listOfHero = heroRepository.findByMatchId(matchId);
		List<HeroKills> heroKills = listOfHero.stream().map(list -> new HeroKills(list.getHeroName(), list.getKills()))
				.collect(Collectors.toList());
		return heroKills;
	}

	/**
	 * This method used to fetch all Items and timestamp specified by hero.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @param heroName
	 *            specified hero to fetch Items
	 * @return {@link HeroItems}
	 */
	@Override
	public List<HeroItems> getItemsByMatchIdAndHeroName(Long matchId, String heroName) {
		List<HeroItemsEntity> itemsEntity = heroItemsRepository.join(heroName, matchId);
		List<HeroItems> heroItems = itemsEntity.stream().map(item -> new HeroItems(item.getItem(), item.getTime()))
				.collect(Collectors.toList());
		return heroItems;
	}

	/**
	 * This method used to fetch all Spells and casts specified by hero.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @param heroName
	 *            specified hero to fetch Spells
	 * @return {@link HeroSpells}
	 */
	@Override
	public List<HeroSpells> getHeroSpellsByMatchIdAndHeroName(Long matchId, String heroName) {
		List<HeroSpellsEntity> spellsEntity = heroSpellsRepository.join(heroName, matchId);
		List<HeroSpells> heroSpells = spellsEntity.stream()
				.map(spell -> new HeroSpells(spell.getSpellName(), spell.getCaste())).collect(Collectors.toList());
		return heroSpells;
	}

	/**
	 * This method used to fetch all damageHero, DamageInstance and TotalDamage
	 * specified by hero.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @param heroName
	 *            specified hero to fetch Damage
	 * @return {@link HeroDamage}
	 */
	@Override
	public List<HeroDamage> getHeroDamageByMatchIdAndHeroName(Long matchId, String heroName) {
		List<HeroKillsEntity> listOfHero = heroRepository.findByMatchId(matchId);
		List<HeroDamageEntity> damageEntities = heroDamageRepository.join(heroName, matchId);
		List<HeroDamage> heroDamageList = damageEntities.stream()
				.map(damageEntity -> new HeroDamage(damageEntity.getTargetHero(), damageEntity.getDamage_instance()))
				.collect(Collectors.toList());
		for (HeroKillsEntity heroEntity : listOfHero) {
			if (!Objects.equals(heroEntity.getHeroName(), heroName)) {
				for (HeroDamage heroDamage : heroDamageList) {
					if (heroDamage.getTarget().equals(heroEntity.getHeroName())) {
						heroDamage.setTotalDamage(heroEntity.getTotal_damages());
					}
				}
			}
		}
		return heroDamageList;
	}
}
