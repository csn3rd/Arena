import javafx.scene.image.Image;
 
public class HealthBar extends Actor {
	private Player player;
	private int health;
	private final int HEALTHBAR_HEIGHT = 10;
	
	/**
	 * A HealthBar is a simple visual indicator to show the remaining health of the player.
	 */
	public HealthBar(Player player) {
		this.player = player;
		this.health = player.getHealth();
		if (player.getNum()==1)	
			this.setImage(new Image("file:PlayerHealth1.gif"));
		else 
			this.setImage(new Image("file:PlayerHealth2.gif"));
		this.setFitHeight(HEALTHBAR_HEIGHT);
		this.setFitWidth(health);
	}
	
	public int getHealth() { return health; }
 
	/**
	 * Actions that the HealthBar takes once every frame.
	 * @param long now, the time stamp, in nanoseconds, of the frame.
	 */
	@Override
	public void act(long now) {
		this.setFitWidth(player.getHealth());
		this.setX(player.getX());
		this.setY(player.getY() - player.getFitHeight() / 2);
		if (player.getHealth() == 0) {
			this.setVisible(false);
		}
		else {
			this.setVisible(true);
		}
	}
 
	/**
	 * The HealthBar simply moves along with its parent Player, so it is convenient
	 * to completely disregard the parameters.
	 */
	@Override
	public double move(double dx, double dy) { 
		this.setX(player.getX());
		this.setY(player.getY() - player.getFitHeight() / 2 - 15);
		return player.getFitHeight() / 2 + 15;
	}
	
	public String toString(){
		return "Player " + player.getNum() + " health: " + health; 
	}
}