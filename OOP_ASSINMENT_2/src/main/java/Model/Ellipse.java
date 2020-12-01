package Model;

public class Ellipse extends Shape {
    private double[] ellipseDimAndCoord = new double[4] ;
	

	@Override
	public void setDimAndCoord(double [] dimAndCoord) {
		this.ellipseDimAndCoord=dimAndCoord;
	}

	@Override
	public double [] getDimAndCoord() {
		return this.ellipseDimAndCoord;
	}
    @Override
    public Object clone() throws CloneNotSupportedException{
		final IShape Ellipse = new Ellipse();
		Ellipse.setBorderColor(getBorderColor());
		Ellipse.setFillColor(getFillColor());
		Ellipse.setDimAndCoord(getDimAndCoord());
		Ellipse.setId(getId());
		return Ellipse;
	}

}
