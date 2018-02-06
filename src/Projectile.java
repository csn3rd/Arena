import javafx.scene.image.Image;
 
public class Projectile extends Actor {
	private double dx;
	private boolean isVisible;
	
	/**
	 * A Projectile is a battle object a player can shoot
	 * @param player that produces the projectile
	 */
	public Projectile(Player player) {
		player.getWorld().stop();
		if (player.getDirection().equals("left")) {
			dx = -30;
			this.setX(player.getX()-50); //initial position
		} else {
			dx = 30;
			this.setX(player.getX()+50); //initial position
		}
		this.setY(player.getY()+player.getFitHeight()/3); //initial position
		if (dx == 30) {
			this.setImage(new Image("file:sprArrowR_0.gif"));
		} else {
			this.setImage(new Image("file:sprArrowL_0.gif"));
		}
		isVisible = true;
		player.getWorld().start();
	}
	
	public void act(long now) {
		// add code later
		move(dx, 0);
		Player other = this.getOneIntersectingObject(Player.class);
		if (isVisible && other!=null) {
			other.update(-4);
			isVisible = false;
			this.setVisible(false);
		}
	}
 
	@Override
	public double move(double dx, double dy) { //dy should always be 0
		// add code later
		this.setX(this.getX() + dx);
		this.setY(this.getY());
		return dy;
	}
	public String toString(){
		return "Projectile dx: " + dx + " dy: " + 0;
	}
}