package com.byporti;

public class Game {

	private boolean inPit = false;
	private boolean hasGold = false;
	private boolean gameOver = false;

	// Yapay zekanin kullacabilecegi yonler.
	private static final int UP = 1;
	private static final int RIGHT = 2;
	private static final int DOWN = 3;
	private static final int LEFT = 4;

	// Baslangicte saga donuk baslasin.
	private int direction = RIGHT;

	// Haritada bulunan objeler ve ozellikleri
	public static final String BREEZE = "Breeze";
	public static final String STENCH = "Stench";
	public static final String PIT = "Pit";
	public static final String WUMPUS = "Wumpus";
	public static final String GLITTER = "Glitter";
	public static final String GOLD = "Tresuare";
	public static final String UNKNOWN = "Unknown";

	// Yapay zekanin yapabilecegi islemler.
	// secilen yone dogru hareket.
	public static final String A_MOVE = "move";
	// altini buldugunda yerden alma ve probleme cozme islemi icin.
	public static final String A_GRAB = "grab";
	// pite yakalandigi zaman cikma islemi icin.
	public static final String A_CLIMB = "climb";

	// 4x4 luk harita icin her bir blogun ayri ozelligi olacak.Bunlari kaydetmek
	// icin.
	private String[][] cell;

	public Game(int size) {
		cell = new String[size + 1][size + 1];

		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				// Butun hucreler baslangicta ziyaret edilmemis, yani bilinmeyen
				// durumda.
				cell[i][j] = UNKNOWN;
			}
		}

		// Player 1x1 kismindan baslayacagindan bilinen hale getirelim.
		setVisited(new Location(1, 1));
	}

	private void setVisited(Location l) {
		if (cell[l.getX()][l.getY()].contains(UNKNOWN)) {
			cell[l.getX()][l.getY()] = cell[l.getX()][l.getY()].replaceAll(UNKNOWN, "");
		}
	}

	// Secilen blogun bilinen ya da bilinmeyen oldugunu kontrol
	public boolean isUnknown(Location l) {
		if (cell[l.getX()][l.getY()].contains(UNKNOWN)) {
			return true;
		} else {
			return false;
		}
	}

	// Ziyaret edilip edilmedigi kontrol
	public boolean isVisited(Location l) {
		if (!isUnknown(l)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasBreeze(Location l) {
		if (isUnknown(l)) {
			return false;
		} else if (cell[l.getX()][l.getY()].contains(BREEZE)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasStench(Location l) {
		if (isUnknown(l)) {
			return false;
		} else if (cell[l.getX()][l.getY()].contains(STENCH)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasGlitter(Location l) {
		if (isUnknown(l)) {
			return false;
		} else if (cell[l.getX()][l.getY()].contains(GLITTER)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPit(Location l) {
		if (isUnknown(l)) {
			return false;
		} else if (cell[l.getX()][l.getY()].contains(PIT)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasWumpus(Location l) {
		if (isUnknown(l)) {
			return false;
		} else if (cell[l.getX()][l.getY()].contains(WUMPUS)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasGold(Location l) {
		if (isUnknown(l)) {
			return false;
		} else if (cell[l.getX()][l.getY()].contains(GOLD)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPlayer(Location l) {
		if (l.getX() == 1 && l.getY() == 1) {
			return true;
		}
		return false;
	}

	//Bloklarýn her birine bilgi ekleme metodu.
	private void addInformation(Location l, String value) {
		// Eger eklenmisse tekrar eklememek icin
		if (!cell[l.getX()][l.getY()].contains(value)) {
			// O bloga degerini ekledik.
			cell[l.getX()][l.getY()] += value;
		}
	}

	public void addWumpus(Location l) {
		// O blogta wumpus olmadigi surece ekleme yapilsin.
		if (!cell[l.getX()][l.getY()].contains(WUMPUS)) {
			// Merkeze wumpusu ekledik.
			addInformation(l, WUMPUS);
			// Cevresine yani 4 bir tarafinada wumpusun orada bulundugunu
			// belirtmek adina stench ekledik.
			addInformation(new Location(l.getX() - 1, l.getY()), STENCH);
			addInformation(new Location(l.getX() + 1, l.getY()), STENCH);
			addInformation(new Location(l.getX(), l.getY() - 1), STENCH);
			addInformation(new Location(l.getX(), l.getY() + 1), STENCH);
		}
	}

	public void addPit(Location l) {
		// O blogta pit olmadigi surece ekleme yapilsin.
		if (!cell[l.getX()][l.getY()].contains(PIT)) {
			// Merkeze pit ekledik.
			addInformation(l, PIT);
			// Cevresine yani 4 bir tarafinada pit'in orada bulundugunu
			// belirtmek adina breeze ekledik.
			addInformation(new Location(l.getX() - 1, l.getY()), BREEZE);
			addInformation(new Location(l.getX() + 1, l.getY()), BREEZE);
			addInformation(new Location(l.getX(), l.getY() - 1), BREEZE);
			addInformation(new Location(l.getX(), l.getY() + 1), BREEZE);
		}
	}

	public void addGold(Location l) {
		if (!cell[l.getX()][l.getY()].contains(GOLD)) {
			// Merkeze gold ekledik.
			addInformation(l, GOLD);
			// Cevresine yani 4 bir tarafinada gold'un orada bulundugunu
			// belirtmek adina glitter ekledik.
			addInformation(new Location(l.getX() - 1, l.getY()), GLITTER);
			addInformation(new Location(l.getX() + 1, l.getY()), GLITTER);
			addInformation(new Location(l.getX(), l.getY() - 1), GLITTER);
			addInformation(new Location(l.getX(), l.getY() + 1), GLITTER);
		}
	}

	private int score = 0;
	// Baslangicta player 1x1 blogunda oldugu icin.
	private int playerX = 1;
	private int playerY = 1;

	public boolean doAction(String value) {
		// Eger oyun bitmisse islem yapmasin ve false deger dondursun.
		if (gameOver) {
			return false;
		}
		// Her islem 1 puan gotursun.
		score -= 1;
		// Gelen bilgi hareket bilgisi ise
		if (value.equals(A_MOVE)) {
			// Eger pit icerisinde degilse yani hareket edebilme mevcut durumda
			// ise
			if (!inPit) {
				if (direction == LEFT) {
					return move(new Location(playerX - 1, playerY));
				}
				if (direction == RIGHT) {
					return move(new Location(playerX + 1, playerY));
				}
				if (direction == UP) {
					return move(new Location(playerX, playerY + 1));
				}
				if (direction == DOWN) {
					return move(new Location(playerX, playerY - 1));
				}
			}
		}
		
		if (value.equals(A_GRAB)) {
			if (hasGold(new Location(playerX, playerY))) {
				// Yerden goldu aldik ve o blogta bir sey kalmadigini gostermek
				// icin.
				cell[playerX][playerY] = cell[playerX][playerY].replaceAll(GOLD, "");

				// GAME OVER.
				score += 1000;
				hasGold = true;
				gameOver = true;

				return true;
			}
		}

		if (value.equals(A_CLIMB)) {
			// Climp islemi gerceklestirildi.Pit blogundan artik cikilabilir.
			inPit = false;
		}

		return false;
	}

	public boolean move(Location l) {
		// Baslangic yeri 1x1 idi.Hareket edilmesi sonucu yeni yere islem
		// yapildi.Yani yeni yer bilinen bir blog haline geldi.
		setVisited(l);
		// Yeni yerde bulunabilecek ve puanlamayi etkileyecek 2 durum vardir.
		// 1. durum wumpus bulunmasi
		// 2. durum pit bulunmasi

		if (hasWumpus(l)) {
			score -= 1000;
			gameOver = true;

			// Kisaca oyun bitti.
		}

		if (hasPit(l)) {
			score -= 1000;
			inPit = true;

			// Pit icerisine girildi.Ceza olarak 1000 puan eksildi ve climp
			// islemi gerekecek.
		}

		return true;
	}

	// GETTER AND SETTER
	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public static int getUp() {
		return UP;
	}

	public static int getRight() {
		return RIGHT;
	}

	public static int getDown() {
		return DOWN;
	}

	public static int getLeft() {
		return LEFT;
	}

}
