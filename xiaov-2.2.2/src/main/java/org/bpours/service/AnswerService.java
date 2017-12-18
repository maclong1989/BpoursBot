/**
 * Project Name:xiaov-2.2.2 File Name:AnswerService.java Package Name:org.bpours.service Date:2017年12月18日下午4:15:00
 * Copyright (c) 2017, jiangyl3@asiainfo.com All Rights Reserved.
 *
 */

package org.bpours.service;

import org.apache.commons.lang.StringUtils;
import org.bpours.dao.mybatis.mapper.AnswerMapper;
import org.bpours.dao.mybatis.pojo.Answer;
import org.bpours.sif.service.SifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:AnswerService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年12月18日 下午4:15:00 <br/>
 * 
 * @author jiangyl3@asiainfo.com
 * @version
 * @since JDK 1.6
 * @see
 */
@Service
public class AnswerService {

	@Autowired
	private AnswerMapper answerMapper;

	@Autowired
	private SifService sifService;

	public String answer(final String content, final String userName) {

		if (!content.matches(".+ .+")) {
			return "";
		}

		String keyword = content.split(" ", 2)[1];

		String ret = sifService.processSifService(keyword, userName);

		if (StringUtils.isNotEmpty(ret)) {
			return ret;
		}

		// if (keyword.startsWith("学习") && keyword.matches("学习 [^ ]+ [^ ]+")) {
		// String[] newKwyWords = keyword.split(" ");
		// Answer answer = new Answer();
		// answer.setKeyword(newKwyWords[1]);
		// answer.setAnswer(newKwyWords[2]);
		//
		// answerMapper.insert(answer);
		// }

		Answer answer = answerMapper.selectByPrimaryKey(keyword);

		if (answer != null) {
			return answer.getAnswer();
		}

		return "";
	}

}
