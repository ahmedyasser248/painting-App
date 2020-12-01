package Main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import Services.DrawingManager;



@SpringBootApplication
@CrossOrigin
@RestController
@RequestMapping
public class OopAssinment2Application {
	
	public static void main(String[] args) {
		SpringApplication.run(OopAssinment2Application.class, args);
	}

	
	
	
	DrawingManager draw = DrawingManager.getInstance();

	@GetMapping("/c")
	public String addSquare(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "length") String z,@RequestParam(name = "id") String i)
			throws CloneNotSupportedException {
		double [] array = new double[3];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		draw.createShape("Square", array,Integer.parseInt(i));
		return draw.show1()+","+draw.show();
	}

	@GetMapping("/c1")
	public String addRectangle(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "width") String z,@RequestParam(name = "height") String w,@RequestParam(name = "id") String i)
			throws CloneNotSupportedException {
		double [] array = new double[4];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		array[3]=Double.parseDouble(w);
		draw.createShape("Rectangle", array,Integer.parseInt(i));
		return draw.show1()+","+draw.show();
	}

	@GetMapping("/c2")
	public String addCcircle(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "rad") String z,@RequestParam(name = "id") String i)
			throws CloneNotSupportedException {
		
		double [] array = new double[3];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		
		draw.createShape("Circle", array,Integer.parseInt(i));
		return draw.show1()+","+draw.show();
	}

	@GetMapping("/c4")
	public String addEllipse(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "r1") String z,@RequestParam(name = "r2") String w,@RequestParam(name = "id") String i)
			throws CloneNotSupportedException {
		double [] array = new double[4];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		array[3]=Double.parseDouble(w);
		draw.createShape("Ellipse", array,Integer.parseInt(i));
		return draw.show1()+","+draw.show();

	}




	@GetMapping("/modifySquare")
	public String modifySquare(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "length") String z,@RequestParam(name = "id") String i,@RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double [] array = new double[3];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		
		draw.modify(array, Integer.parseInt(i),t);
		return draw.show1()+","+draw.show();
	}

	@GetMapping("/modifyRectangle")
	public String modifyRectangle(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "width") String z,@RequestParam(name = "height") String w,@RequestParam(name = "id") String i,@RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double [] array = new double[4];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		array[3]=Double.parseDouble(w);
		
		draw.modify(array, Integer.parseInt(i),t);
		return draw.show1()+","+draw.show();
	}

	@GetMapping("/modifyCircle")
	public String modifyCircle(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "rad") String z,@RequestParam(name = "id") String i,@RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		
		double [] array = new double[3];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		
		draw.modify(array, Integer.parseInt(i),t);
		return draw.show1()+","+draw.show();
	}

	@GetMapping("/modifyEllipse")
	public String modifyEllipse(@RequestParam(name = "x") String x,@RequestParam(name = "y") String y,@RequestParam(name = "r1") String z,@RequestParam(name = "r2") String w,@RequestParam(name = "id") String i,@RequestParam(name = "type") String t)
			throws CloneNotSupportedException {
		double [] array = new double[4];
		array[0]=(Double.parseDouble(x));
		array[1]=(Double.parseDouble(y));
		array[2]=(Double.parseDouble(z));
		array[3]=Double.parseDouble(w);
		draw.modify(array, Integer.parseInt(i),t);
		return draw.show1()+","+draw.show();

	}


	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") String i)
			throws CloneNotSupportedException {
				draw.delete(Integer.parseInt(i));
				return draw.show1()+","+draw.show();
		
	}








	@GetMapping("/undo")
	public String undo() throws CloneNotSupportedException {
		
		return draw.undoMethod();
	

	}
/*
	@GetMapping("/redo")
	public double[] redo(){
		
		return draw.redoMethod();

	}	
*/
	
}
