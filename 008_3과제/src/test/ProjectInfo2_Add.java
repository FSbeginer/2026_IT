package test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ProjectInfo2_Add {
	public static void main(String[] args) throws Exception {
		add(13,
			new Object[][] {{"128", 50000}, {"256", 50000}, {"512", 50000}, {"1024", 50000}},
			new Object[][] {{"SKT", 1250000}},
			12);
	}

	public static Map<String, Object> getInfos(int pno) throws Exception {
		String json = read();
		ScriptEngine engine = engine();

		List<Map<String, Object>> projects = (List<Map<String, Object>>) engine
				.eval("Java.asJSONCompatible(" + json + ")");

		for (Map<String, Object> project : projects) {
			int number = (int) project.get("pno");
			if (number == pno) {
				return project;
			}
		}

		return null;
	}

	public static void add(int pno, Object[][] capacities,
			Object[][] items, int... installments) throws Exception {
		ScriptEngine engine = engine();
		engine.put("json", read());
		engine.put("pno", pno);
		engine.put("capacities", capacities);
		engine.put("items", items);
		engine.put("installments", installments);
		engine.eval("var data=JSON.parse(json)");

		if ((boolean) engine.eval("data.some(function(p){return p.pno==pno})"))
			throw new IllegalArgumentException("이미 존재하는 pno입니다: " + pno);

		String result = (String) engine.eval(
				"data.push({pno:pno," +
				"capacities:Java.from(capacities).map(function(x){return {value:String(x[0]),price:Number(x[1])}})," +
				"items:Java.from(items).map(function(x){return {type:String(x[0]),price:Number(x[1])}})," +
				"installments:Java.from(installments).map(function(x){return {month:Number(x)}})});" +
				"JSON.stringify(data,null,2)");

		Files.writeString(Paths.get("./datafiles/project.json"), result + System.lineSeparator());
	}

	public static List<Map<String, Object>> getCapaties(int pno) throws Exception {
		return (List<Map<String, Object>>) getInfos(pno).get("capacities");
	}

	public static List<Map<String, Object>> getItems(int pno) throws Exception {
		return (List<Map<String, Object>>) getInfos(pno).get("items");
	}

	public static List<Map<String, Object>> getInstallments(int pno) throws Exception {
		return (List<Map<String, Object>>) getInfos(pno).get("installments");
	}

	public static int getPrice(int pno) throws Exception {
		List<Map<String, Object>> items = getItems(pno);
		List<Integer> prices = new ArrayList<Integer>();
		for (var item : items) {
			prices.add((Integer) item.get("price"));
		}
		return (int) ((double) prices.stream().mapToInt(x -> x).sum() / prices.stream().count());
	}

	static String read() throws Exception {
		return Files.readString(Paths.get("./datafiles/project.json"))
				.replaceAll(",(?=\\s*[}\\]])", "")
				.replaceAll("}(?=\\s*\\{)", "},");
	}

	static ScriptEngine engine() {
		return new ScriptEngineManager().getEngineByName("javascript");
	}
}
