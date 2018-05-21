import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player extends Actor {
	private double dx, dy;
	private int health;
	private HealthBar healthBar;
	private int playerNum;
	private boolean isLiving;
	private int jumpCount;
	private boolean faceLeft;
	private boolean faceRight;

	// controls for P1
	private boolean keyW, keyA, keyS, keyD, key1, key2;

	// controls for P2
	private boolean keyUP, keyDOWN, keyLEFT, keyRIGHT, keyLB, keyRB;

	private AnimationTimer jumpTimer;
	private AnimationTimer moveTimer;
	private AnimationTimer attackTimer;
	private AnimationTimer attackTimer2;
	private ArrayList<ImageView> walkAnimationL, walkAnimationR;
	private int jumpDelayCount;
	private int moveCount;
	private int attackCount; //for sword
	private int attackCount2; //for arrow projectile
	private int countAnimation;
	private boolean changeAnim;
	private boolean attackAnim;
	private boolean attackAnim2;

	private ImageView knightMoveL0, knightMoveL1, knightMoveL2, knightMoveL3, knightMoveL4, knightMoveL5, knightMoveL6,
	knightMoveL7, knightMoveR0, knightMoveR1, knightMoveR2, knightMoveR3, knightMoveR4, knightMoveR5,
	knightMoveR6, knightMoveR7;

	// playerNum is which player it is: input may only be 1 or 2
	public Player(int health, int playerNum, double translateX, double translateY) {
		jumpCount = 0;
		moveCount = 0;
		jumpDelayCount = 0;
		countAnimation = 0;
		attackCount = 0;
		attackCount2 = 0;

		changeAnim = false;
		attackAnim = false;

		knightMoveL0 = new ImageView("file:sprKnightMoveL_0.gif");
		knightMoveL1 = new ImageView("file:sprKnightMoveL_1.gif");
		knightMoveL2 = new ImageView("file:sprKnightMoveL_2.gif");
		knightMoveL3 = new ImageView("file:sprKnightMoveL_4.gif");
		knightMoveL5 = new ImageView("file:sprKnightMoveL_5.gif");
		knightMoveL6 = new ImageView("file:sprKnightMoveL_6.gif");
		knightMoveL7 = new ImageView("file:sprKnightMoveL_7.gif");

		knightMoveR0 = new ImageView("file:sprKnightMoveR_0.gif");
		knightMoveR1 = new ImageView("file:sprKnightMoveR_1.gif");
		knightMoveR2 = new ImageView("file:sprKnightMoveR_2.gif");
		knightMoveR3 = new ImageView("file:sprKnightMoveR_3.gif");
		knightMoveR4 = new ImageView("file:sprKnightMoveR_4.gif");
		knightMoveR5 = new ImageView("file:sprKnightMoveR_5.gif");
		knightMoveR6 = new ImageView("file:sprKnightMoveR_6.gif");
		knightMoveR7 = new ImageView("file:sprKnightMoveR_7.gif");

		walkAnimationL = new ArrayList<ImageView>();
		walkAnimationL.add(knightMoveL0);
		walkAnimationL.add(knightMoveL1);
		walkAnimationL.add(knightMoveL2);
		walkAnimationL.add(knightMoveL3);
		walkAnimationL.add(knightMoveL4);
		walkAnimationL.add(knightMoveL5);
		walkAnimationL.add(knightMoveL6);
		walkAnimationL.add(knightMoveL7);

		walkAnimationR = new ArrayList<ImageView>();
		walkAnimationR.add(knightMoveR0);
		walkAnimationR.add(knightMoveR1);
		walkAnimationR.add(knightMoveR2);
		walkAnimationR.add(knightMoveR3);
		walkAnimationR.add(knightMoveR4);
		walkAnimationR.add(knightMoveR5);
		walkAnimationR.add(knightMoveR6);
		walkAnimationR.add(knightMoveR7);

		jumpTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				jumpDelayCount++;
			}
		};
		moveTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (countAnimation > 7) {
					countAnimation = 0;
				}
				if (moveCount++ >= 10) {
					moveCount = 0;
					countAnimation++;
				}
			}
		};

		attackTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				attackCount++;
				primaryAttack();
				if (attackCount == 0){
					this.stop();
				}
			}
		};

		attackTimer2 = new AnimationTimer() {
			@Override
			public void handle(long now){
				attackCount2++;
				secondaryAttack();
				if (attackCount2 == 0){
					this.stop();
				}

			}
		};

		if (playerNum == 1 || playerNum == 2) {
			this.playerNum = playerNum;
		} else {
			playerNum = 0;
			System.out.println("Invalid argument for playerNum in the Player Class");
		}

		dx = 0;
		dy = 5;
		if (playerNum == 1) {
			this.setImage(new Image("file:sprKnightNoneR_0.gif"));
			faceRight = true;
			faceLeft = false;
		} else if (playerNum == 2) {
			this.setImage(new Image("file:sprKnightNoneL_0.gif"));
			faceLeft = true;
			faceRight = false;
		}
		this.setFitHeight(50);
		this.setFitWidth(50);
		this.setX(translateX);
		this.setY(translateY);
		this.health = health;
		healthBar = new HealthBar(this);
		isLiving = true;
		keyW = false;
		keyA = false;
		keyS = false;
		keyD = false;
		key1 = false;
		key2 = false;
		keyUP = false;
		keyDOWN = false;
		keyLEFT = false;
		keyRIGHT = false;
		keyLB = false;
		keyRB = false;
	}

	public void act(long now) {
		if (playerNum == 1 && this.getScene() != null) {
			moveTimer.start();
			this.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent k) {
					if (k.getCode() == KeyCode.W) {
						keyW = true;
					}
					if (k.getCode() == KeyCode.S) {
						keyS = true;
					}
					if (k.getCode() == KeyCode.A) {
						keyA = true;
						keyD = false;
					}
					if (k.getCode() == KeyCode.D) {
						keyD = true;
						keyA = false;
					}
					if (k.getCode() == KeyCode.DIGIT1) {
						key1 = true;
					}
					if (k.getCode() == KeyCode.DIGIT2) {
						key2 = true;
					}
				}
			});
			this.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent k) {
					if (k.getCode() == KeyCode.W) {
						keyW = false;
						jumpCount = 0;
					}
					if (k.getCode() == KeyCode.S) {
						keyS = false;
					}
					if (k.getCode() == KeyCode.A) {
						keyA = false;
					}
					if (k.getCode() == KeyCode.D) {
						keyD = false;
					}
					if (k.getCode() == KeyCode.DIGIT1) {
						key1 = false;
					}
					if (k.getCode() == KeyCode.DIGIT2) {
						key2 = false;
					}
				}
			});
			// jump
			if (keyW && onPlatform()
					&& this.getY() <= this.getOneIntersectingObject(Platform.class).getY() - this.getFitHeight() / 2) {
				jumpCount++;
			}
			// down
			if (keyS) {
				dy += 0;
				if (faceLeft) {
					this.setImage(new Image("file:sprKnightNoneDuckL_0.gif"));
					this.setFitHeight(25);
				} else if (faceRight) {
					this.setImage(new Image("file:sprKnightNoneDuckR_0.gif"));
					this.setFitHeight(25);
				}
				keyA = false;
				keyD = false;
			} else if (!keyS && !changeAnim && !attackAnim) {
				if (faceLeft) {
					this.setImage(new Image("file:sprKnightNoneL_0.gif"));
					this.setFitHeight(50);
				} else if (faceRight) {
					this.setImage(new Image("file:sprKnightNoneR_0.gif"));
					this.setFitHeight(50);
				}
			}
			// stay still
			if (!keyD && !keyA) {
				moveTimer.stop();
				changeAnim = false;
				dx = 0;
			}
			// left
			else if (keyA) {
				changeAnim = true;
				moveTimer.start();
				dx = -10;
			}
			// right
			if (keyD) {
				changeAnim = true;
				moveTimer.start();
				dx = 10;
			}
			dy = move(dx, dy);
			if (key1 && !attackAnim) {
				attackAnim = true;
				attackAnim2 = true;
				attackTimer.start();
			}
			if (key2 && !attackAnim2) {
				attackAnim2 = true;
				attackAnim = true;
				attackTimer2.start();
			}
		}
		if (playerNum == 2 && this.getScene() != null) {
			this.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent k) {
					if (k.getCode() == KeyCode.UP) {
						keyUP = true;
					}
					if (k.getCode() == KeyCode.DOWN) {
						keyDOWN = true;
					}
					if (k.getCode() == KeyCode.LEFT) {
						keyLEFT = true;
						keyRIGHT = false;
					}
					if (k.getCode() == KeyCode.RIGHT) {
						keyRIGHT = true;
						keyLEFT = false;
					}
					if (k.getCode() == KeyCode.PERIOD) {
						keyLB = true;
					}
					if (k.getCode() == KeyCode.SLASH) {
						keyRB = true;
					}
				}
			});
			this.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent k) {
					if (k.getCode() == KeyCode.UP) {
						keyUP = false;
						jumpCount = 0;
					}
					if (k.getCode() == KeyCode.DOWN) {
						keyDOWN = false;
					}
					if (k.getCode() == KeyCode.LEFT) {
						keyLEFT = false;
					}
					if (k.getCode() == KeyCode.RIGHT) {
						keyRIGHT = false;
					}
					if (k.getCode() == KeyCode.PERIOD) {
						keyLB = false;
					}
					if (k.getCode() == KeyCode.SLASH) {
						keyRB = false;
					}
				}
			});
			// jump
			if (keyUP && onPlatform()
					&& this.getY() <= this.getOneIntersectingObject(Platform.class).getY() - this.getFitHeight() / 2) {
				jumpCount++;
			}
			// down
			if (keyDOWN) {
				dy += 0;
				if (faceLeft) {
					this.setImage(new Image("file:sprKnightNoneDuckL_0.gif"));
					this.setFitHeight(25);
				} else if (faceRight) {
					this.setImage(new Image("file:sprKnightNoneDuckR_0.gif"));
					this.setFitHeight(25);
				}
				keyLEFT = false;
				keyRIGHT = false;
			} else if (!keyDOWN && !changeAnim && !attackAnim) {
				if (faceLeft) {
					this.setImage(new Image("file:sprKnightNoneL_0.gif"));
					this.setFitHeight(50);

				} else if (faceRight) {
					this.setImage(new Image("file:sprKnightNoneR_0.gif"));
					this.setFitHeight(50);
				}
			}
			// stay still
			if (!keyRIGHT && !keyLEFT) {
				moveTimer.stop();
				changeAnim = false;
				dx = 0;
			}
			// left
			if (keyLEFT) {
				changeAnim = true;
				moveTimer.start();
				dx = -10;
			}
			// right
			if (keyRIGHT) {
				changeAnim = true;
				moveTimer.start();
				dx = 10;
			}
			dy = move(dx, dy);
			if (keyLB && !attackAnim) {
				attackAnim = true;
				attackAnim2 = true; //animation is attking
				attackTimer.start();
			}
			if (keyRB && !attackAnim2) {
				attackAnim = true;
				attackAnim2 = true;
				attackTimer2.start();
			}
		}

		if (changeAnim) {
			if (keyA || keyLEFT) {
				try {
					this.setImage(walkAnimationL.get(countAnimation).getImage());
					faceLeft = true;
					faceRight = false;
				} catch (Exception e) {}
			} else if (keyD || keyRIGHT) {
				try {
					this.setImage(walkAnimationR.get(countAnimation).getImage());
					faceLeft = false;
					faceRight = true;
				} catch (Exception e) {}
			}
		}
		if (!isLiving) {
			this.getWorld().death(this.getNum());
			this.getWorld().remove(this);
		}
	}

	public void primaryAttack() {
		if (attackCount > 0) {
			if (faceLeft)
				this.setImage(new Image("file:sprKnightPreAttackL_0.gif"));
			else if (faceRight)
				this.setImage(new Image("file:sprKnightPreAttackR_0.gif"));
		}
		if (attackCount > 10) {
			if (faceLeft)
				this.setImage(new Image("file:sprKnightPostAttackL_0.gif"));
			else if (faceRight)
				this.setImage(new Image("file:sprKnightPostAttackR_0.gif"));
			if (attackCount > 25) {
				try {
					Player other = this.getOneIntersectingObject(Player.class);
					if (other != null){
						if ((faceLeft && this.getX() > other.getX()) || (faceRight && this.getX() < other.getX())) {
							other.update(-6);
						}
					}
				}
				catch (Exception e){}
				attackAnim = false;
				attackAnim2 = false;
				attackCount = 0;
			}
		}
	}

	public void secondaryAttack() {
		if (attackCount2 == 1) {
			if (faceLeft) {
				this.getWorld().add(new Projectile(this));
			} else if (faceRight) {
				this.getWorld().add(new Projectile(this));

			}
		}
		else if (attackCount2 >= 30){
			attackAnim2 = false;
			attackAnim = false;
			attackCount2 = 0;
		}
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double d) {
		dx = d;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double d) {
		dy = d;
	}

	public boolean getLiving() {
		return isLiving;
	}

	public void setLiving(boolean a) {
		isLiving = a;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}

	public boolean isLiving() {
		return isLiving;
	}

	public int getNum() {
		return playerNum;
	}

	// checks if you died
	public void update(int num) {
		if (health + num <= 0) {
			health = 0;
			isLiving = false;
		} else if (this.getX() > this.getWorld().getWidth() + 100 || this.getX() < -100
				|| this.getY() > this.getWorld().getHeight() + 100) {
			isLiving = false;
		} else {
			health += num;
		}
	}

	/**
	 * this may or may not function
	 * 
	 * @return
	 */
	public boolean onPlatform() {
		// you must intersect it and you must be approaching from above
		return (this.getOneIntersectingObject(Platform.class) != null);
	}

	public String getDirection() {
		if (faceLeft) {
			return "left";
		} else if (faceRight) {
			return "right";
		}
		return null;
	}

	@Override
	public double move(double dx, double dy) {
		Actor intersector = this.getOneIntersectingObject(Platform.class);
		if (onPlatform() && dy >= 0 && this.getY() < intersector.getY() - this.getFitHeight() / 2) {
			this.setY(intersector.getY() - this.getFitHeight());
			dy = 0;
			if (jumpCount == 1) {
				dy -= 12;
			}
		} else {
			dy += 1;
			this.update(0);
		}
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		return dy;
	}
}