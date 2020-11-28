package Services;

import Model.*;


public class ShapeFactory {
	
	public Shape createShape(String shapeName) {
		
		if(shapeName=="Rectangle") {
			return new Rectangle();
		}else if(shapeName=="Square") {
			return new Square();
		}else if(shapeName=="Circe") {
			return new Circle();
		}else {
			return null;
		}
		
	}
}
