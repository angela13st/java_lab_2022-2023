package com.angela.model;

import java.util.Arrays;

public enum MonthNames {
	Sijecanj(1), Veljaca(2), Ozujak(3), Travanj(4), Svibanj(5), Lipanj(6), Srpanj(7), Kolovoz(8), Rujan(9),
	Listopad(10), Studeni(11), Prosinac(12);

	private int value;

	MonthNames(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String valueOf(int month) {
		return Arrays.stream(MonthNames.values())
	            .filter(ime -> ime.value ==month)
	            .findFirst().get().name();
	}
}
