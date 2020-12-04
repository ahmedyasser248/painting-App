package Model;

public class Circle extends Shape {

	private double[] circleDimAndCoord = new double[3];
	private String className;

	@Override
	public void setDimAndCoord(double[] dimAndCoord) {
		this.circleDimAndCoord = dimAndCoord;
	}

	@Override
	public double[] getDimAndCoord() {
		return this.circleDimAndCoord;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final IShape Circle = new Circle();
		Circle.setBorderColor(getBorderColor());
		Circle.setFillColor(getFillColor());
		Circle.setDimAndCoord(getDimAndCoord());
		Circle.setId(getId());
		return Circle;
	}
	@Override
	public void setClassName(String name) {
		this.className = name;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

}
