/*
 * Battlesheep is a funny remake of the famous Battleship game, developed
 * as a distributed system.
 * 
 * Copyright (C) 2016 - Giulio Biagini, Michele Corazza, Gianluca Iselli
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sd.battlesheep.model.field;

import java.io.Serializable;
import java.util.List;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public class Move implements Serializable {

	private static final long serialVersionUID = -2548231018360975595L;
	
	private String attacker;

	private String target;

	private int x;

	private int y;

	private boolean hit;

	private List<String> crashedOpponents;
	
	private int moveIndex;


	public Move(String attacker, String target, int x, int y, boolean hit, List<String> crashedList, int moveIndex) {
		this.attacker = attacker;
		this.target = target;
		this.x = x;
		this.y = y;
		this.hit = hit;
		this.crashedOpponents = crashedList;
		this.moveIndex=moveIndex;
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
	
	public int getMoveIndex() {
		return moveIndex;
	}
	
	/**
	 * @return the crashedOpponents
	 */
	public List<String> getCrashedOpponents() {
		return crashedOpponents;
	}

	/**
	 * @return the attacker
	 */
	public String getAttacker() {
		return attacker;
	}

}