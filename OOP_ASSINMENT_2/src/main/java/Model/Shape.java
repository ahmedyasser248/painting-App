package Model;

public abstract class Shape {
	
	protected java.awt.Point mainPoint;
	protected java.awt.Color BorderColor;
	protected java.awt.Color fillColor;
	
	public java.awt.Point getMainPoint() {
		return mainPoint;
	}
	public void setMainPoint(java.awt.Point mainPoint) {
		this.mainPoint = mainPoint;
	}
	public java.awt.Color getBorderColor() {
		return BorderColor;
	}
	public void setBorderColor(java.awt.Color borderColor) {
		BorderColor = borderColor;
	}
	public java.awt.Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(java.awt.Color fillColor) {
		this.fillColor = fillColor;
	}



}
