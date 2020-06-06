package gg.bayes.challenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import gg.bayes.challenge.pojo.HeroDamageEntity;
import gg.bayes.challenge.pojo.HeroItemsEntity;
import gg.bayes.challenge.pojo.HeroKillsEntity;
import gg.bayes.challenge.pojo.HeroSpellsEntity;

/**
 * This class holds for reading contents and manipulation events of heroes.
 * 
 * @author RohitSharma
 *
 */
@Component
public class ReaderUtils {

	/**
	 * This is used for finding hero name in events
	 */
	private static final String NPC_DOTA_HERO = "npc_dota_hero_";
	/**
	 * This is used for finding items.
	 */
	private static final String ITEMS = "item_";
	/**
	 * This is used for finding timestamp
	 */
	private static final String BRACKETS = "[";

	/**
	 * This is used for finding spells name
	 */
	private static final String HYPHEN = "_";

	/**
	 * This is used for splits event lines by spaces.
	 */
	private static final String REG_EXP = "\\s+";

	/**
	 * This is used for to identified unique hero.
	 */
	private static final String SYMBOL_S = "'s";

	/**
	 * It is hold for list of unique heroes with his total kills count.
	 */
	private Set<HeroKillsEntity> heroKillsEntities = new HashSet<HeroKillsEntity>();

	/**
	 * It's hold for list of unique heroes
	 */
	private Set<String> heroes = new HashSet<>();

	/**
	 * It's holds key as hero and value is total killed by count.
	 */
	private Map<String, Integer> heroKillsMap = new HashMap<>();

	/**
	 * It's holds key as hero spells and value is total number of he spells for
	 * casts.
	 */
	Map<String, Integer> spellMap = new HashMap<>();

	/**
	 * It's holds key as hero and value is list of damage of heroes he damaged.
	 */
	Map<String, List<HeroDamageEntity>> heroDamageEntitiesMap = new HashMap<>();

	/**
	 * This method is used for read the Match events and manipulated to in
	 * structure way in HeroEntity Object.
	 * 
	 * @param payload
	 *            this is match event combat logs.
	 * @param matchId
	 *            this is match Id for match.
	 * @return {@link HeroKillsEntity}
	 */
	public Set<HeroKillsEntity> readPayload(String payload, Long matchId) {
		read(payload, matchId);

		heroKillsEntities.stream().forEach(heroEntity -> {
			heroEntity.setKills(heroKillsMap.get(heroEntity.getHeroName()));
			addEntityForSpell(heroEntity);
			addEntityForDamage(heroEntity);
			heroEntity.setTotal_damages(totalDamageCountforHero(heroEntity.getHeroName()));
		});
		return heroKillsEntities;
	}

	public void clean() {
		spellMap.clear();
		heroDamageEntitiesMap.clear();
		heroKillsMap.clear();
		heroes.clear();
		heroKillsEntities.clear();

	}

	/**
	 * This method is used to read all content and apply events business logic
	 * to do in structure data.
	 * 
	 * @param payload
	 *            this is match event combat logs.
	 * @param matchId
	 *            this is match Id for match.
	 */
	private void read(String payload, Long matchId) {
		try (BufferedReader reader = new BufferedReader(new StringReader(payload))) {
			String eventlogs = reader.readLine();
			while (eventlogs != null) {
				eventlogs = reader.readLine();
				if (StringUtils.isNoneEmpty(eventlogs) && !eventlogs.contains(Event.USES.getEvent())
						&& !eventlogs.contains("game")) {
					addingUniqueHeroInSetInterface(eventlogs, matchId);
					checkEvent(eventlogs);
				}
			}
		} catch (IOException exc) {
			// quit
		}
	}

	/**
	 * This method is check event with start hero name as {@link NPC_DOTA_HERO}
	 * and add it in {@link Set} interface for counting unique hero in this
	 * Match, and also manipulate {@link HeroKillsEntity}.
	 * 
	 * @param line
	 *            this is casts event line
	 * @param matchId
	 *            it is match Id of Match
	 */
	private void addingUniqueHeroInSetInterface(String eventlogs, long matchId) {
		StringTokenizer tokens = new StringTokenizer(eventlogs);
		while (tokens.hasMoreElements()) {
			String token = tokens.nextToken();
			if (token.startsWith(NPC_DOTA_HERO)) {
				String hero = token.substring(NPC_DOTA_HERO.length());
				if (!hero.contains(SYMBOL_S)) {
					if (heroes.add(hero)) {
						HeroKillsEntity heroKillsEntity = new HeroKillsEntity();
						heroKillsEntity.setHeroName(hero);
						heroKillsEntity.setMatchId(matchId);
						heroKillsEntities.add(heroKillsEntity);
						break;
					}
				}
			}
		}
	}

	/**
	 * This method is control all events in this Match.
	 * 
	 * @param line
	 *            It is one event
	 */
	private void checkEvent(String eventlogs) {
		if (eventlogs.contains(Event.CASTS.getEvent())) {
			addSpellForHeroMap(eventlogs);
		} else if (eventlogs.contains(Event.BUYS.getEvent())) {
			addItemsForHero(eventlogs);
		} else if (eventlogs.contains(Event.HITS.getEvent())) {
			addDamgeByHitsHero(eventlogs);

		} else if (eventlogs.contains(Event.KILLED.getEvent())) {
			for (String heroName : heroes) {
				if (eventlogs.contains(NPC_DOTA_HERO + heroName)) {
					if (heroKillsMap.containsKey(heroName)) {
						heroKillsMap.put(heroName, heroKillsMap.get(heroName) + 1);
					} else {
						heroKillsMap.put(heroName, 1);
					}
					break;
				}
			}
		}
	}

	/**
	 * It is calculating {@link Event.CASTS} logics to manipulate spells hero.
	 * 
	 * @param eventlogs
	 *            this is casts event logs
	 */
	private void addSpellForHeroMap(String eventlogs) {
		for (HeroKillsEntity heroEntity : heroKillsEntities) {
			if (eventlogs.contains(NPC_DOTA_HERO + heroEntity.getHeroName())) {
				StringTokenizer tokenizer = new StringTokenizer(eventlogs);
				while (tokenizer.hasMoreElements()) {
					String token = tokenizer.nextToken();
					if (token.startsWith(heroEntity.getHeroName() + HYPHEN)) {
						if (spellMap.containsKey(token)) {
							spellMap.put(token, spellMap.get(token) + 1);
						} else {
							spellMap.put(token, 1);
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * This method used for manage relationship between {@link HeroKillsEntity}
	 * to {@link HeroDamageEntity}
	 * 
	 * @param heroEntity
	 *            holds heroEntity
	 */
	private void addEntityForDamage(HeroKillsEntity heroEntity) {
		List<HeroDamageEntity> damageEntities = heroDamageEntitiesMap.get(heroEntity.getHeroName());
		heroEntity.setHeroDamageCollection(damageEntities);
		damageEntities.stream().forEach(damageEntity -> {
			damageEntity.setHeroKills(heroEntity);
		});
	}

	/**
	 * This method used for calculated total damage count of hero.
	 * 
	 * @param heroName
	 *            holds Hero name to count his total damaged.
	 * @return {@link Integer}
	 */
	private int totalDamageCountforHero(String heroName) {
		Map<String, List<HeroDamageEntity>> damageEntitiesMap = new HashMap<>(heroDamageEntitiesMap);
		damageEntitiesMap.remove(heroName);
		int totalDamageCount = 0;
		for (String hero : damageEntitiesMap.keySet()) {
			List<HeroDamageEntity> damageEntities = damageEntitiesMap.get(hero);
			for (Iterator<HeroDamageEntity> iterator = damageEntities.iterator(); iterator.hasNext();) {
				HeroDamageEntity damageEntity = (HeroDamageEntity) iterator.next();
				if (heroName.equals(damageEntity.getTargetHero())) {
					totalDamageCount = totalDamageCount + damageEntity.getDamage_instance();
				}
			}
		}
		return totalDamageCount;

	}

	/**
	 * It's manipulating {@link HeroSpellsEntity}
	 * 
	 * @param heroEntity
	 *            {@link HeroKillsEntity}
	 */
	private void addEntityForSpell(HeroKillsEntity heroEntity) {
		List<HeroSpellsEntity> spellCollection = new ArrayList<>();
		spellMap.keySet().stream().filter(key -> key.startsWith(heroEntity.getHeroName())).forEach(key -> {
			HeroSpellsEntity spellsEntity = new HeroSpellsEntity();
			spellsEntity.setSpellName(key);
			spellsEntity.setCaste(spellMap.get(key));
			spellsEntity.setHeroKills(heroEntity);
			spellCollection.add(spellsEntity);
		});
		heroEntity.setHeroSpellsCollection(spellCollection);
	}

	/**
	 * It is calculating {@link Event.HITS} logics to manipulate damage hero
	 * 
	 * @param eventlogs
	 *            this is damage event logs
	 */
	private void addDamgeByHitsHero(String eventlogs) {
		String[] splits = eventlogs.split(REG_EXP);
		String hittedBy = splits[1].substring(NPC_DOTA_HERO.length());
		String hitted = splits[3].substring(NPC_DOTA_HERO.length());
		Integer damageCount = Integer.valueOf(splits[7]);
		if (heroDamageEntitiesMap.containsKey(hittedBy)) {
			List<HeroDamageEntity> damageEntities = heroDamageEntitiesMap.get(hittedBy);
			if (!exitsHittedByHero(damageEntities, hitted, damageCount)) {
				HeroDamageEntity damageEntity = new HeroDamageEntity();
				damageEntity.setTargetHero(hitted);
				damageEntity.setDamage_instance(damageCount);
				damageEntities.add(damageEntity);
			}
			heroDamageEntitiesMap.put(hittedBy, damageEntities);
		} else {
			List<HeroDamageEntity> damageEntities = new ArrayList<>();
			HeroDamageEntity damageEntity = new HeroDamageEntity();
			damageEntity.setTargetHero(hitted);
			damageEntity.setDamage_instance(damageCount);
			damageEntities.add(damageEntity);
			heroDamageEntitiesMap.put(hittedBy, damageEntities);
		}
	}

	/**
	 * It is checking condition is Hitted By hero exits or not if exits then
	 * return true or return false
	 * 
	 * @param damageEntities
	 *            list of damage entity
	 * @param hitted
	 *            this hitted hero
	 * @param damageCount
	 *            the total damaged count
	 * @return {@link Boolean}
	 */
	private boolean exitsHittedByHero(List<HeroDamageEntity> damageEntities, String hitted, Integer damageCount) {
		for (HeroDamageEntity damageEntity : damageEntities) {
			if (damageEntity.getTargetHero().equals(hitted)) {
				Integer damageInstance = damageEntity.getDamage_instance() + damageCount;
				damageEntity.setDamage_instance(damageInstance);
				return true;
			}
		}
		return false;
	}

	/**
	 * It is calculating for {@link Event.ITEMS} logics to manipulate items for
	 * hero.
	 * 
	 * @param eventlogs
	 *            this is casts event logs
	 */
	private void addItemsForHero(String eventlogs) {
		for (HeroKillsEntity heroEntity : heroKillsEntities) {
			if (eventlogs.contains(NPC_DOTA_HERO + heroEntity.getHeroName())) {
				HeroItemsEntity itemsEntity = new HeroItemsEntity();
				StringTokenizer tokenizer = new StringTokenizer(eventlogs);
				while (tokenizer.hasMoreElements()) {
					String token = tokenizer.nextToken();
					if (token.startsWith(BRACKETS)) {
						itemsEntity.setTime(getTimeStamp(token));
					} else if (token.startsWith(ITEMS)) {
						String item = token.substring(ITEMS.length());
						itemsEntity.setItem(item);
					}
				}
				heroEntity.getHeroIteamCollection().add(itemsEntity);
				itemsEntity.setHeroKills(heroEntity);
				break;
			}
		}
	}

	/**
	 * It is converting string to LocalTime
	 * 
	 * @param token
	 *            this is timestamp
	 * @return {@link LocalTime}
	 */
	private Long getTimeStamp(String token) {
		token = token.substring(1, token.length() - 1);
		LocalTime localTime = LocalTime.parse(token);
		long time = localTime.toNanoOfDay() / 1000000;
		return time;
	}
}
