import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
 
public class World extends Pane {
	private AnimationTimer at;
	private ArrayList<Actor> actors;
	
	/**
	 * A World is an extension of a Pane that holds Actors (which are custom ImageView objects). 
	 * Since a World is a Pane it already keeps track of its children Nodes and this list can be 
	 * accessed via a call to the Pane's getChildren(). A World contains an AnimationTimer which 
	 * runs its own act() method as well as the act() method for every Actor in the World.
	 */
	public World() {
		at = new MyAnimationTimer();
		actors = new ArrayList<Actor>();
		
	}
	
	public void death(int num) {
		this.stop();
		GUI.gameOver(GUI.getPrimaryStage(), num);
	}
 
	
	public <A extends Actor> ArrayList<A> getObjects(java.lang.Class<A> cls) {
		ArrayList<A> arr = new ArrayList<A>();
		for (int i=0; i<actors.size(); i++) {
			if (actors.get(i).getClass().equals(cls)) {
				arr.add((A) actors.get(i));
			}
		}
		return arr;
	}
	
	public void add(Actor actor) {
		actors.add(actor);
		this.getChildren().add(actor);
	}
	
	public void remove(Actor actor) {
		actors.remove(actor);
		this.getChildren().remove(actor);
	}
	
	public void start() {
		at.start();
	}
	
	public void stop() {
		at.stop();
	} 
	
	private class MyAnimationTimer extends AnimationTimer {
		private double fps = 1;
		private int count = 0;
		@Override
		public void handle(long now) {
			if (count++ >= fps) {
				count = 0;
				try {
					for (Actor a : actors) {
						a.act(now);
					}
				} catch(ConcurrentModificationException e) {}
			}
		}
	}
}