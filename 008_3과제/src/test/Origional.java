package test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Origional {
	public static Map<String, Object> getInfos(int pno) throws Exception {
        String json = Files.readString(Paths.get("./datafiles/project.json"));

        json = json.replaceAll("\\}\\s*\\{", "},{");

        ScriptEngine engine =
                new ScriptEngineManager().getEngineByName("javascript");

        List<Map<String, Object>> projects =(List<Map<String, Object>>) engine.eval("Java.asJSONCompatible(" + json + ")");

        for (Map<String, Object> project : projects) {
            int number = (int) project.get("pno");
            if (number == pno) {
                return project;
            }
        }

        return null;
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
		List<Integer> prices=  new ArrayList<Integer>();
		for (var item : items) {
			prices.add((Integer) item.get("price"));
		}
		return (int) ((double)prices.stream().mapToInt(x->x).sum()/prices.stream().count());
	}
}

