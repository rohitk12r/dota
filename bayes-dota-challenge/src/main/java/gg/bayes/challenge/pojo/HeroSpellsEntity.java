package gg.bayes.challenge.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * It's holds Spells casts by specified hero.
 * 
 * @author RohitSharma
 *
 */
@Data
@Entity
@Table(name="HEROSPELLS")
public class HeroSpellsEntity {
	
	/**
	 * This is primary key of Items, which is auto generation.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long spellId;
	
	
	/**
	 * It's holds the number of times they cast said spell by specified hero.
	 */
	private Integer caste;
	
	/**
	 * 
	 * It's holds to Spells Name caste by specified hero.
	 */
	private String spellName;
	
	/**
	 * It's hold relation of {@link HeroKillsEntity}  to {@link HeroSpellsEntity}}
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="hero_Id", nullable=false)
	private HeroKillsEntity heroKills;
}
