package Model;

public interface IShape {
    
    public void setClassName(String name);

    public String getClassName ();



    public void setDimAndCoord(double[] dimAndCoord);

    public double[] getDimAndCoord();

    public java.awt.Color getBorderColor();

    public void setBorderColor(java.awt.Color borderColor);

    public java.awt.Color getFillColor();

    public void setFillColor(java.awt.Color fillColor);

    public void setId(int id);

    public int getId();

    public abstract Object clone() throws CloneNotSupportedException;

}
