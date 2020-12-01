package Services;

import java.util.Deque;
import java.util.LinkedList;

import Model.*;

public class undoRedo {
    

    Deque<IShape> undo = new LinkedList<>();
	
	Deque<IShape> redo = new LinkedList<>();

    public void addUndo(IShape shape){
		if(undo.size()<10){
			undo.push(shape);
		}
		else{
			undo.removeFirst();
			undo.push(shape);
		}
    }
   
	public void undoMethod(){
		redo.push(undo.peekLast());
		undo.removeLast();
	}
    
}
