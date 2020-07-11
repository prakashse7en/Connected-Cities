package org.connectedCities.utility;

public class Terminator {
	public static void terminate(final String message, final int status) {
		System.out.println(message);
		System.exit(status);
	}

	public static void yes() {
		terminate("yes", 0);
	}

	public static void no() {
		terminate("no", 0);
	}
}
