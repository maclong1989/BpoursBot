package org.bpours.dao.mybatis.mapper;

import org.bpours.dao.mybatis.pojo.Answer;

public interface AnswerMapper {
    int deleteByPrimaryKey(String keyword);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(String keyword);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);
}