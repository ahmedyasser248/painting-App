package Model;

public class Line extends Shape {
    private double[] LineDimAndCoord = new double[4];
    private String className;

    @Override
    public void setDimAndCoord(double[] dimAndCoord) {
        this.LineDimAndCoord = dimAndCoord;
    }

    @Override
    public double[] getDimAndCoord() {
        return this.LineDimAndCoord;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final IShape Line = new Line();
        Line.setBorderColor(getBorderColor());
        Line.setFillColor(getFillColor());
        Line.setDimAndCoord(getDimAndCoord());
        Line.setId(getId());
        return Line;
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
