package Frontend;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Backend.Bullet;
import Backend.GameObject;
import Backend.Levels;
import Backend.Monster;
import Backend.Obstacle;
import Backend.Player;
import Backend.PowerUp;
import Backend.QuadTree;
import Backend.SnakeMonster;
import Backend.SpawnPoint;
import processing.core.PApplet;
import processing.core.PImage;//IMAGE RELEVANT
import processing.video.Capture;

public class Application extends PApplet {

	public static PImage shipPic;// IMAGE RELEVANT
	public static PImage monsterPic;// IMAGE RELEVANT
	public static PImage snakeMonsterPic;// IMAGE RELEVANT
	public static PImage heart;// IMAGE RELEVANT
	public static PImage rock; // IMAGE RELEVANT
	public static PImage lifePowerUp;
	public static PImage speedPowerUp;
	public static PImage tripleFirePowerUp;
	public static PImage rapidFirePowerUp;
	public static PImage deadMonsterPic;
	public static PImage deadSnakeMonsterPic;

	Player player;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	QuadTree qt = new QuadTree(0, new Rectangle(0, 0, 900, 900));

	int level = 1;
	int levelFrame = 900;
	int spawnFrame = 100;
	int lastClick = 0;
	boolean gameOver = false;
	boolean mPressed = false;

	int score = 0; // DC: added score counter
	int snakeMonsterScore = 10;
	int monsterScore = 5;

	boolean videoEnabled = false;
	boolean videoGlitch = false;
	boolean hydraGlitch = false;
	public static boolean switchGlitch = false; // Every monster looks like a
												// ship, the ship looks like a
												// monster
	public static boolean manyPowerUpsGlitch = false; // Every monster drops a
														// power-up
	public static boolean runawayGlitch = false; // power ups run away from
													// player
	public static boolean teleportGlitch = false; // some monsters teleport

	File gunShot;
	File death1;
	File death2;
	File explosion;
	File backgroundMusic;

	double threshhold = 15;
	Capture video;
	PImage temp;
	PImage dImage;

	public static void main(String[] args) {
		PApplet.main("Frontend.Application");

	}

	public void settings() {
		size(900, 900);
	}

	public void setup() {
		frameRate(60);

		player = new Player(this, 450, 450);
		gameObjects.add(player);

		Levels.loadLevel(this, player, gameObjects, level);
		generateGlitch();

		monsterPic = loadImage("monster1.png");// IMAGE RELEVANT
		snakeMonsterPic = loadImage("monster2.png");// IMAGE RELEVANT
		shipPic = loadImage("AVerySillyShip2.png");// IMAGE RELEVANT
		heart = loadImage("heart.png");// IMAGE RELEVANT
		rock = loadImage("definitelyARock.png"); // IMAGE RELEVANT

		deadMonsterPic = loadImage("monster1dead.png");
		deadSnakeMonsterPic = loadImage("monster2dead.png");

		lifePowerUp = loadImage("lifePowerUp.png");
		speedPowerUp = loadImage("speedPowerUp.png");
		tripleFirePowerUp = loadImage("TripleShotPowerUp.png");
		rapidFirePowerUp = loadImage("rapidFirePowerUp.png");

		gunShot = new File("src/gunShot.wav");
		death1 = new File("src/death1.wav");
		death2 = new File("src/death2.wav");
		explosion = new File("src/explosion.wav");
		backgroundMusic = new File("src/backgroundMusic.wav");
		playBackground(backgroundMusic);

		try {
			video = new Capture(this, 1800, 600);
			video.start();
			temp = createImage(800, 600, RGB);
			dImage = createImage(800, 600, RGB);
			videoEnabled = true;
		} catch (Exception e) {
			try {
				video.stop();
				video = new Capture(this, 640, 480);
				video.start();
				temp = createImage(640, 480, RGB);
				dImage = createImage(640, 480, RGB);
				videoEnabled = true;
			} catch (Exception e1) {
				videoEnabled = false;
			}
		} 
	}

	public void captureEvent(Capture video) {
		if (videoEnabled) {
			temp.copy(video, 0, 0, video.width, video.height, 0, 0, video.width, video.height);
			temp.updatePixels();
			video.read();
		}
	}

	public void playBackground(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(1);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void mousePressed() {
		// click to restart game.
		if (gameOver) {
			gameOver = false;
			player = new Player(this, 450, 450);
			gameObjects.add(player);
			level = 1;
			Levels.loadLevel(this, player, gameObjects, level);
			frameCount = 0;
			levelFrame = 900;
			spawnFrame = 100;
			lastClick = 0;
			return;
		}

		if (frameCount - lastClick >= player.getDelay() - 5) {
			lastClick = frameCount;
			playSound(gunShot);
			player.shoot(gameObjects);
		}
		mPressed = true;
	}

	public void mouseReleased() {
		mPressed = false;
	}

	public void keyPressed() {
		player.setDir();
	}

	public void keyReleased() {
		player.stopDir();
	}

	public void draw() {
		// do nothing if game over is showing.
		if (gameOver) {
			return;
		}

		// check if mouse is held down and shoot every 20 frames
		if (mPressed && frameCount - lastClick > player.getDelay()) {
			playSound(gunShot);
			lastClick = frameCount;
			player.shoot(gameObjects);
		}

		// draw background and border
		background(255);

		if (videoEnabled) {
			temp.loadPixels();
			dImage.loadPixels();
			video.loadPixels();

			for (int x = 0; x < video.width; x++) {
				for (int y = 0; y < video.height; y++) {
					int loc = x + y * video.width;
					float r1 = red(video.pixels[loc]);
					float g1 = green(video.pixels[loc]);
					float b1 = blue(video.pixels[loc]);
					float r2 = red(temp.pixels[loc]);
					float g2 = green(temp.pixels[loc]);
					float b2 = blue(temp.pixels[loc]);

					double d = Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));
					if (d < threshhold) {
						dImage.pixels[loc] = color(255);
					} else {
						dImage.pixels[loc] = color(200);
					}
				}
			}
			dImage.updatePixels();

			if (videoGlitch)
				image(dImage, 450, 450);
		}

		fill(0);
		rect(0, 0, 900, 30);
		rect(0, 0, 30, 900);
		rect(900 - 30, 0, 30, 900);
		rect(0, 900 - 30, 900, 30);

		textAlign(LEFT);
		textSize(12);

		text("Level: " + level, 780, 50);
		text("Score: " + score, 780, 63); // DC: added score display

		// spawn Monster
		levelSpawn();

		imageMode(CENTER);// IMAGE RELEVANT

		player.drawPlayerHealth();

		Bullet.moveShowBullets(gameObjects);
		checkCollision();

		player.move();
		player.draw();

		Monster.showMonsters();

		Obstacle.showObstacles();

		if (runawayGlitch) {
			PowerUp.movePowerUps(player.getX(), player.getY());
		}
		PowerUp.showPowerUps();

	}

	public void checkCollision() {
		qt.clear();
		for (GameObject obj : gameObjects) {
			qt.insert(obj);
		}

		ArrayList<GameObject> returnList = new ArrayList<GameObject>();
		ArrayList<GameObject> removeList = new ArrayList<GameObject>();
		ArrayList<GameObject> newObjects = new ArrayList<GameObject>();

		for (int i = 0; i < gameObjects.size(); i++) {

			returnList.clear();
			qt.retrieve(returnList, gameObjects.get(i));

			if (gameObjects.get(i) instanceof Bullet) {

				Bullet b = (Bullet) gameObjects.get(i);
				for (int j = 0; j < returnList.size(); j++) {
					float d = (float) (Math.hypot(b.getX() - returnList.get(j).getX(),
							b.getY() - returnList.get(j).getY()));

					if (d <= (b.getSize() + returnList.get(j).getSize()) / 2 && !(returnList.get(j) instanceof Player)
							&& !(returnList.get(j) instanceof Bullet)) {
						if (returnList.get(j) instanceof Monster) {
							Monster m = (Monster) returnList.get(j);
							removeList.add(b);
							removeList.add(m);
							Bullet.getBullets().remove(b);
							Monster.getMonsters().remove(m);
							m.kill();
							if (m instanceof SnakeMonster) {
								score += snakeMonsterScore; // DC: increment score if monster killed
								playSound(death1);
								if (hydraGlitch) {
									SnakeMonster m1 = new SnakeMonster(this, m.getX(), m.getY(), m.getSpeed());
									newObjects.add(m1);
								}
							} else {
								score += monsterScore; // and here
								playSound(death2);
								if (hydraGlitch) {
									Monster m1 = new Monster(this, m.getX(), m.getY(), m.getSpeed());
									newObjects.add(m1);
								}
							}
							break;
						}

						if (returnList.get(j) instanceof Obstacle) {
							removeList.add(b);
							Bullet.getBullets().remove(b);
							break;
						}
					}
				}
			} else if (gameObjects.get(i) instanceof Monster) {
				Monster m = (Monster) gameObjects.get(i);
				for (int j = 0; j < returnList.size(); j++) {
					float d = (float) (Math.hypot(m.getX() - returnList.get(j).getX(),
							m.getY() - returnList.get(j).getY()));
					if (d <= (m.getSize() + returnList.get(j).getSize()) / 2 && returnList.get(j) instanceof Player) {
						// Player lose health and die/game over
						player.setHealth(player.getHealth() - 1);
						if (player.getHealth() == 0) {
							gameOver = true;
							textSize(30);
							textAlign(CENTER);
							text("Game Over!", 450, 450);
							textSize(18);
							text("You scored " + score + " points!", 450, 470);
							text("Click mouse to restart.", 450, 490);
							Obstacle.getObstacles().clear();
							SpawnPoint.getSpawnPoints().clear();
							gameObjects.clear();
							score = 0; // DC: reset score on Game Over
						} else {
							player.setX(450);
							player.setY(450);
							gameObjects.removeAll(Monster.getMonsters());
							gameObjects.removeAll(Bullet.getBullets());
							frameCount = levelFrame * (level - 1);
						}
						playSound(explosion);
						Bullet.getBullets().clear();
						Monster.getMonsters().clear();
						PowerUp.getPowerUps().clear();
						player.resetPowerUps();
						lastClick = 0;
						return;
					} else {
						m.setMove(player.getX(), player.getY());
					}
				}
				m.move();
			}

		}

		if (!newObjects.isEmpty()) {
			gameObjects.addAll(newObjects);
		}

		gameObjects.removeAll(removeList);

	}

	public void levelSpawn() {
		// increase level
		if (frameCount % levelFrame == 0) {
			SpawnPoint.getSpawnPoints().clear();
			level++;
			Levels.loadLevel(this, player, gameObjects, level);
			spawnFrame = spawnFrame - 5;

			// reset all glitches
			videoGlitch = false;
			hydraGlitch = false;
			switchGlitch = false;
			manyPowerUpsGlitch = false;
			runawayGlitch = false;
			teleportGlitch = false;

			generateGlitch();
		}

		// spawn monster
		if (frameCount % spawnFrame == 0) {
			SpawnPoint.spawn(this, gameObjects);
		}
	}

	public void generateGlitch() {
		Random r = new Random();
		int chance;
		if (videoEnabled) {
			chance = 12;
		} else {
			chance = 10;
		}
		switch (r.nextInt(chance)) {
		case 0:
			videoGlitch = true;
			//System.out.println("videoGlitch");
			break;
		case 1:
			hydraGlitch = true;
			//System.out.println("hydraGlitch");
			break;
		case 2:
			switchGlitch = true;
			//System.out.println("switchGlitch");
			break;
		case 3:
			manyPowerUpsGlitch = true;
			//System.out.println("manyPowerUpsGlitch");
			break;
		case 4:
			runawayGlitch = true;
			//System.out.println("runawayGlitch");
			break;
		case 5:
			teleportGlitch = true;
			//System.out.println("teleportGlitch");
			break;
		default:
			//System.out.println("normal mode");
			break;
		}
	}

}