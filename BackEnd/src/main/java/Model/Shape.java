package Model;

public abstract class Shape implements IShape {

	protected java.awt.Color BorderColor;
	protected java.awt.Color fillColor;
	private int id;

	@Override
	public abstract void setDimAndCoord(double[] dimAndCoord);

	@Override
	public abstract double[] getDimAndCoord();

	@Override
	public java.awt.Color getBorderColor() {
		return BorderColor;
	}

	@Override
	public void setBorderColor(java.awt.Color borderColor) {
		BorderColor = borderColor;
	}

	@Override
	public java.awt.Color getFillColor() {
		return fillColor;
	}

	@Override
	public void setFillColor(java.awt.Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public abstract Object clone() throws CloneNotSupportedException;

}
