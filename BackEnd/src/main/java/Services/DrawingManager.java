package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import Model.*;

public class DrawingManager {

	static DrawingManager obj = new DrawingManager();

	ArrayList<IShape> shapes = new ArrayList<>();

	ShapeFactory shapeFactory = new ShapeFactory();

	Deque<ArrayList<IShape>> undo = new LinkedList<>();

	Deque<ArrayList<IShape>> redo = new LinkedList<>();

	Deque<Integer> undoId = new LinkedList<>();

	Deque<Integer> redoId = new LinkedList<>();
	
	int counter =0 ; 

	public void createShape(String name, double[] array, int id) throws CloneNotSupportedException {
		if (shapes.size() == 0) {
			addUndo(new ArrayList<>());
		}
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
		redo.clear();
		redoId.clear();
	}

	public void modify(double[] array, int id) throws CloneNotSupportedException {

		for (int i = 0; i < this.shapes.size(); i++) {
			if (this.shapes.get(i).getId() == id) {
				shapes.get(i).setDimAndCoord(array);
				// setIndexOfModifird(id);
				ArrayList<IShape> temp = new ArrayList<>();
				Iterator<IShape> iterator = shapes.iterator();
				while (iterator.hasNext()) {
					temp.add((IShape) iterator.next().clone());
				}
				addUndo(temp);
				addUndoId(id);
				redo.clear();
				redoId.clear();
			}
		}

	}

	public void delete(int id) throws CloneNotSupportedException {
		for (int i = 0; i < this.shapes.size(); i++) {
			if (this.shapes.get(i).getId() == id) {
				shapes.remove(i);
				// setDeleteIndex(id);
				ArrayList<IShape> temp = new ArrayList<>();
				Iterator<IShape> iterator = shapes.iterator();
				while (iterator.hasNext()) {
					temp.add((IShape) iterator.next().clone());
				}
				addUndo(temp);
				addUndoId(id);
				redo.clear();
				redoId.clear();
			}
		}

	}

	public String show() {
		String res = " ";
		for (int i = 0; i < shapes.size(); i++) {
			res += Arrays.toString(shapes.get(i).getDimAndCoord());
		}
		return res;
	}

	public String show1() {
		String res = " ";
		res += String.valueOf(undoId.peekLast());
		res += ",";
		res += String.valueOf(undoId.size());
		res += ",";
		res += String.valueOf(undo.size());
		res += ",";

		return res;
	}

	public String show2() {
		String res = " ";
		res += String.valueOf(redoId.peekLast());
		res += ",";
		res += String.valueOf(redoId.size());
		res += ",";
		res += String.valueOf(redo.size());
		res += ",";

		return res;
	}

	private void addUndoId(int id) {
		if (undoId.size() <= 5) {

			undoId.add(id);
		} else {
			undoId.removeFirst();
			undoId.add(id);
		}
	}

	private void addUndo(ArrayList<IShape> temp) {
		if (undo.size() <= 5) {
			undo.add(temp);

		} else {
			System.out.println(undo.removeFirst().size());
			undo.add(temp);
		}
	}


	public String undoMethod() throws CloneNotSupportedException {

		if (!undo.isEmpty()) {
			redo.add(undo.peekLast());
			undo.removeLast();
			counter ++;
		}
		if (!undo.isEmpty()) {
			// reset
			if (undo.peekLast().size() == 0) {
				shapes.clear();
				redo.add(undo.peekLast());
				undo.removeLast();
				return "00";
			}
			// if shape drag or resize
			else if (shapes.size() == undo.peekLast().size()) {

				String res = "";
				res += "modify";
				res += ",";
				res += String.valueOf(undoId.peekLast());
				res += ",";

				for (int i = 0; i < this.shapes.size(); i++) {
					if (this.shapes.get(i).getId() == undoId.peekLast()) {
						for (int j = 0; j < undo.peekLast().get(i).getDimAndCoord().length; j++) {
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
			// if shape draw
			else if (shapes.size() > undo.peekLast().size()) {
				int in = shapes.get(shapes.size() - 1).getId();
				shapes.remove(shapes.size() - 1);
				return String.valueOf(in);
			}
			// if shape deleted
			else {

				String res = "";
				res += "delete";
				res += ",";

				for (int i = 0; i < this.undo.peekLast().size(); i++) {

					if (this.undo.peekLast().get(i).getId() == undoId.peekLast()) {

						shapes.add(i, (IShape) undo.peekLast().get(i).clone());
						res += undo.peekLast().get(i).getClass().getName();

						res += ",";

						if (i != 0) {
							res += String.valueOf(this.undo.peekLast().get(i - 1).getId());
							res += ",";
						} else {
							res += String.valueOf(this.undo.peekLast().get(i).getId());
							res += ",";
						}
						res += String.valueOf(undoId.peekLast());
						res += ",";

						for (int j = 0; j < undo.peekLast().get(i).getDimAndCoord().length; j++) {
							res += String.valueOf((undo.peekLast().get(i).getDimAndCoord())[j]);
							res += ",";
						}

					}

				}
				redoId.add(undoId.peekLast());
				undoId.removeLast();
				return res;

			}
		} else {
			return "null";
		}

	}

	public String redoMethod() throws CloneNotSupportedException {

		if (!redo.isEmpty()) {
			if (redo.peekLast().size() == 0) {
				undo.add(redo.peekLast());
				redo.removeLast();
			}
			if(counter == 6){
				undo.add(redo.peekLast());
				redo.removeLast();
				counter =0;

			}
			
			// if drag and drop then undo
			if (shapes.size() == redo.peekLast().size()) {

				String res = "";

				res += String.valueOf(redoId.peekLast());
				res += ",";

				for (int i = 0; i < this.shapes.size(); i++) {
					if (this.shapes.get(i).getId() == redoId.peekLast()) {
						for (int j = 0; j < redo.peekLast().get(i).getDimAndCoord().length; j++) {
							res += String.valueOf((redo.peekLast().get(i).getDimAndCoord())[j]);
							res += ",";
						}
						shapes.get(i).setDimAndCoord(redo.peekLast().get(i).getDimAndCoord().clone());
					}

				}
				undoId.add(redoId.peekLast());
				redoId.removeLast();
				undo.add(redo.peekLast());
				redo.removeLast();
				return res;

			}
			// delete then undo
			else if (shapes.size() > redo.peekLast().size()) {
				for (int i = 0; i < this.shapes.size(); i++) {
					if (this.shapes.get(i).getId() == redoId.peekLast()) {
						shapes.remove(i);
						undoId.add(redoId.peekLast());
						redoId.removeLast();
						undo.add(redo.peekLast());
						redo.removeLast();
						return String.valueOf(undoId.peekLast());
					}
				}
				return null;
			}
			// draw shape then undo
			else {

				String res = "";
				res += "add";
				res += ",";
				res += redo.peekLast().get(redo.peekLast().size() - 1).getClass().getName();
				res += ",";
				shapes.add((IShape) redo.peekLast().get(redo.peekLast().size() - 1).clone());
				res += String.valueOf((redo.peekLast().get(redo.peekLast().size() - 1).getId()));
				res += ",";
				for (int j = 0; j < redo.peekLast().get(redo.peekLast().size() - 1).getDimAndCoord().length; j++) {
					res += String.valueOf((redo.peekLast().get(redo.peekLast().size() - 1).getDimAndCoord())[j]);
					res += ",";
				}

				undo.add(redo.peekLast());
				redo.removeLast();
				return res;
			}
		} else {
			return "null";
		}

	}

	public ArrayList<IShape> getshapes(){
		return this.shapes;
	}
	
	public static DrawingManager getInstance() {
		return obj;
	}

}
