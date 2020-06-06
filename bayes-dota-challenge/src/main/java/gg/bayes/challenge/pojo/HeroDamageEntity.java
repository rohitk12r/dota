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
 * It's holds Damage entity by hitting specified hero.
 * 
 * @author RohitSharma
 *
 */
@Data
@Entity
@Table(name = "HERODAMAGE")
public class HeroDamageEntity {

	/**
	 * This is primary key of Damage, which is auto generation.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long damageId;

	/**
	 * It's holds the target hero name that damaged by specified hero.
	 */
	private String targetHero;

	/**
	 * It's holds the number of times it damaged by specified hero. 
	 */
	private Integer damage_instance;
	
	/**
	 * It's hold relation of {@link HeroKillsEntity}  to {@link HeroDamageEntity}}
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hero_Id", nullable = false)
	private HeroKillsEntity heroKills;
}
