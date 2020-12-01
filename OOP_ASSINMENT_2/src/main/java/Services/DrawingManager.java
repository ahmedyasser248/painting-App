package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import Model.*;

public class DrawingManager {

	static DrawingManager obj = new DrawingManager();

	private int indexOfModifird;

	private String typeOfModifying;

	private int deleteIndex;

	


	ArrayList<IShape> shapes = new ArrayList<>();

	ShapeFactory shapeFactory = new ShapeFactory();

	Deque<ArrayList<IShape>> undo = new LinkedList<>();

	Deque<ArrayList<IShape>> redo = new LinkedList<>();

	Deque <Integer> undoId = new LinkedList<>();

	Deque<Integer> redoId = new LinkedList<>();

	public void createShape(String name, double[] array,int id) throws CloneNotSupportedException {
		IShape shape = shapeFactory.createShape(name);
		shape.setDimAndCoord(array);
		shape.setId(id);
		shapes.add(shape);
		ArrayList<IShape> temp = new ArrayList<>();
		Iterator<IShape> iterator = shapes.iterator();
		while (iterator.hasNext()) {
			temp.add((IShape) iterator.next().clone());
		}
		addUndo(temp);
	}

	public void modify(double[] array,int id,String type) throws CloneNotSupportedException {
		
		for(int i =0 ;i<this.shapes.size();i++){
			if(this.shapes.get(i).getId()==id){
				shapes.get(i).setDimAndCoord(array);
				//setIndexOfModifird(id);
				setTypeOfModifying(type);
				ArrayList<IShape> temp = new ArrayList<>();
				Iterator<IShape> iterator = shapes.iterator();
				while (iterator.hasNext()) {
					temp.add((IShape) iterator.next().clone());
				}
				addUndo(temp);
				addUndoId(id);
				}
		}
	}

	public void delete(int id) throws CloneNotSupportedException {
		for(int i =0 ;i<this.shapes.size();i++){
			if(this.shapes.get(i).getId()==id){		
				shapes.remove(i);
				//setDeleteIndex(id);
				ArrayList<IShape> temp = new ArrayList<>();
				Iterator<IShape> iterator = shapes.iterator();
				while (iterator.hasNext()) {
					temp.add((IShape) iterator.next().clone());
				}
				addUndo(temp);
				addUndoId(id);
			}
		}
	}

	public String getTypeOfModifying() {
		return typeOfModifying;
	}

	public void setTypeOfModifying(String typeOfModifying) {
		this.typeOfModifying = typeOfModifying;
	}

	public int getIndexOfModifird() {
		return indexOfModifird;
	}

	public void setIndexOfModifird(int indexOfModifird) {
		this.indexOfModifird = indexOfModifird;
	}

	public int getDeleteIndex() {
		return deleteIndex;
	}

	public void setDeleteIndex(int deleteIndex) {
		this.deleteIndex = deleteIndex;
	}


	public String show() {
		String res = " ";
		for(int i =0;i<shapes.size();i++ ){
			res +=Arrays.toString( shapes.get(i).getDimAndCoord());
		}
		return res;
	}

	public String show1() {
		String res = " ";
			res += String.valueOf(undoId.peekLast());
			res += ",";
			res += String.valueOf(undoId.size());
		
		return res;
	}

	
	private void addUndoId(int id){
		if(undoId.size()<10){
		
			undoId.add(id);
		}
		else{
			undoId.removeFirst();
			undoId.add(id);
		}
    }
	private void addUndo(ArrayList<IShape> temp){
		if(undo.size()<10){
			undo.add(temp);
		
		}
		else{
			undo.removeFirst();
			undo.add(temp);
		}
    }
   
	public String undoMethod() throws CloneNotSupportedException {
		 
		if(redo.size()<10){
			redo.add(undo.peekLast());
		
		}
		else{
			redo.removeFirst();
			redo.add(undo.peekLast());
		}
	
		undo.removeLast();

		 if(shapes.size()==undo.peekLast().size()){
			
			String res ="";
			res += getTypeOfModifying();
			res+=",";
			res += String.valueOf(undoId.peekLast());
			res+=",";

			for(int i =0 ;i<this.shapes.size();i++){
				if(this.shapes.get(i).getId()==undoId.peekLast()){	
					for(int j=0;j<undo.peekLast().get(i).getDimAndCoord().length;j++){
						res += String.valueOf((undo.peekLast().get(i).getDimAndCoord())[j]);
						res += ",";
					}
					shapes.get(i).setDimAndCoord(undo.peekLast().get(i).getDimAndCoord().clone()); 
				}
				
			}		
			redoId.add(undoId.peekLast());
			undoId.removeLast();
			return res;
		}
		else if(shapes.size()>undo.peekLast().size()) {
			int in =shapes.get(shapes.size()-1).getId();
			shapes.remove(shapes.size()-1);
			return String.valueOf(in);
		}
		else {
			String res ="";
			res += "delete";
			res+=",";
			for(int i =0 ;i<this.undo.peekLast().size();i++){
				if(this.undo.peekLast().get(i).getId()==undoId.peekLast()){	
					shapes.add(i,undo.peekLast().get(i));
					res += undo.peekLast().get(undoId.peekLast()).getClass().getName();
					res+=",";
					res +=String.valueOf(this.undo.peekLast().get(i-1).getId());
					res+=",";
					res += undoId.peekLast();
					res+=",";
					for(int j=0;j<undo.peekLast().get(undoId.peekLast()).getDimAndCoord().length;j++){
						res += String.valueOf((undo.peekLast().get(undoId.peekLast()).getDimAndCoord())[j]);
						res += ",";
					}	
				}
			}
			redoId.add(undoId.peekLast());
			undoId.removeLast();
			return res;
		}

	
	}
/*
	public double[] redoMethod(){
		undo.push(redo.peekLast());
		shapes.add(redo.peekLast());
		redo.removeLast();
		return shapes.get(shapes.size()-1).getDimAndCoord();
		
	}
	*/
	public static DrawingManager getInstance(){
		return obj;
	}

}
