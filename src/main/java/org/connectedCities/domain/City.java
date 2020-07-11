package org.connectedCities.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class City {
	private String name;
	private boolean visited;
	private Map<String, City> nodes;

	public City(final String name) {
		this.name = name;
		this.visited = false;
		nodes = new ConcurrentHashMap<String, City>();
	}

	public String getName() {
		return this.name;
	}

	
	public void addConnection(final String cityName, final City connection) {
		this.nodes.put(cityName, connection);
	}

	public Map<String, City> getNodes() {
		return this.nodes;
	}

	
	public boolean isConnectedWithNode(final String cityName) {
		if (nodes != null && !nodes.isEmpty()) {
			return nodes.containsKey(cityName);
		}
		return false;
	}

	/**
	 * Return if city was visited or not.
	 * 
	 * @return city status, wither it was visited before or not.
	 */
	public boolean isVisited() {
		return this.visited;
	}

	/**
	 * Change visit status of the city to true.
	 * */
	public void visited() {
		this.visited = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		City other = (City) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
