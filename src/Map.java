import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
 
public class Map extends World {
	private ArrayList<Platform> platforms;
	private ArrayList<Player> players;
	private final double PIXELS_PER_UNIT = 15;
	private String textFile;
	/**
	 * A Map is helper a class which can self-generate a pre-designed World from a text file.
	 */
	public Map(String txtFile){
		textFile = txtFile;
		platforms = new ArrayList<Platform>();
		players = new ArrayList<Player>();
		//each row and column represents # of var pixels_per_unit
		try {
			File f = new File(txtFile);
			Scanner in = new Scanner(f);
			int row = Integer.parseInt(in.next());
			int col = Integer.parseInt(in.next());
			for (int r = 0; r < row; r++){
				for (int c = 0; c < col; c++){
					String input = in.next();
					if (input.equals("1")){
						Player temp = new Player(100, 1, c * PIXELS_PER_UNIT, r * PIXELS_PER_UNIT);
						players.add(temp);
						this.add(temp);
						HealthBar healthbar = new HealthBar(temp);
						healthbar.setVisible(false);
						this.add(healthbar);
					}
					if (input.equals("2")){
						Player temp = new Player(100, 2, c * PIXELS_PER_UNIT, r * PIXELS_PER_UNIT);
						players.add(temp);
						this.add(temp);
						HealthBar healthbar = new HealthBar(temp);
						healthbar.setVisible(false);
						this.add(healthbar);
					}
					if (input.equals("P")){
						
						Platform temp = new Platform(c * PIXELS_PER_UNIT, r * PIXELS_PER_UNIT);
						platforms.add(temp);
						this.add(temp);
					}
				}
			}
			in.close();
		}
		catch (Exception e){}
		this.start();
	}
 
 
	public void generateMap(String txtFile) {
		try {
			Scanner in = new Scanner(txtFile);
			int row = Integer.parseInt(in.next());
			int col = Integer.parseInt(in.next());
			for (int r = 0; r < row; r++){
				for (int c = 0; c < col; c++){
					String input = in.next();
					if (input.equals("P")){
						this.add(new Platform(r * PIXELS_PER_UNIT, c * PIXELS_PER_UNIT));
					}
				}
			}
			in.close();
		}
		catch (Exception e){
			System.out.println("File input error.");
		}
	} 
	public String getFile(){
		return textFile;
	}
}