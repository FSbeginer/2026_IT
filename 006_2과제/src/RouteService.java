import java.beans.Beans;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class RouteService {
	public static List<Station> stations;
	static Map<Station, List<RouteNode>> graph;

	static {
		try {
			var rs = DB.res("select * from station order by sno");
			stations = new ArrayList<Station>();
			while (rs.next()) {
				var station = new Station(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4) / 2,
						rs.getInt(5) / 2);
				stations.add(station);
			}

			graph = new HashMap<>();
			var lines = stations.stream().collect(Collectors.groupingBy(x -> x.line.subSequence(0, 2)));
			var names = stations.stream().collect(Collectors.groupingBy(x -> x.name));

			for (var station : stations) {
				graph.put(station, new ArrayList<>());
			}
			for (var line : lines.values()) {
				for (int i = 0; i < line.size() - 1; i++) {
					var from = line.get(i);
					var to = line.get(i + 1);
					addEdge(from, to, 1, Math.hypot(from.x - to.x, from.y - to.y));
				}
			}
			for (var sameNameStations : names.values()) {
				for (int i = 0; i < sameNameStations.size(); i++) {
					for (int j = i + 1; j < sameNameStations.size(); j++) {
						addEdge(sameNameStations.get(i), sameNameStations.get(j), 0, 0);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static RouteInfo getRouteInfo(Station start, Station end) {
		PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparingDouble(x -> x.dist));
		Map<Station, RouteNode> infos = new HashMap<>();
		Map<Station, Station> prev = new HashMap<>();

		var startNode = new RouteNode(start, 0, 0);
		infos.put(start, startNode);
		queue.add(startNode);
		while (!queue.isEmpty()) {
			var current = queue.poll();
			if (current.dist > infos.get(current.station).dist)
				continue;
			if (current.station.equals(end))
				break;

			for (var edge : graph.get(current.station)) {
				double nextDistance = current.dist + edge.dist;
				var savedNode = infos.get(edge.station);
				if (savedNode == null || nextDistance < savedNode.dist) {
					var nextNode = new RouteNode(edge.station, current.cost + edge.cost, nextDistance);
					infos.put(edge.station, nextNode);
					queue.add(nextNode);
					prev.put(edge.station, current.station);
				}
			}
		}

		List<Station> route = new ArrayList<>();
		var station = end;
		while (station != null) {
			route.add(station);
			station = prev.get(station);
		}
		Collections.reverse(route);

		var info = infos.get(end);
		return new RouteInfo(route, info.cost, info.dist);
	}

	private static void addEdge(Station from, Station to, int cost, double dist) {
		graph.get(from).add(new RouteNode(to, cost, dist));
		graph.get(to).add(new RouteNode(from, cost, dist));
	}

	private static class RouteNode {
		Station station;
		int cost;
		double dist;

		RouteNode(Station station, int cost, double dist) {
			this.station = station;
			this.cost = cost;
			this.dist = dist;
		}
	}
}

class Station {
	int sno;
	String name, line;
	int x, y;

	public Station(int sno, String name, String line, int x, int y) {
		this.sno = sno;
		this.name = name;
		this.line = line;
		this.x = x;
		this.y = y;
	}
}

class RouteInfo {
	List<Station> route;
	int cost;
	double dist;

	public RouteInfo(List<Station> route, int cost, double dist) {
		this.route = route;
		this.cost = cost;
		this.dist = dist;
	}
}
