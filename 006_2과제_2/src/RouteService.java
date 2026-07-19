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
	public static Map<Station, List<NodeInfo>> graph; // 지도
	public static List<Station> stations; // 역 정보s
	static {
		try {
			var rs = DB.res("select * from station order by sno");
			stations = new ArrayList<Station>();
			
			while(rs.next())
				stations.add(new Station(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)/2, rs.getInt(5)/2));
			
			var lines = stations.stream().collect(Collectors.groupingBy(x->x.line.substring(0,2)));
			var sameNames = stations.stream().collect(Collectors.groupingBy(x->x.name));
			
			graph = new HashMap<>();
			for (Station station : stations) {
				graph.put(station, new ArrayList<>());
			}
			
			for (var line : lines.values()) { // 1호선, 2호선, 7호선
				for (int i = 0; i < line.size()-1; i++) { 
					var f = line.get(i);
					var t = line.get(i+1);
					addEdge(f,t, 1, Math.hypot(f.x-t.x, f.y-t.y));
				}
			}
			for (var sameName : sameNames.values()) {
				for (int i = 0; i < sameName.size(); i++) {
					for (int j = i + 1; j < sameName.size(); j++) {
						addEdge(sameName.get(i), sameName.get(j), 0, 0);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 다익스트라
	public static RouteInfo findRoute(Station start, Station end) {
		PriorityQueue<NodeInfo> queue = new PriorityQueue<>(Comparator.comparingDouble(x->x.dist));
		Map<Station, NodeInfo> infos = new HashMap<Station, NodeInfo>(); // dist[] 와 동일 key : station까지의 누적 거리/구간 정보
		Map<Station, Station> prev = new HashMap<Station, Station>(); // 지나온 역
		
		infos.put(start, new NodeInfo(start, 0, 0));
		queue.add(infos.get(start));
		while(!queue.isEmpty()) {
			var cur = queue.poll();
			if(cur.dist>infos.get(cur.st).dist) continue;  // 오래된 값 버리기
			if(cur.st.equals(end)) break;
			for (var next : graph.get(cur.st)) {
				double nd = cur.dist + next.dist;
				int nc = cur.cost + next.cost;
				var info = infos.get(next.st);
				if(info==null || nd < info.dist) {
					infos.put(next.st, new NodeInfo(next.st, nc, nd));
					prev.put(next.st, cur.st);
					queue.add(new NodeInfo(next.st, nc, nd));
				}
			}
		}
		
		// 경로 역추적
		List<Station> route = new ArrayList<Station>();
		var p = end;
		while(p!=null) {
			route.add(p);
			p = prev.get(p);
		}
		Collections.reverse(route);
		return new RouteInfo(route, infos.get(end).cost, infos.get(end).dist);
	}
	
	private static void addEdge(Station f, Station t, int cost, double dist) {
		graph.get(f).add(new NodeInfo(t, cost, dist));
		graph.get(t).add(new NodeInfo(f, cost, dist));
	}
}
class NodeInfo {
	Station st;		/// 현재 / 다음으로 갈 역
	int cost; 		/// 역 갯수 / 여태까지 지나온 역 갯수
	double dist; 	/// 역과 역 사이 거리 / 총 지나온 거리
	public NodeInfo(Station st, int cost, double dist) {
		super();
		this.st = st;
		this.cost = cost;
		this.dist = dist;
	}
}
class RouteInfo {
	List<Station> route;
	int cost;
	double dist;
	public RouteInfo(List<Station> route, int cost, double dist) {
		super();
		this.route = route;
		this.cost = cost;
		this.dist = dist;
	}
}
