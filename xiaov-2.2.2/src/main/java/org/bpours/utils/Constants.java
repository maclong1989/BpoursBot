package org.bpours.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static Map<Integer, String> raritys = new HashMap<>();

	static {
		raritys.put(1, "N");
		raritys.put(2, "R");
		raritys.put(3, "SR");
		raritys.put(5, "SSR");
		raritys.put(4, "UR");
	}

}
