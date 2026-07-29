// UPDATED_KST: 2026-07-22 21:26:27
package test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public final class ProjectInfo3_Add {
    private static final Path FILE = Path.of("./datafiles/project.json");

    public static void main(String[] args) throws Exception {
    	ProjectInfo3_Add.add(13, 
    					new Object[][] {{"128",50000}, {"256",50000}, {"512",50000}, {"1024",50000}},
    					new Object[][] {{"SKT",1250000}},
    					12);
	}
    
    static Map<String, Object> get(int pno) throws Exception {
        ScriptEngine js = engine();
        js.put("json", read());
        js.put("pno", pno);

        return (Map<String, Object>) js.eval(
                "var a=JSON.parse(json), r=null;" 		+
                "for(var i=0;i<a.length;i++)" 			+ 
                "   if(a[i].pno==pno) {r=a[i];break;}" 	+
                "Java.asJSONCompatible(r)"
        );
    }

    static void add(int pno, Object[][] capacities,
                           Object[][] items, int... installments) throws Exception {
        ScriptEngine js = engine();
        js.put("json", read());
        js.put("pno", pno);
        js.put("capacities", capacities);
        js.put("items", items);
        js.put("installments", installments);
        js.eval("var data=JSON.parse(json)");

        if ((boolean) js.eval("data.some( function(p) {return p.pno==pno} )"))
            throw new IllegalArgumentException("이미 존재하는 pno입니다: " + pno);

        String result = (String) js.eval(
                "data.push({pno:pno, " 												+
                "           capacities:Java.from(capacities).map(function(x){" 		+ 
                "                                                             return {value:String(x[0]), price:Number(x[1])}" +
                "                                                           })," 	+
                "           items:Java.from(items).map(function(x){" 				+
                "                                                   return {type:String(x[0]),price:Number(x[1])}" + 
                "                                                 })," 				+
                "           installments:Java.from(installments).map(function(x){" 	+
                "                                                   return {month:Number(x)}" +
                "                                                 })});" 			+
                "JSON.stringify(data,null,2)"
        );

        Files.writeString(FILE, result + System.lineSeparator());
    }

	static String read() throws Exception {
	    return Files.readString(FILE).replaceAll("}(?=\\s*\\{)", "},");
	}

    static ScriptEngine engine() {
        ScriptEngine js = new ScriptEngineManager().getEngineByName("nashorn");
        return js;
    }
}
