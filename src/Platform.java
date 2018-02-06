import javafx.scene.image.Image;
 
public class Platform extends Actor {
 
	/**
	 * A Platform is a class on which fighting Actors can stand.
	 * @param X position
	 * @param Y position
	 */
	public Platform(double X, double Y) {
		this.setImage(new Image("file:Platform.gif"));
		this.setFitHeight(10);
		this.setFitWidth(10);
		this.setX(X);
		this.setY(Y);
	}
 
 
	@Override
	public void act(long now) {
 
	}
 
	@Override
	public double move(double dx, double dy) {
		return 0;	 	
	}
 
 
	@Override
	public String toString() {
		return "Platform: ("+getX()+", "+getY()+")";
	}
}