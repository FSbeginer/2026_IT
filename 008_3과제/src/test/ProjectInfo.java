package test;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class ProjectInfo {
	static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

	public List<Project> readJson() throws Exception {
		String json = Files.readString(Paths.get("./project.json"));
		var list = (List<Map<String, Object>>) engine.eval("Java.asJSONCompatible(" + json + ")");

		var projects = new ArrayList<Project>();
		for (var m : list)
			projects.add(new Project(m));
		return projects;
	}
}

// �ڱ� �ʵ带 JS ��ü�� Ű�� �����Ѵ�. stringify �� keySet() ������� �д´�.
abstract class JsObj extends AbstractJSObject {
	public Set<String> keySet() {
		var keys = new LinkedHashSet<String>();
		for (Field f : getClass().getDeclaredFields())
			keys.add(f.getName());
		return keys;
	}

	public Object getMember(String name) {
		try {
			Field f = getClass().getDeclaredField(name);
			f.setAccessible(true);
			Object v = f.get(this);
			return v instanceof List ? new JsArr((List<?>) v) : v; // �� List �� �ѱ�� Ű�� �������
		} catch (Exception e) {
			return null;
		}
	}

	public String toString() {
		try {
			ProjectInfo.engine.put("m", this);
			return (String) ProjectInfo.engine.eval("JSON.stringify(m,null,2)");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

// �迭�� keySet() �� �ƴ϶� isArray() + length + getSlot() ���� ������.
class JsArr extends AbstractJSObject {
	List<?> list;

	JsArr(List<?> list) {
		this.list = list;
	}

	public boolean isArray() {
		return true;
	}

	public Object getSlot(int i) {
		return list.get(i);
	}

	public boolean hasSlot(int i) {
		return i >= 0 && i < list.size();
	}

	public Object getMember(String name) {
		return "length".equals(name) ? list.size() : null;
	}
}

class Project extends JsObj {
	int pno;
	List<Capacity> capacities = new ArrayList<>();
	List<Item> items = new ArrayList<>();
	List<Installment> installments = new ArrayList<>();

	Project(Map<String, Object> m) {
		pno = ((Number) m.get("pno")).intValue();
		for (var v : (List<Map<String, Object>>) m.get("capacities"))
			capacities.add(new Capacity(v));
		for (var v : (List<Map<String, Object>>) m.get("items"))
			items.add(new Item(v));
		for (var v : (List<Map<String, Object>>) m.get("installments"))
			installments.add(new Installment(v));
	}
}

class Capacity extends JsObj {
	String value;
	int price;

	Capacity(Map<String, Object> m) {
		value = (String) m.get("value");
		price = ((Number) m.get("price")).intValue();
	}

	Capacity(String value, int price) {
		this.value = value;
		this.price = price;
	}
}

class Item extends JsObj {
	String type;
	int price;

	Item(Map<String, Object> m) {
		type = (String) m.get("type");
		price = ((Number) m.get("price")).intValue();
	}

	Item(String type, int price) {
		this.type = type;
		this.price = price;
	}
}

class Installment extends JsObj {
	int month;

	Installment(Map<String, Object> m) {
		month = ((Number) m.get("month")).intValue();
	}

	Installment(int month) {
		this.month = month;
	}
}
