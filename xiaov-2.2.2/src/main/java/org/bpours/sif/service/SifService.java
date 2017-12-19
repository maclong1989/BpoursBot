package org.bpours.sif.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.b3log.latke.logging.Logger;
import org.b3log.xiaov.XiaoVServletListener;
import org.bpours.dao.mybatis.mapper.SifCardsMapper;
import org.bpours.dao.mybatis.pojo.SifCards;
import org.bpours.utils.Constants;
import org.bpours.utils.HttpClientUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SifService implements InitializingBean {

	private static final Logger LOGGER = Logger.getLogger(XiaoVServletListener.class.getName());

	@Autowired
	private SifCardsMapper SifCardsMapper;

	Map<Integer, List<SifCards>> aqours = new HashMap<>();
	Map<Integer, List<SifCards>> us = new HashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		List<SifCards> cards = SifCardsMapper.selectAll();

		cards.forEach(card -> {

			if (card.getUnitTypeId() > 100) {

				if (aqours.containsKey(card.getRarity())) {
					aqours.get(card.getRarity()).add(card);
				} else {
					List<SifCards> cardList = new ArrayList<>();
					cardList.add(card);
					aqours.put(card.getRarity(), cardList);
				}

			} else {

				if (us.containsKey(card.getRarity())) {
					us.get(card.getRarity()).add(card);
				} else {
					List<SifCards> cardList = new ArrayList<>();
					cardList.add(card);
					us.put(card.getRarity(), cardList);
				}

			}

		});

	}

	public String processSifService(final String keyword, final String userName) {

		String ret = "";

		if (keyword.startsWith("查询")) {
			ret = processSearch(keyword, userName);
		}

		if (keyword.startsWith("抽卡")) {
			ret = processPick(keyword, userName, 10);
		}

		if (keyword.startsWith("单抽")) {
			ret = processPick(keyword, userName, 1);
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

	private String processPick(final String keyword, final String userName, final int num) {

		String ret = "";

		try {

			String[] searches = keyword.trim().split("\\s+", 2);

			String type = searches[1];

			Map<Integer, List<SifCards>> currentGroup = null;

			if (type.equals("水团")) {
				currentGroup = aqours;
			} else if (type.equals("缪斯")) {
				currentGroup = us;
			} else {
				return "抽卡指令错误！抽卡 水团/缪斯";
			}

			List<Integer> ids = new ArrayList<>();

			for (int i = 0; i < num; i++) {
				int rand = RandomUtils.nextInt(100);

				Integer rarity = null;

				if (rand < 90) {
					rarity = 2;
				} else if (rand < 95) {
					rarity = 3;
				} else if (rand < 99) {
					rarity = 4;
				} else {
					rarity = 5;
				}

				List<SifCards> list = currentGroup.get(rarity);
				rand = RandomUtils.nextInt(list.size());

				SifCards card = list.get(rand);
				ids.add(card.getId());
			}

			List<SifCards> sifCards = SifCardsMapper.selectCardsByIds(ids);

			StringBuilder sb = new StringBuilder();

			sifCards.forEach(sifCard -> {

				sb.append("抽到卡片：");

				if (StringUtils.isNotEmpty(sifCard.getEponym())) {
					sb.append("[");
					sb.append(sifCard.getEponym());
					sb.append("]");
				}

				sb.append(sifCard.getName());
				sb.append(" ");
				sb.append("稀有度：");
				sb.append(Constants.raritys.get(sifCard.getRarity()));

				sb.append("\n");

				if (num == 1) {
					String retjson = HttpClientUtil
							.httpGetRequest("https://card.niconi.co.ni/cardApi/" + sifCard.getUnitNumber());

					try {
						JSONObject root = new JSONObject(retjson);

						JSONObject unit = root.getJSONObject("unit");

						String normalCardId = unit.getString("normal_card_id");
						String rankMaxCardId = unit.getString("rank_max_card_id");

						sb.append("未觉醒卡片封面:");
						sb.append("https://card.niconi.co.ni/card/v4/");
						sb.append(normalCardId);
						sb.append(".png");
						sb.append("\n");
						sb.append("觉醒卡片封面:");
						sb.append("https://card.niconi.co.ni/card/v4/");
						sb.append(rankMaxCardId);
						sb.append(".png");
						sb.append("\n");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			return sb.toString();

		} catch (Exception e) {
			LOGGER.info("用户查询格式错误." + e.getMessage());
			e.printStackTrace();
			ret = "抽卡错误";
		}

		return ret;
	}

}
