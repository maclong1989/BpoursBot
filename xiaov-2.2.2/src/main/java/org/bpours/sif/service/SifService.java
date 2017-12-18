package org.bpours.sif.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.logging.Logger;
import org.b3log.xiaov.XiaoVServletListener;
import org.bpours.dao.mybatis.mapper.SifCardsMapper;
import org.bpours.dao.mybatis.pojo.SifCards;
import org.bpours.utils.HttpClientUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SifService {

	private static final Logger LOGGER = Logger.getLogger(XiaoVServletListener.class.getName());

	@Autowired
	private SifCardsMapper SifCardsMapper;

	public String processSifService(final String keyword, final String userName) {

		String ret = "";

		if (keyword.startsWith("查询")) {
			ret = processSearch(keyword, userName);
		}

		return ret;
	}

	private String processSearch(final String keyword, final String userName) {

		String ret = "";

		try {

			String[] searches = keyword.trim().split("\\s+", 2);

			String search = searches[1];

			List<SifCards> sifCardsList = SifCardsMapper.selectCardsInfos(search);

			if (sifCardsList == null || sifCardsList.isEmpty()) {
				return "查无此卡。";
			}

			StringBuilder sb = new StringBuilder();

			sifCardsList.forEach(sifCard -> {

				sb.append("查到卡片：");

				if (StringUtils.isNotEmpty(sifCard.getEponym())) {
					sb.append("[");
					sb.append(sifCard.getEponym());
					sb.append("]");
				}

				String retjson = HttpClientUtil
						.httpGetRequest("https://card.niconi.co.ni/cardApi/" + sifCard.getUnitNumber());

				try {
					JSONObject root = new JSONObject(retjson);

					JSONObject unit = root.getJSONObject("unit");

					String normalCardId = unit.getString("normal_card_id");
					String rankMaxCardId = unit.getString("rank_max_card_id");

					sb.append(sifCard.getName());
					sb.append(" ");
					sb.append("未觉醒卡片封面:");
					sb.append("https://card.niconi.co.ni/card/v4/");
					sb.append(normalCardId);
					sb.append(".png");
					sb.append(" ");
					sb.append("觉醒卡片封面:");
					sb.append("https://card.niconi.co.ni/card/v4/");
					sb.append(rankMaxCardId);
					sb.append(".png");
					sb.append("");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				sb.append("\n");
			});

			return sb.toString();

		} catch (Exception e) {
			LOGGER.info("用户查询格式错误." + e.getMessage());
			e.printStackTrace();
			ret = "查询格式错误，正确的查询格式为： 查询(空格)查询的内容";
		}

		return ret;
	}

}
