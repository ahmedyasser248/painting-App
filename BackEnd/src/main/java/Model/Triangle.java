package Model;

public class Triangle extends Shape {
private double[] triangleDimAndCoord = new double[6];
	private String className;

	@Override
	public void setDimAndCoord(double[] dimAndCoord) {
		this.triangleDimAndCoord = dimAndCoord;
	}

	@Override
	public double[] getDimAndCoord() {
		return this.triangleDimAndCoord;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final IShape Triangle = new Triangle();
		Triangle.setBorderColor(getBorderColor());
		Triangle.setFillColor(getFillColor());
		Triangle.setDimAndCoord(getDimAndCoord());
		Triangle.setId(getId());
		return Triangle;
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
