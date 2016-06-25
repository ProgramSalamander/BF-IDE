package ui;

import java.util.ArrayList;

public class RedoUndoStack {
	private ArrayList<String> text = new ArrayList<String>();
	private int current=0;
	public void pop(String s){
		text.add(s);
		current++;
	}
	public String undoPush(){
		String string ="";
		if(current>0){
			current--;
			string =text.get(current);
		}
		return string;
	}
	public String redoPush(){
		String string="";
		if(current<text.size()-1){
			current++;
			string = text.get(current);
		}
		else {
			string = text.get(current);
		}
		
		return string;
	}
}
