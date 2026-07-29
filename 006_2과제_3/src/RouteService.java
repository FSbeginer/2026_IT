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
	public static List<Station> stations = new ArrayList<Station>();
	public static Map<Station, List<Edge>> graph = new HashMap<Station, List<Edge>>();
	static {
		try {
			var rs = DB.res("select * from station ");
			while(rs.next()) {
				stations.add(new Station(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)/2, rs.getInt(5)/2));
			}
			for (int i = 0; i < stations.size(); i++) {
				graph.put(stations.get(i), new ArrayList<Edge>());
			}
			
			var lines = stations.stream().collect(Collectors.groupingBy(x->x.line.subSequence(0, 2)));
			var sameNames = stations.stream().collect(Collectors.groupingBy(x->x.name));
			for (var line: lines.values()) {
				for (int i = 0; i < line.size()-1; i++) {
					var f = line.get(i);
					var t = line.get(i+1);
					addEdge(f,t,1,Math.hypot(f.x-t.x, f.y-t.y));
				}
			}
			for (var sameName : sameNames.values()) {
				for (int i = 0; i < sameName.size(); i++) {
					for (int j = i+1; j < sameName.size(); j++) {
						var f = sameName.get(i);
						var t = sameName.get(j);
						addEdge(f, t, 0, 0);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static RouteInfo getRouteInfo(Station start, Station end) {
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>(Comparator.comparingDouble(x->x.dist));
		Map<Station, Edge> infos = new HashMap<Station, Edge>();
		Map<Station, Station> prev = new HashMap<Station, Station>();
		
		infos.put(start, new Edge(start, 0, 0));
		queue.add(infos.get(start));
		
		while(!queue.isEmpty()) {
			var current = queue.poll();
			if(current.dist > infos.get(current.st).dist) continue;
			if(current.st==end) break;
			for (var edge : graph.get(current.st)) {
				int nc = current.cost + edge.cost;
				double nd = current.dist + edge.dist;
				if(infos.get(edge.st)==null||infos.get(edge.st).dist > nd) {
					var ed = new Edge(edge.st, nc, nd);
					infos.put(edge.st, ed);
					queue.add(ed);
					prev.put(edge.st, current.st);
				}
			}
		}
		
		List<Station> route = new ArrayList<Station>();
		var p = end;
		while(p!=null) {
			route.add(p);
			p = prev.get(p);
		}
		Collections.reverse(route);
		return new RouteInfo(route, infos.get(end).cost, infos.get(end).dist);
	}
	
	private static void addEdge(Station f, Station t, int i, double hypot) {
		graph.get(f).add(new Edge(t, i, hypot));
		graph.get(t).add(new Edge(f, i, hypot));
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
class Edge{
	Station st;
	int cost;
	double dist;
	public Edge(Station st, int cost, double dist) {
		this.st = st;
		this.cost = cost;
		this.dist = dist;
	}
}
