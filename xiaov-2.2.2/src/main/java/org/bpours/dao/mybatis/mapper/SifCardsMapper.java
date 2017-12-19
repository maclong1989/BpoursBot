package org.bpours.dao.mybatis.mapper;

import java.util.List;

import org.bpours.dao.mybatis.pojo.SifCards;

public interface SifCardsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SifCards record);

	int insertSelective(SifCards record);

	SifCards selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SifCards record);

	int updateByPrimaryKey(SifCards record);

	List<SifCards> selectCardsInfos(String search);

	Integer selectCountOfCards();

	List<SifCards> selectCardsByIds(List<Integer> ids);

	List<SifCards> selectAll();
}