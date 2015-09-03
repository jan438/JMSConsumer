package com.mylab;

import java.util.Arrays;
import java.util.HashSet;

public class SimpleJmsApp {
	private static final String BROKER_URL = "tcp://localhost:61616?jms.prefetchPolicy.all=1000";
	private static final int CONSUME_LIFE_TIME_IN_MS = 3600 * 1000;
	private static final HashSet<String> TOPICS = new HashSet<>(Arrays.asList("destination"));

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			TOPICS.clear();
			TOPICS.addAll(Arrays.asList(args));
		}

		System.out.println("Now starting consumers...");
		for (String topic : TOPICS) {
			SimpleTopicConsumer consumer = new SimpleTopicConsumer(BROKER_URL, topic, CONSUME_LIFE_TIME_IN_MS);
			thread(consumer, false);
		}

	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}