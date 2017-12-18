package org.bpours.dao.mybatis.pojo;

public class SifCards {

	public SifCards() {
	}

	public SifCards(Integer unitNumber, String normalLiveAsset, String rankMaxLiveAsset, String eponym, String name,
			Integer rarity, Integer attribute, Integer unitTypeId, Integer tokuten, Integer skillTriggerType,
			Integer skillType, Integer skillId, Integer leaderSkillId, Integer beforeLevelMax, Integer afterLevelMax) {
		this.unitNumber = unitNumber;
		this.normalLiveAsset = normalLiveAsset;
		this.rankMaxLiveAsset = rankMaxLiveAsset;
		this.eponym = eponym;
		this.name = name;
		this.rarity = rarity;
		this.attribute = attribute;
		this.unitTypeId = unitTypeId;
		this.tokuten = tokuten;
		this.skillTriggerType = skillTriggerType;
		this.skillType = skillType;
		this.skillId = skillId;
		this.leaderSkillId = leaderSkillId;
		this.beforeLevelMax = beforeLevelMax;
		this.afterLevelMax = afterLevelMax;
	}

	private Integer id;

	private Integer unitNumber;

	private String normalLiveAsset;

	private String rankMaxLiveAsset;

	private String eponym;

	private String name;

	private Integer rarity;

	private Integer attribute;

	private Integer unitTypeId;

	private Integer tokuten;

	private Integer skillTriggerType;

	private Integer skillType;

	private Integer skillId;

	private Integer leaderSkillId;

	private Integer beforeLevelMax;

	private Integer afterLevelMax;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getNormalLiveAsset() {
		return normalLiveAsset;
	}

	public void setNormalLiveAsset(String normalLiveAsset) {
		this.normalLiveAsset = normalLiveAsset == null ? null : normalLiveAsset.trim();
	}

	public String getRankMaxLiveAsset() {
		return rankMaxLiveAsset;
	}

	public void setRankMaxLiveAsset(String rankMaxLiveAsset) {
		this.rankMaxLiveAsset = rankMaxLiveAsset == null ? null : rankMaxLiveAsset.trim();
	}

	public String getEponym() {
		return eponym;
	}

	public void setEponym(String eponym) {
		this.eponym = eponym == null ? null : eponym.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getRarity() {
		return rarity;
	}

	public void setRarity(Integer rarity) {
		this.rarity = rarity;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(Integer unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public Integer getTokuten() {
		return tokuten;
	}

	public void setTokuten(Integer tokuten) {
		this.tokuten = tokuten;
	}

	public Integer getSkillTriggerType() {
		return skillTriggerType;
	}

	public void setSkillTriggerType(Integer skillTriggerType) {
		this.skillTriggerType = skillTriggerType;
	}

	public Integer getSkillType() {
		return skillType;
	}

	public void setSkillType(Integer skillType) {
		this.skillType = skillType;
	}

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public Integer getLeaderSkillId() {
		return leaderSkillId;
	}

	public void setLeaderSkillId(Integer leaderSkillId) {
		this.leaderSkillId = leaderSkillId;
	}

	public Integer getBeforeLevelMax() {
		return beforeLevelMax;
	}

	public void setBeforeLevelMax(Integer beforeLevelMax) {
		this.beforeLevelMax = beforeLevelMax;
	}

	public Integer getAfterLevelMax() {
		return afterLevelMax;
	}

	public void setAfterLevelMax(Integer afterLevelMax) {
		this.afterLevelMax = afterLevelMax;
	}
}