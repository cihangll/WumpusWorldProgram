package com.byporti;

public class Agent {

	private Game game;

	public Agent(Game game) {
		this.game = game;
	}

	public void doAction() {
		int playerX = game.getPlayerX();
		int playerY = game.getPlayerY();

		if (game.isGameOver()) {
			return;
		}

		if (game.hasGold(new Location(playerX, playerY))) {
			game.doAction(Game.A_GRAB);
			return;
		}
		if (game.hasPit(new Location(playerX, playerY))) {
			game.doAction(Game.A_CLIMB);
		}

		// Yer degistirme islemi.
		// Hiç bir islem yapamaz ise sag tarafa ilerlesin.
		game.setDirection(Game.getRight());
		game.doAction(Game.A_MOVE);
		// Yer degisiminden sonra playerX ve playerY degiseceginden
		// guncelleyelim.
		playerX = game.getPlayerX();
		playerY = game.getPlayerY();

		// Yapilacak her islem oncesi bir durum analizi yapip ona gore islem
		// yapmamiz gerek.
		createState(new Location(playerX, playerY));

	}

	//Stackoverflow :D 
	private static final int[][] neighbour_blocks = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

	private void createState(Location l) {

		// Yer degistirme isleminden sonra birden fazla olasýlýk olabilir.
		// 1)Glitter bulunma durumu
		// 2)Stench bulunma durumu
		// 3)Pit bulunma durumu
		// 4)Wumpus bulunma durumu
		// ...

		// Bulunan noktanin komsu bloklarini bulmak icin.
		for (int i = 0; i < 4; i++) {
			// 1, 0, -1, 0 durumlari.Yani x dogrusunda -1 ve 1 arasi.
			int neighbourX = l.getX() + neighbour_blocks[i][0];
			// 0, 1, 0, -1 durumlari.Yani y dogrusunda -1 ve 1 arasi.
			int neighbourY = l.getY() + neighbour_blocks[i][1];

			/*
			 * Ornegin (2,1) icin; (3,1), (2,2), (1,1) ve (2,0) noktalari komsu
			 * noktalar olucaktir.
			 */

			Location neighbourLocation = new Location(neighbourX, neighbourY);
			// bilinen bir blok ise bilinmeyen ise zaten durum belirleyemeyiz.
			if (!game.isUnknown(neighbourLocation)) {
				// Baslangicta state tipi : TEHLIKE YOK olucak.
				if (game.hasPit(neighbourLocation)) {
					// State tipi :PIT SEVIYE TEHLIKE
				}
				if (game.hasWumpus(neighbourLocation)) {
					// State tipi : WUMPUS SEVIYE TEHLIKE
				}
			} else {
				// State tipi : BILINMEYEN, islemleri diger olasilikler icin
				// beklet tarzi birsey olabilir.
			}

		}
	}
}
