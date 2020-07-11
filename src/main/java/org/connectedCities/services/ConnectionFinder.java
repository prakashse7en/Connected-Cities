package org.connectedCities.services;

import java.util.concurrent.ConcurrentHashMap;

import org.connectedCities.domain.City;
import org.connectedCities.utility.Terminator;

public class ConnectionFinder {

	private String intialPosition;
	private String finalPosition;
	private ConcurrentHashMap<String, City> nodeMap;

	public ConnectionFinder(final String startPoint, final String endPoint,
			final ConcurrentHashMap<String, City> dataMap) {
		this.intialPosition = startPoint.toLowerCase();
		this.finalPosition = endPoint.toLowerCase();
		this.nodeMap = dataMap;
	}

	public void checkConnection() {
		if (isConnected()) {
			Terminator.yes();
		} else {
			Terminator.no();
		}
	}

	protected boolean isConnected() {
		City startCity = nodeMap.get(intialPosition);
		search(startCity);
		return false;
	}

	protected void search(City currentNode) {
		if (currentNode.getName().equals(finalPosition)) {
			Terminator.yes();
		}
		clearNode(currentNode);
		if (!currentNode.isVisited()) {
			currentNode.visited();
		} else {
			return;
		}

		for (City node : currentNode.getNodes().values()) {
			search(node);
		}
	}

	protected void clearNode(City A) {
		for (City city : A.getNodes().values()) {
			city.getNodes().remove(A.getName());
		}
	}

}
