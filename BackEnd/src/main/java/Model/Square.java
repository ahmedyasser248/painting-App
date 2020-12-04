package Model;

public class Square extends Shape {

	private double[] sqrDimAndCoord = new double[3];
	public String className ; 

	@Override
	public void setDimAndCoord(double[] dimAndCoord) {
		this.sqrDimAndCoord = dimAndCoord;
	}

	@Override
	public double[] getDimAndCoord() {
		return this.sqrDimAndCoord;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final IShape Square = new Square();
		Square.setBorderColor(getBorderColor());
		Square.setFillColor(getFillColor());
		Square.setDimAndCoord(getDimAndCoord());
		Square.setId(getId());
		return Square;
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
