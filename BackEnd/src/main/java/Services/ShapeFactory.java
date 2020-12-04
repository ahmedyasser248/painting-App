package Services;

import Model.*;

public class ShapeFactory {

	public Shape createShape(String shapeName) {

		if (shapeName == "Rectangle") {
			return new Rectangle();
		} else if (shapeName == "Square") {
			return new Square();
		} else if (shapeName == "Circle") {
			return new Circle();
		} else if (shapeName == "Ellipse") {
			return new Ellipse();
		} else if (shapeName == "Line") {
			return new Line();
		} else if (shapeName == "Triangle") {
			return new Triangle();
		} else {
			return null;
		}

	}
}
