package org.connectedCities.manager;

import java.io.BufferedReader;
import java.util.concurrent.ConcurrentHashMap;

import org.connectedCities.domain.City;
import org.connectedCities.services.ConnectionFinder;
import org.connectedCities.services.ConnectionsBuilder;
import org.connectedCities.services.FileLoader;
import org.connectedCities.utility.Terminator;

public class Connected {
	public static void main(String[] args) {
		if (!isValidArgsLength(args)) {
			Terminator
					.terminate(
							"Input Error: Usage java Connected <filename> <cityname1> <cityname2>",
							1);
		}
		execute(args);
	}

	protected static void execute(String[] args) {
		final String dataFile = args[0];
		final String startCity = args[1].toLowerCase();
		final String endCity = args[2].toLowerCase();

		checkIdenticalCities(startCity, endCity);
		FileLoader fileLoader = new FileLoader();
		BufferedReader fileBufferReader = fileLoader.loadFile(dataFile);

		ConnectionsBuilder connectionBuilder = new ConnectionsBuilder(
				fileBufferReader, startCity, endCity);
		ConcurrentHashMap<String, City> nodeMap = connectionBuilder
				.buildNodeConnections();

		ConnectionFinder connectionFinder = new ConnectionFinder(startCity,
				endCity, nodeMap);
		connectionFinder.checkConnection();
	}

	
	protected static boolean isValidArgsLength(final String args[]) {
		if (args.length == 3) {
			for (String arg : args) {
				if (arg.trim().isEmpty()) {
					return false;
				}
			}
			return true;
		}

		return false;
	}
	
	
	protected static void checkIdenticalCities(final String cityA,final String cityB){
		if(cityA.trim().toLowerCase().equals(cityB.trim().toLowerCase())){
			Terminator.yes();
		}
	}
}
