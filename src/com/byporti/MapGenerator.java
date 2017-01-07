package com.byporti;

import java.util.Random;

public class MapGenerator {

	private Location location;

	// Standart olarak 2 verilmesi test asamasindan iyi olacaktir.
	private int pitSize;

	private Random random;
	private Board board;

	// Yapici metot.Harita olusturulurken pitSize a gore haritanin karmasikligi
	// belirlenecek.
	public MapGenerator(int pitSize) {
		this.pitSize = pitSize;

		random = new Random();
		board = new Board();
	}

	public Board generate() {
		// Player 1x1 de bulunuyor.Diger objeler eklenmeden once buna uyulmali.
		board.addPlayer();
		// ilk once gold eklenmeli.
		addGold();
		// Sonrasinda wumpus musait bir bolgeye eklenmeli.
		addWumpus();
		for (int i = 0; i < pitSize; i++) {
			// Son olarak wumpus ve player olmayan bir yere pit eklenmeli.
			addPit();
		}

		return board;
	}

	public void addGold() {
		while (true) {
			int x = random.nextInt(board.getSize()) + 1;
			int y = random.nextInt(board.getSize()) + 1;

			// Player secilmedigi surece bir yer belirle.Baþlangýçta harita
			// oluþturulacakken player 1x1 blogunda.
			if (!board.hasPlayer(new Location(1, 1))) {
				location = new Location(x, y);
				board.addGold(location);
				break;
			}
		}
	}

	public void addWumpus() {
		while (true) {
			int x = random.nextInt(board.getSize()) + 1;
			int y = random.nextInt(board.getSize()) + 1;
			// Player ve Gold secilmedigi surece wumpusa yer belirle.
			if (!board.hasPlayer(new Location(x, y)) && !board.hasGold(new Location(x, y))) {
				location = new Location(x, y);
				board.addWumpus(location);
				break;
			}
		}
	}

	public void addPit() {
		while (true) {
			int x = random.nextInt(board.getSize()) + 1;
			int y = random.nextInt(board.getSize()) + 1;
			// Player ve Gold secilmedigi surece wumpusa yer belirle.
			if (!board.hasPlayer(new Location(x, y)) && !board.hasGold(new Location(x, y))
					&& !board.hasWumpus(new Location(x, y)) && !board.hasPit(new Location(x, y))) {
				location = new Location(x, y);
				board.addPit(location);
				break;
			}
		}
	}

}
