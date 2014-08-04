package com.opendata.common.util;

public class IdFactory {
	private static long beginId = 0;

	public synchronized static Long getId() {
		long id = System.currentTimeMillis();
		while (id == beginId) {
			id = System.currentTimeMillis();
		}
		beginId = id;
		return id;
	}
}
