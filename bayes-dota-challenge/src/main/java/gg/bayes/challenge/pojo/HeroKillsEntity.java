package gg.bayes.challenge.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * It's holds all Heroes who are involved in combat game event.
 * 
 * @author RohitSharma
 *
 */
@Data
@Entity
@Table(name = "HEROKILLS")
public class HeroKillsEntity {

	/**
	 * This is primary key of Hero, which is auto generation.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long heroId;

	/**
	 * It store matchId which identified by the specific game.
	 */
	private Long matchId;

	/**
	 * It's holds Hero who are involved in combat game.
	 */
	private String heroName;

	/**
	 * It's holds how many times hero had kills other heroes
	 */
	private Integer kills;

	/**
	 * It's holds total damage count by hitted by all heroes.
	 */
	private Integer total_damages;

	/**
	 * It's hold relation of {@link HeroKillsEntity} to {@link HeroItemsEntity}}
	 */
	@OneToMany(mappedBy = "heroKills", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<HeroItemsEntity> heroIteamCollection = new ArrayList<>();

	/**
	 * It's hold relation of {@link HeroKillsEntity} to {@link HeroSpellsEntity}}
	 */
	@OneToMany(mappedBy = "heroKills", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<HeroSpellsEntity> heroSpellsCollection = new ArrayList<>();

	/**
	 * It's hold relation of {@link HeroKillsEntity} to {@link HeroDamageEntity}}
	 */
	@OneToMany(mappedBy = "heroKills", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<HeroDamageEntity> heroDamageCollection = new ArrayList<>();
}
