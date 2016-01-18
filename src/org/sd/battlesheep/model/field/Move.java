package org.sd.battlesheep.model.field;

import java.io.Serializable;
import java.util.List;

//FIXME!
@SuppressWarnings("serial")
public class Move implements Serializable{
	
	private String target;
	private int x;
	private int y;
	private boolean hit;
	//FIXME!
	private List<String> crashedOpponents;
	
	public Move(String target, int x, int y, boolean hit) {
		this.target=target;
		this.x=x;
		this.y=y;
		this.hit=hit;
	}
	
}