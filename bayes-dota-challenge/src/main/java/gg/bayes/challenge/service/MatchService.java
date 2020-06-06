package gg.bayes.challenge.service;

import java.util.List;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

public interface MatchService {

	/**
	 * This method used for insert events in to Database.
	 * 
	 * @param payload
	 *            this is content of events
	 * @return {@link Long}
	 */
	Long ingestMatch(String payload);

	/**
	 * This method used to fetch all heroes and how many times he kills.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @return {@link HeroKills}
	 */
	List<HeroKills> getHeroKillsByMatchId(Long matchId);

	/**
	 * This method used to fetch all Items and timestamp specified by hero.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @param heroName
	 *            specified hero to fetch Items
	 * @return {@link HeroItems}
	 */
	List<HeroItems> getItemsByMatchIdAndHeroName(Long matchId, String heroName);

	/**
	 * This method used to fetch all Spells and casts specified by hero.
	 * 
	 * @param matchId
	 *            It used for identified by the specific Match.
	 * @param heroName
	 *            specified hero to fetch Spells
	 * @return {@link HeroSpells}
	 */
	List<HeroSpells> getHeroSpellsByMatchIdAndHeroName(Long matchId, String heroName);

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
	List<HeroDamage> getHeroDamageByMatchIdAndHeroName(Long matchId, String heroName);
}
