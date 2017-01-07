package com.byporti;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Location player;
	private Location wumpus;
	private Location gold;
	private List<Location> pits;

	// Oyunun standart olarak boyutu 4x4 olacak.
	private static final int size = 4;

	public Board() {
		pits = new ArrayList<Location>();
	}

	// Bloklara objelerin eklenmesi.
	public void addPlayer() {
		player = new Location(1, 1);
	}

	public void addGold(Location l) {
		gold = new Location(l.getX(), l.getY());
	}

	public void addPit(Location l) {
		pits.add(new Location(l.getX(), l.getY()));
	}

	public void addWumpus(Location l) {
		wumpus = new Location(l.getX(), l.getY());
	}

	// Locationa gore
	public boolean hasPlayer(Location l) {
		if (player.getX() == l.getX() && player.getY() == l.getY()) {
			return true;
		}
		return false;
	}

	public boolean hasGold(Location l) {
		if (gold.getX() == l.getX() && gold.getY() == l.getY()) {
			return true;
		}
		return false;
	}

	public boolean hasPit(Location l) {

		for (Location pitLocation : pits) {
			if (pitLocation.getX() == l.getX() && pitLocation.getY() == l.getY()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasWumpus(Location l) {
		if (wumpus.getX() == l.getX() && wumpus.getY() == l.getY()) {
			return true;
		}
		return false;
	}

	// GENERATE WORLD
	public Game generateWorld() {
		Game game = new Game(size);
		game.addWumpus(new Location(wumpus.getX(), wumpus.getY()));
		game.addGold(new Location(gold.getX(), gold.getY()));

		for (int i = 0; i < pits.size(); i++) {
			game.addPit(new Location(pits.get(i).getX(), pits.get(i).getY()));
		}
		return game;
	}

	// Getter and setter---------------------
	public Location getWumpus() {
		return wumpus;
	}

	public void setWumpus(Location wumpus) {
		this.wumpus = wumpus;
	}

	public Location getGold() {
		return gold;
	}

	public void setGold(Location gold) {
		this.gold = gold;
	}

	public List<Location> getPits() {
		return pits;
	}

	public void setPits(List<Location> pits) {
		this.pits = pits;
	}

	public int getSize() {
		return size;
	}

	// To String method.
	@Override
	public String toString() {
		return "Map [wumpus=" + wumpus + ", gold=" + gold + ", pits=" + pits + ", size=" + size + "]";
	}

}
