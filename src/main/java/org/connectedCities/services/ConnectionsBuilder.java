package org.connectedCities.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.connectedCities.domain.City;
import org.connectedCities.utility.Terminator;


public class ConnectionsBuilder {
	private static final String DELIMITER = ",";
	private BufferedReader bReader;
	private String cityA;
	private String cityB;

	public ConnectionsBuilder(final BufferedReader bReader, final String cityA,
			final String cityB) {
		this.bReader = bReader;
		this.cityA = cityA.toLowerCase();
		this.cityB = cityB.toLowerCase();
	}

	
	public ConcurrentHashMap<String, City> buildNodeConnections() {
		return processNode();
	}

	protected final ConcurrentHashMap<String, City> processNode() {
		String line;
		final ConcurrentHashMap<String, City> dataMap = new ConcurrentHashMap<String, City>();
		try {
			while ((line = bReader.readLine()) != null) {
				// Check if direct relation can be found
				String connectedCities[] = parseLine(line);
				saveConnection(dataMap, connectedCities);
			}
		} catch (IOException e) {
			Terminator.terminate(e.getMessage(), 1);
		}
		checkNodesExist(dataMap);
		return dataMap;
	}

	protected final void checkNodesExist(Map<String, City> data) {
		if (data == null || data.isEmpty() || !data.containsKey(this.cityA)
				|| !data.containsKey(cityB)) {
			Terminator.no();
		}
	}

	protected void saveConnection(final Map<String, City> dataMap,
			String[] connectedCities) {
		if (connectedCities.length == 2) {

			ArrayList<City> cities = new ArrayList<City>(2);
			City city;
			for (String cityName : connectedCities) {
				if ((city = dataMap.get(cityName)) == null) {
					city = new City(cityName);
					dataMap.put(cityName, city);
				}
				cities.add(city);
			}
			if (!cities.get(0).isConnectedWithNode(cities.get(1).getName())) {
				cities.get(0).addConnection(cities.get(1).getName(),
						cities.get(1));
			}
			if (!cities.get(1).isConnectedWithNode(cities.get(0).getName())) {
				cities.get(1).addConnection(cities.get(0).getName(),
						cities.get(0));
			}
		}
	}

	protected final String[] parseLine(final String line) {
		String connectedCities[] = getConnectedCities(line);
		checkDirectConnection(connectedCities);
		return connectedCities;
	}

	protected String[] getConnectedCities(String line) {
		return line.trim().toLowerCase().replace(DELIMITER + " ", DELIMITER)
				.split(DELIMITER);
	}

	
	protected void checkDirectConnection(String[] currentConnection) {
		String[] targetConnections = { this.cityA, this.cityB };
		Arrays.sort(currentConnection);
		Arrays.sort(targetConnections);
		if (currentConnection[0].equals(targetConnections[0])
				&& currentConnection[1].equals(targetConnections[1])) {
			Terminator.yes();
		}
	}
}
