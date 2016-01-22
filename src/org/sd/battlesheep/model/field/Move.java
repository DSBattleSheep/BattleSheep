package org.sd.battlesheep.model.field;

import java.io.Serializable;
import java.util.List;

//FIXME!
@SuppressWarnings("serial")
public class Move implements Serializable {

	private String target;

	private int x;

	private int y;

	private boolean hit;
	// FIXME!
	private List<String> crashedOpponents;
	


	public Move(String target, int x, int y, boolean hit) {
		this.target = target;
		this.x = x;
		this.y = y;
		this.hit = hit;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return the hit
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * @return the crashedOpponents
	 */
	public List<String> getCrashedOpponents() {
		return crashedOpponents;
	}

}