/**
 * Project Name:xiaov-2.2.2 File Name:AnswerService.java Package Name:org.bpours.service Date:2017年12月18日下午4:15:00
 * Copyright (c) 2017, jiangyl3@asiainfo.com All Rights Reserved.
 *
 */

package org.bpours.service;

import org.apache.commons.lang.StringUtils;
import org.b3log.xiaov.util.XiaoVs;
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

		if (!content.matches(".+\\s.+")) {
			return "";
		}

		String keyword = content.split(" ", 2)[1];

		String ret = sifService.processSifService(keyword, userName);

		if (StringUtils.isNotEmpty(ret)) {
			return ret;
		}

		ret = repeater(keyword, userName);

		if (StringUtils.isNotEmpty(ret)) {
			return ret;
		}

		ret = study(keyword, userName);

		if (StringUtils.isNotEmpty(ret)) {
			return ret;
		}

		ret = keywordAnswer(keyword, userName);

		if (StringUtils.isNotEmpty(ret)) {
			return ret;
		}

		return ret;
	}

	private String repeater(final String keyword, final String userName) {

		if (keyword.startsWith("复读机") && keyword.matches("复读机\\s+.*")) {
			String[] newKwyWords = keyword.split("\\s+", 2);
			String content = newKwyWords[1].replace("复读机", "").replace(XiaoVs.QQ_BOT_NAME, "");
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < 2; i++) {
				sb.append(content);
				if (i != 1) {
					sb.append("<br/>");
				}
			}

			return sb.toString();

		}

		return "";
	}

	private String study(final String keyword, final String userName) {

		if (keyword.startsWith("学习") && keyword.matches("学习\\s+.*")) {
			String[] newKwyWords = keyword.split("\\s+", 3);
			Answer answer = new Answer();
			answer.setKeyword(newKwyWords[1]);
			answer.setAnswer(newKwyWords[2]);

			answerMapper.insert(answer);

			return "学习成功";
		}

		return "";
	}

	private String keywordAnswer(final String keyword, final String userName) {

		Answer answer = answerMapper.selectByPrimaryKey(keyword);

		if (answer != null) {
			return answer.getAnswer();
		}

		return "";
	}

}
