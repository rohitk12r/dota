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
 * It's holds Items purchase by specified hero.
 * 
 * @author RohitSharma
 *
 */
@Data
@Entity
@Table(name = "HEROITEMS")
public class HeroItemsEntity {

	/**
	 * This is primary key of Items, which is auto generation.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId;

	/**
	 * It's holds to purchase items by specified hero
	 */
	private String item;
	/**
	 * It's holds event time when buy items by specified hero
	 */
	private Long time;

	/**
	 * It's hold relation of {@link HeroKillsEntity} to {@link HeroItemsEntity}}
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hero_Id", nullable = false)
	private HeroKillsEntity heroKills;

}
