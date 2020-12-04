package Model;

public class Rectangle extends Shape {

	private double[] rectDimAndCoord = new double[4];
	private String className;

	@Override
	public void setDimAndCoord(double[] dimAndCoord) {
		this.rectDimAndCoord = dimAndCoord;
	}

	@Override
	public double[] getDimAndCoord() {
		return this.rectDimAndCoord;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final IShape Rectangle = new Rectangle();
		Rectangle.setBorderColor(getBorderColor());
		Rectangle.setFillColor(getFillColor());
		Rectangle.setDimAndCoord(getDimAndCoord());
		Rectangle.setId(getId());
		return Rectangle;
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
