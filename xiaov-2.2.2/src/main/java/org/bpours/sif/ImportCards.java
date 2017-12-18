package org.bpours.sif;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.bpours.dao.mybatis.mapper.SifCardsMapper;
import org.bpours.dao.mybatis.pojo.SifCards;
import org.bpours.utils.SpringManager;

import com.alibaba.fastjson.JSON;

public class ImportCards {

	public static void main(String[] args) {

		try {

			SpringManager.init();

			String cardsStr = FileUtils.readFileToString(new File("D:\\sif\\cards.txt"));

			List<SifCardJson> cardsJson = JSON.parseArray(cardsStr, SifCardJson.class);

			List<SifCards> cards = cardsJson.stream()
					.map(o -> new SifCards(o.getUnit_number(), o.getNormal_live_asset(), o.getRank_max_live_asset(),
							o.getEponym(), o.getName(), o.getRarity(), o.getAttribute(), o.getUnit_type_id(),
							o.getTokuten(), o.getSkill_trigger_type(), o.getSkill_id(), o.getSkill_id(),
							o.getLeader_skill_id(), o.getBefore_level_max(), o.getAfter_level_max()))
					.collect(Collectors.toList());

			SifCardsMapper sifCardsMapper = SpringManager.getBean(SifCardsMapper.class);
			cards.forEach(card -> sifCardsMapper.insert(card));

			System.out.println("导入完毕,共有" + cards.size() + "条");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
