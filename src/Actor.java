import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
 
public abstract class Actor extends ImageView {
	
	/**
	 * Constructs an Actor. Actor is an abstract base class for general 
	 * sprites in an arcade style game. Because Actor extends ImageView,
	 * you have access to all the ImageView commands.
	 */
	public Actor() {
		super(); 
	}
	
	public abstract void act(long now);
	
	public <A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
		ArrayList<A> al = new ArrayList<A>();
		for (Actor a : this.getWorld().getObjects(cls)) {			
			if (this.intersects(a.getBoundsInLocal()) && a != this) {
				al.add((A) a);
			}
		}
		return al;
	}
	
	public World getWorld() {
		return (World)  getParent();
	}
	
	public <A extends Actor> A getOneIntersectingObject(Class<A> cls) {
		for (Actor a : getIntersectingObjects(cls)) {
			if (this.intersects(a.getBoundsInLocal()) && a!=this) {
				return (A) a;
			}
		}
		return null;
	}
	
	public boolean isIntersecting(Actor other) {
		for (Actor a : this.getWorld().getObjects(Actor.class)) {
			if (a.equals(other)) {	
				if (this.intersects(a.getBoundsInLocal()) && a != this) {
					return true;
				}
			}
		}
		return false;
	}
	
	public abstract double move(double dx, double dy);
	
}
