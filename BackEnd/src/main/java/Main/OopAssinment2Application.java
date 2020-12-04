package Main;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Model.Circle;
import Model.Ellipse;
import Model.IShape;
import Model.Rectangle;
import Model.Square;
import Model.Triangle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Services.DrawingManager;
import Services.Save;


@SpringBootApplication
@CrossOrigin
@RestController
@RequestMapping
public class OopAssinment2Application {

	public static void main(String[] args) {
		SpringApplication.run(OopAssinment2Application.class, args);
	}

	ArrayList<IShape> loaded= new ArrayList<>();

	DrawingManager draw = DrawingManager.getInstance();

	@GetMapping("/c")
	public String addSquare(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "length") String z, @RequestParam(name = "id") String i)
			throws CloneNotSupportedException {
		double[] array = new double[3];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		draw.createShape("Square", array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();
	}

	@GetMapping("/c1")
	public String addRectangle(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "width") String z, @RequestParam(name = "height") String w,
			@RequestParam(name = "id") String i) throws CloneNotSupportedException {
		double[] array = new double[4];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		draw.createShape("Rectangle", array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();
	}

	@GetMapping("/c2")
	public String addCcircle(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "rad") String z, @RequestParam(name = "id") String i)
			throws CloneNotSupportedException {

		double[] array = new double[3];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));

		draw.createShape("Circle", array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();
	}

	@GetMapping("/c3")
	public String addLine(@RequestParam(name = "p1x") String x, @RequestParam(name = "p1y") String y,
			@RequestParam(name = "p2x") String z, @RequestParam(name = "p2y") String w,
			@RequestParam(name = "id") String i) throws CloneNotSupportedException {
		double[] array = new double[4];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		draw.createShape("Line", array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}

	@GetMapping("/c4")
	public String addEllipse(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "r1") String z, @RequestParam(name = "r2") String w,
			@RequestParam(name = "id") String i) throws CloneNotSupportedException {
		double[] array = new double[4];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		draw.createShape("Ellipse", array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}

	@GetMapping("/c5")
	public String addTriangle(@RequestParam(name = "px1") String x, @RequestParam(name = "py1") String y,
			@RequestParam(name = "px2") String z, @RequestParam(name = "py2") String w,
			@RequestParam(name = "px3") String a, @RequestParam(name = "py3") String b,
			@RequestParam(name = "id") String i) throws CloneNotSupportedException {
		double[] array = new double[6];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		array[4] = (Double.parseDouble(a));
		array[5] = Double.parseDouble(b);
		draw.createShape("Triangle", array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}

	@GetMapping("/modifySquare")
	public String modifySquare(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "length") String z, @RequestParam(name = "id") String i,
			@RequestParam(name = "type") String t) throws CloneNotSupportedException {
		double[] array = new double[3];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));

		draw.modify(array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();
	}

	@GetMapping("/modifyRectangle")
	public String modifyRectangle(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "width") String z, @RequestParam(name = "height") String w,
			@RequestParam(name = "id") String i, @RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double[] array = new double[4];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);

		draw.modify(array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();
	}

	@GetMapping("/modifyCircle")
	public String modifyCircle(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "rad") String z, @RequestParam(name = "id") String i,
			@RequestParam(name = "type") String t) throws CloneNotSupportedException {

		double[] array = new double[3];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));

		draw.modify(array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();
	}

	@GetMapping("/modifyEllipse")
	public String modifyEllipse(@RequestParam(name = "x") String x, @RequestParam(name = "y") String y,
			@RequestParam(name = "r1") String z, @RequestParam(name = "r2") String w,
			@RequestParam(name = "id") String i, @RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double[] array = new double[4];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		draw.modify(array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}

	@GetMapping("/modifyTriangle")
	public String modifyTriangle(@RequestParam(name = "px1") String x, @RequestParam(name = "py1") String y,
	@RequestParam(name = "px2") String z, @RequestParam(name = "py2") String w,
	@RequestParam(name = "px3") String a, @RequestParam(name = "py3") String b,
			@RequestParam(name = "id") String i, @RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double[] array = new double[6];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		array[4] = (Double.parseDouble(a));
		array[5] = Double.parseDouble(b);
		draw.modify(array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}
	@GetMapping("/modifyLine")
	public String modifyLine(@RequestParam(name = "p1x") String x, @RequestParam(name = "p1y") String y,
			@RequestParam(name = "p2x") String z, @RequestParam(name = "p2y") String w,
			@RequestParam(name = "id") String i, @RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double[] array = new double[4];
		array[0] = (Double.parseDouble(x));
		array[1] = (Double.parseDouble(y));
		array[2] = (Double.parseDouble(z));
		array[3] = Double.parseDouble(w);
		draw.modify(array, Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}


	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") String i) throws CloneNotSupportedException {
		draw.delete(Integer.parseInt(i));
		return draw.show1() + "," + draw.show2() + "," + draw.show();

	}

	@GetMapping("/undo")
	public String undo() throws CloneNotSupportedException {

		return draw.undoMethod();

	}

	@GetMapping("/redo")
	public String redo() throws CloneNotSupportedException {

		return draw.redoMethod();

	}
	
	int j = 1;
	@GetMapping("/Save")
	public String save(@RequestParam String type) {
		String name = "Draw" + j + ".json";
		String home = System.getProperty("user.home");
		home = home + "/Downloads/" + name;
		ArrayList<IShape> ch = draw.getshapes();
		ObjectMapper mapper = new ObjectMapper();
		String jsonArray = null;
		File f = new File(home);
		try (FileWriter fw = new FileWriter(f, false)) {
			jsonArray = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ch);
			System.out.println(jsonArray);
			fw.write(jsonArray);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("worked");
			j++;
		}

		return name;

	}

@GetMapping("/LoadJson")
public JSONArray loadJson(@RequestParam String type){
	String separator = System.getProperty("file.separator");
		String home = System.getProperty("user.home");
		home = home +separator+"Downloads"+separator+type;
		ObjectMapper objmapper=new ObjectMapper();
		System.out.println(home);
		JSONParser jsonParser=new JSONParser();
		JSONArray shapess=null;
		ArrayList<IShape>test=new ArrayList<>();

		try{
			FileReader reader =new FileReader(home);
			Object obj =jsonParser.parse(reader);
			

			 shapess= (JSONArray)obj;
			 ArrayList<IShape> loaded = new ArrayList<>();
			 for(int i =0;i<shapess.size();i++){
				 JSONObject shp = (JSONObject) shapess.get(i);
				 //System.out.println(shp.get("className").toString());
				 if(shp.get("className").toString().equals("Model.Circle")){	
					IShape cir = new Circle();
					cir.setId(Integer.parseInt(shp.get("id").toString()));
					cir.setClassName(cir.getClass().getName());
					String str = shp.get("dimAndCoord").toString().substring(1, shp.get("dimAndCoord").toString().length()-2);
				 	String [] str1 = str.split(",");
				 	double [] array = new double[3];
				 	for(int j =0;j<str1.length;j++){
					array[j]=Double.parseDouble(str1[j]);
					}
					cir.setDimAndCoord(array);
					loaded.add(cir);
					
				 }
				 if(shp.get("className").toString().equals("Model.Square")){	
					IShape cir = new Square();
					cir.setId(Integer.parseInt(shp.get("id").toString()));
					cir.setClassName(cir.getClass().getName());
					String str = shp.get("dimAndCoord").toString().substring(1, shp.get("dimAndCoord").toString().length()-2);
				 	String [] str1 = str.split(",");
				 	double [] array = new double[3];
				 	for(int j =0;j<str1.length;j++){
					array[j]=Double.parseDouble(str1[j]);
					}
					cir.setDimAndCoord(array);
					loaded.add(cir);
					
				 }
				 if(shp.get("className").toString().equals("Model.Rectangle")){	
					IShape cir = new Rectangle();
					cir.setId(Integer.parseInt(shp.get("id").toString()));
					cir.setClassName(cir.getClass().getName());
					String str = shp.get("dimAndCoord").toString().substring(1, shp.get("dimAndCoord").toString().length()-2);
				 	String [] str1 = str.split(",");
				 	double [] array = new double[4];
				 	for(int j =0;j<str1.length;j++){
					array[j]=Double.parseDouble(str1[j]);
					}
					cir.setDimAndCoord(array);
					loaded.add(cir);
					
				 }
				 if(shp.get("className").toString().equals("Model.Line")){	
					IShape cir = new Model.Line();
					cir.setId(Integer.parseInt(shp.get("id").toString()));
					cir.setClassName(cir.getClass().getName());
					String str = shp.get("dimAndCoord").toString().substring(1, shp.get("dimAndCoord").toString().length()-2);
				 	String [] str1 = str.split(",");
				 	double [] array = new double[4];
				 	for(int j =0;j<str1.length;j++){
					array[j]=Double.parseDouble(str1[j]);
					}
					cir.setDimAndCoord(array);
					loaded.add(cir);
					
				 }
				 if(shp.get("className").toString().equals("Model.Ellipse")){	
					IShape cir = new Ellipse();
					cir.setId(Integer.parseInt(shp.get("id").toString()));
					cir.setClassName(cir.getClass().getName());
					String str = shp.get("dimAndCoord").toString().substring(1, shp.get("dimAndCoord").toString().length()-2);
				 	String [] str1 = str.split(",");
				 	double [] array = new double[4];
				 	for(int j =0;j<str1.length;j++){
					array[j]=Double.parseDouble(str1[j]);
					}
					cir.setDimAndCoord(array);
					loaded.add(cir);
					
				 }
				 
				 if(shp.get("className").toString().equals("Model.Triangle")){	
					IShape cir = new Triangle();
					cir.setId(Integer.parseInt(shp.get("id").toString()));
					cir.setClassName(cir.getClass().getName());
					String str = shp.get("dimAndCoord").toString().substring(1, shp.get("dimAndCoord").toString().length()-2);
				 	String [] str1 = str.split(",");
				 	double [] array = new double[6];
				 	for(int j =0;j<str1.length;j++){
					array[j]=Double.parseDouble(str1[j]);
					}
					cir.setDimAndCoord(array);
					loaded.add(cir);
					
				 }
				 
			 }
			 draw.loadSHapes(loaded);
			 draw.clearAllUndoAndRedo();
			 
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(shapess);
		return shapess;
}

public int i=1;
	@GetMapping("/SaveXml")
	public String saveXml(@RequestParam String type){
		ArrayList<IShape> ch = draw.getshapes();
		String name = "Draw"+i+".xml";
		String separator = System.getProperty("file.separator");
		String home = System.getProperty("user.home");
		home = home + separator + "Downloads"+separator+name;
		try{
			File f = new File(home);
			FileOutputStream fos = new FileOutputStream(f);
			i++;
			XMLEncoder enc = new XMLEncoder(fos);
			enc.writeObject(ch);
			enc.close();
			fos.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	
		
		return name;
	}

	public boolean load = false;

	@GetMapping("/LoadXml")
	public ArrayList<IShape> loadXml(@RequestParam String type) throws CloneNotSupportedException {
		load=true;
		String separator = System.getProperty("file.separator");
		String home = System.getProperty("user.home");
		home = home +separator+"Downloads"+separator+type;
		try{	
			FileInputStream fis = new FileInputStream(home);
				XMLDecoder dec = new XMLDecoder(fis);
				loaded = (ArrayList<IShape>) dec.readObject();
				draw.clearAllUndoAndRedo();
				draw.loadSHapes(loaded);
				dec.close();
				fis.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return loaded;
	}

}
