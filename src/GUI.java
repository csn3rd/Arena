
import java.io.File;
import java.nio.file.Paths;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GUI extends Application {

	private final static double STAGE_HEIGHT = 800;
	private final static double STAGE_WIDTH = 1200;
	private Media heartCourage;
	private Media archangel;
	private Media winterspell;
	private Media oFortuna;
	private Media gameEnd;
	private static MediaPlayer mainScreenPlayer;
	private static MediaPlayer gameScreenPlayer1;
	private static MediaPlayer gameScreenPlayer2;
	private static MediaPlayer gameScreenPlayer3;
	private static MediaPlayer gameOverPlayer;
	private static Map map;
	private static boolean mapSelected;
	private static ImageView gameScreenBackground;
	private static Stage pStage;
	private static AnimationTimer gameScreenTimer;
	private static int countdown;
	private static StackPane gameStackPane;
	private static Ellipse e = new Ellipse(STAGE_WIDTH / 2, STAGE_HEIGHT / 2, STAGE_WIDTH / 4, STAGE_HEIGHT / 4);
	private static VBox vbox = new VBox();
	private static Label pauseLabel = new Label("PAUSED");
	private static Button returnToGameButton = new Button("Return to Game");
	private static Button quitButton = new Button("Exit");
	private static int lifeCount1;
	private static int lifeCount2;
	private static Label player1Died = new Label("Player 1 Died");
	private static boolean player1d;
	private static Label player2Died = new Label("Player 2 Died");
	private static boolean player2d;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		setPrimaryStage(stage);
		heartCourage = new Media(Paths.get("Hearts_of_Courage.mp3").toUri().toString());
		archangel = new Media(Paths.get("Archangel.mp3").toUri().toString());
		winterspell = new Media(Paths.get("Winterspell.mp3").toUri().toString());
		oFortuna = new Media(Paths.get("ofortuna.mp3").toUri().toString());
		gameEnd = new Media(Paths.get("GameEnd.mp3").toUri().toString());
		gameScreenPlayer1 = new MediaPlayer(heartCourage);
		gameScreenPlayer2 = new MediaPlayer(archangel);
		gameScreenPlayer3 = new MediaPlayer(winterspell);
		mainScreenPlayer = new MediaPlayer(oFortuna);
		gameOverPlayer = new MediaPlayer(gameEnd);
		mainScreen(stage);
	}

	public static Stage getPrimaryStage() {
		return pStage;
	}

	private void setPrimaryStage(Stage pStage) {
		GUI.pStage = pStage;
	}
	public static void gameOver(Stage stage, int num) {
		if (num == 1){
			lifeCount1--;
			player1Died.setStyle("-fx-text-fill: linear-gradient(from 200% 400% to 200% 400%, repeat,green 0%, black 50%);-fx-font-family: Copperplate; -fx-font-weight: bold; -fx-font-size: 30;");
			player1d = true;
			player1Died.setTranslateY(-350);
		}
		else if (num == 2){
			lifeCount2--;
			player2Died.setStyle("-fx-text-fill: linear-gradient(from 200% 400% to 200% 400%, repeat,red 0%, black 50%);-fx-font-family: Copperplate; -fx-font-weight: bold; -fx-font-size: 30;");
			player2d = true;
			player2Died.setTranslateY(-350);
		}

		if (num == 1 && lifeCount1 == 0){
			mainScreenPlayer.stop();
			gameScreenPlayer1.stop();
			gameScreenPlayer2.stop();
			gameScreenPlayer3.stop();
			gameOverPlayer.play();
			gameOverPlayer.setAutoPlay(true);
			StackPane popup = new StackPane();
			VBox vbox = new VBox();
			Label winLabel = new Label("PLAYER 2 WINS!");
			Button playAgainButton = new Button("Play Again");
			Button quitButton = new Button("Exit");
			vbox.getChildren().addAll(winLabel, playAgainButton, quitButton);
			popup.getChildren().addAll(vbox);
			winLabel.setStyle("-fx-text-fill: red; -fx-font-family: Courier; -fx-font-weight: bold; -fx-font-size: 50;");
			playAgainButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-size: 30;");
			quitButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-size: 30;");
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(150);
			Scene scene = new Scene(popup);
			stage.setScene(scene);
			stage.show();
			playAgainButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle (ActionEvent e){
					avatarScreen(stage);
					gameOverPlayer.stop();
					mainScreenPlayer.play();
				}
			});
			quitButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e) {
					mainScreen(stage);
				}
			});
		} else if (num == 2 && lifeCount2 == 0){
			mainScreenPlayer.stop();
			gameScreenPlayer1.stop();
			gameScreenPlayer2.stop();
			gameScreenPlayer3.stop();
			gameOverPlayer.play();
			gameOverPlayer.setAutoPlay(true);
			StackPane popup = new StackPane();
			VBox vbox = new VBox();
			Label winLabel = new Label("PLAYER 1 WINS!");
			Button playAgainButton = new Button("Play Again");
			Button quitButton = new Button("Exit");
			vbox.getChildren().addAll(winLabel, playAgainButton, quitButton);
			popup.getChildren().addAll(vbox);
			winLabel.setStyle("-fx-text-fill: red; -fx-font-family: Courier; -fx-font-weight: bold; -fx-font-size: 50;");
			playAgainButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-size: 30;");
			quitButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-size: 30;");
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(150);
			Scene scene = new Scene(popup);
			stage.setScene(scene);
			stage.show();
			playAgainButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle (ActionEvent e){
					avatarScreen(stage);
					gameOverPlayer.stop();
					mainScreenPlayer.play();
				}
			});
			quitButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e) {
					mainScreen(stage);
				}
			});	
		} else {
			map = new Map(map.getFile());
			gameScreen(stage);
		}

	}

	public static void mainScreen(Stage stage){
		gameOverPlayer.stop();
		gameScreenPlayer1.stop();
		gameScreenPlayer2.stop();
		gameScreenPlayer3.stop();
		mainScreenPlayer.play();
		mainScreenPlayer.setAutoPlay(true);
		stage.setResizable(false);
		stage.setHeight(STAGE_HEIGHT);
		stage.setWidth(STAGE_WIDTH);
		StackPane root = new StackPane();
		root.setAlignment( Pos.TOP_LEFT);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		ImageView background = new ImageView("file:arena_background1.jpg");
		Label title = new Label("ARENA");
		VBox vbox = new VBox();
		Button startButton = new Button("START");
		Button settingsButton = new Button("SETTINGS");
		Button aboutButton = new Button("ABOUT");
		Button quitButton = new Button("QUIT");
		vbox.getChildren().addAll(startButton, settingsButton, aboutButton, quitButton);
		root.getChildren().addAll(background, title, vbox);

		title.setStyle("-fx-text-fill: linear-gradient(from 200% 400% to 200% 400%, repeat,red 0%, black 50%);-fx-font-family: Copperplate; -fx-font-weight: bold; -fx-font-size: 300;");
		background.setFitHeight(STAGE_HEIGHT);
		background.setFitWidth(STAGE_WIDTH);

		StackPane.setAlignment(title, Pos.TOP_CENTER);

		startButton.setPrefHeight(75);
		settingsButton.setPrefHeight(75);
		aboutButton.setPrefHeight(75);
		quitButton.setPrefHeight(75);
		startButton.setStyle("-fx-font-size: 60;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		settingsButton.setStyle("-fx-font-size: 60;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		aboutButton.setStyle("-fx-font-size: 60;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		quitButton.setStyle("-fx-font-size: 60;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");

		vbox.setAlignment(Pos.CENTER);
		vbox.setTranslateY(STAGE_HEIGHT / 6);
		vbox.setSpacing(15);
		startButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				avatarScreen(stage);
			}
		});
		settingsButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				settingsScreen(stage);

			}
		});
		aboutButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				File f = new File("arena_about.html");
				Stage stage = new Stage();
				stage.setHeight(200);
				stage.setWidth(500);
				WebView webView = new WebView();
				webView.getEngine().load("file:///" + f.getAbsolutePath());
				Scene scene = new Scene(webView);
				stage.setScene(scene);
				stage.show();

			}	
		});
		quitButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				stage.close();

			}	
		});
		stage.show();

	}


	public static void settingsScreen(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		ImageView background = new ImageView("file:KnightArmory.jpg");
		background.setFitHeight(STAGE_HEIGHT);
		background.setFitWidth(STAGE_WIDTH);
		Button backButton = new Button("BACK");
		backButton.setStyle("-fx-font-size: 30;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		backButton.setPrefHeight(50);
		VBox vbox = new VBox();
		Label label = new Label();
		label.setStyle("-fx-font-size: 60;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		label.setText("Volume");
		Slider slider = new Slider();
		slider.setStyle("-fx-font-size: 60;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		vbox.getChildren().addAll(label,slider);
		vbox.setTranslateY(STAGE_HEIGHT / 2 - STAGE_HEIGHT / 8);
		vbox.setTranslateX(STAGE_WIDTH / 2 - STAGE_HEIGHT / 8);
		root.getChildren().addAll(background,backButton,vbox);

		backButton.setAlignment(Pos.TOP_LEFT);
		backButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				mainScreen(stage);				
			}			
		});
		slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				gameScreenPlayer1.setVolume((double)slider.getValue()/slider.getMax());
				gameScreenPlayer2.setVolume((double)slider.getValue()/slider.getMax());
				gameScreenPlayer3.setVolume((double)slider.getValue()/slider.getMax());
				mainScreenPlayer.setVolume((double)slider.getValue()/slider.getMax());
				gameOverPlayer.setVolume((double)slider.getValue()/slider.getMax());
			}
		});
	}

	public static void avatarScreen (Stage stage){
		player1d = false;
		player2d = false;
		mapSelected = false;
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		ImageView background = new ImageView("file:AvatarScreen_background.jpg");
		background.setFitHeight(STAGE_HEIGHT);
		background.setFitWidth(STAGE_WIDTH);
		Label label = new Label("Choose a map");
		Button playButton = new Button("P                    L                    A                    Y");
		Button backButton = new Button("BACK");
		ImageView map1 = new ImageView("file:ArenaBackground_Map1.jpg");
		ImageView map2 = new ImageView("file:ArenaBackground_Map2.jpg");
		ImageView map3 = new ImageView("file:ArenaBackground_Map3.jpg");
		label.setStyle("-fx-text-fill: linear-gradient(from 200% 400% to 200% 400%, repeat,red 0%, black 50%);-fx-font-family: Copperplate; -fx-font-weight: bold; -fx-font-size: 30;");
		label.setTranslateX(STAGE_WIDTH / 2 - 75 - 30); 
		label.setTranslateY(STAGE_HEIGHT / 50);
		map1.setFitHeight(150);
		map1.setFitWidth(150);
		map2.setFitHeight(150);
		map2.setFitWidth(150);
		map3.setFitHeight(150);
		map3.setFitWidth(150);
		map1.setTranslateX(STAGE_WIDTH / 2 - 275);
		map1.setTranslateY(STAGE_HEIGHT / 10);
		map2.setTranslateX(STAGE_WIDTH / 2 - 75);
		map2.setTranslateY(STAGE_HEIGHT / 10);
		map3.setTranslateX(STAGE_WIDTH / 2 + 125);
		map3.setTranslateY(STAGE_HEIGHT / 10);

		ImageView keyboard = new ImageView("file:keyboard.jpg");
		keyboard.setFitHeight(400);
		keyboard.setFitWidth(1075);
		root.getChildren().addAll(background, label, map1, map2, map3, playButton, backButton, keyboard);

		keyboard.setX(STAGE_WIDTH / 2 - keyboard.getFitWidth() / 2);
		keyboard.setY(STAGE_HEIGHT - keyboard.getFitHeight() - 50);

		playButton.setTranslateX(0);
		playButton.setTranslateY(STAGE_HEIGHT / 3);
		playButton.setPrefWidth(STAGE_WIDTH);
		playButton.setPrefHeight(50);
		playButton.setStyle("-fx-font-size: 40;-fx-background-color: #090a0c,linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),linear-gradient(#20262b, #191d22),radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));-fx-background-radius: 5,4,3,5;-fx-background-insets: 0,1,2,0;-fx-text-fill: red;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );-fx-font-family: Impact;-fx-text-fill: linear-gradient(red, #d0d0d0);-fx-padding: 10 20 10 20;");
		backButton.setStyle("-fx-font-size: 30;-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: red; -fx-font-family: Impact;-fx-padding: 10 20 10 20;");
		backButton.setPrefHeight(50);
		map1.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				map = new Map("Arena_Map1.txt");
				gameScreenBackground = new ImageView("file:ArenaBackground_Map1.jpg");
				mapSelected = true;
				map1.setFitHeight(200);
				map1.setFitWidth(200);
				map1.setTranslateX(STAGE_WIDTH / 2 - 300);
				map1.setTranslateY(STAGE_HEIGHT / 10 - 25);
				
				map2.setFitHeight(150);
				map2.setFitWidth(150);
				map2.setTranslateX(STAGE_WIDTH / 2 - 75);
				map2.setTranslateY(STAGE_HEIGHT / 10);
				map3.setFitHeight(150);
				map3.setFitWidth(150);
				map3.setTranslateX(STAGE_WIDTH / 2 + 125);
				map3.setTranslateY(STAGE_HEIGHT / 10);
			}	
		});
		map2.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				map = new Map("Arena_Map2.txt");
				gameScreenBackground = new ImageView("file:ArenaBackground_Map2.jpg");
				mapSelected = true;
				map2.setFitHeight(200);
				map2.setFitWidth(200);
				map2.setTranslateX(STAGE_WIDTH / 2 - 100);
				map2.setTranslateY(STAGE_HEIGHT / 10 - 25);
				
				map1.setFitHeight(150);
				map1.setFitWidth(150);
				map1.setTranslateX(STAGE_WIDTH / 2 - 275);
				map1.setTranslateY(STAGE_HEIGHT / 10);
				map3.setFitHeight(150);
				map3.setFitWidth(150);
				map3.setTranslateX(STAGE_WIDTH / 2 + 125);
				map3.setTranslateY(STAGE_HEIGHT / 10);
			}	
		});
		map3.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				map = new Map("Arena_Map3.txt");
				gameScreenBackground = new ImageView("file:ArenaBackground_Map3.jpg");
				mapSelected = true;
				map3.setFitHeight(200);
				map3.setFitWidth(200);
				map3.setTranslateX(STAGE_WIDTH / 2 + 100);
				map3.setTranslateY(STAGE_HEIGHT / 10 - 25);
				
				map1.setFitHeight(150);
				map1.setFitWidth(150);
				map1.setTranslateX(STAGE_WIDTH / 2 - 275);
				map1.setTranslateY(STAGE_HEIGHT / 10);
				map2.setFitHeight(150);
				map2.setFitWidth(150);
				map2.setTranslateX(STAGE_WIDTH / 2 - 75);
				map2.setTranslateY(STAGE_HEIGHT / 10);
			}
		});
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (mapSelected){
					lifeCount1 = 5;
					lifeCount2 = 5;
					gameScreen(stage);
				}

			}
		});
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				mainScreen(stage);

			}
		});
	}


	public static void gameScreen(Stage stage) {
		map.stop();
		gameStackPane = new StackPane();
		gameScreenPlayer1.stop();
		gameScreenPlayer2.stop();
		gameScreenPlayer3.stop();
		gameOverPlayer.stop();
		mainScreenPlayer.stop();
		HBox hbox1 = new HBox();
		for(int i = 0; i < lifeCount1; i++) {
			ImageView img = new ImageView("file:PlayerLife.gif");
			img.setFitHeight(50);
			img.setFitWidth(50);
			hbox1.getChildren().add(img);
		}
		HBox hbox2 = new HBox();
		for(int i = 0; i < lifeCount2; i++) {
			ImageView img = new ImageView("file:PlayerLife.gif");
			img.setFitHeight(50);
			img.setFitWidth(50);
			hbox2.getChildren().add(img);
		}
		hbox1.setTranslateX(STAGE_WIDTH/4 - 100);
		hbox2.setTranslateX(3 * STAGE_WIDTH / 4 - 100);
		hbox1.setTranslateY(STAGE_HEIGHT - 75);
		hbox2.setTranslateY(STAGE_HEIGHT - 75);
		if (map.getFile().equals("Arena_Map1.txt")){
			gameScreenPlayer1.play();
			gameScreenPlayer1.setAutoPlay(true);
		}
		else if (map.getFile().equals("Arena_Map2.txt")){
			gameScreenPlayer2.play();
			gameScreenPlayer2.setAutoPlay(true);
		}
		else if (map.getFile().equals("Arena_Map3.txt")){
			gameScreenPlayer3.play();
			gameScreenPlayer3.setAutoPlay(true);
		}
		countdown = 250;
		Label countdownText = new Label();
		countdownText.setStyle("-fx-text-fill: linear-gradient(from 200% 400% to 200% 400%, repeat,red 0%, black 50%);-fx-font-family: Copperplate; -fx-font-weight: bold; -fx-font-size: 300");
		gameScreenTimer = new AnimationTimer(){
			@Override
			public void handle(long now){
				countdown--;
				if (countdown == 240){
					if(player1d) {
						gameStackPane.getChildren().add(player1Died);
					} else if(player2d) {
						gameStackPane.getChildren().add(player2Died);
					}
				}
				else if (countdown == 180){
					countdownText.setText("3");
				}
				else if (countdown == 120){
					countdownText.setText("2");
				}
				else if (countdown == 60){
					countdownText.setText("1");
				}
				else if (countdown == 0){
					this.stop();
					if(player1d) {
						player1d = false;
						gameStackPane.getChildren().remove(player1Died);
					} else if(player2d) {
						player2d = false;
						gameStackPane.getChildren().remove(player2Died);
					}
					countdownText.setText("");
					map.start();
				}
			}
		};
		gameScreenTimer.start();
		gameScreenBackground.setFitHeight(STAGE_HEIGHT);
		gameScreenBackground.setFitWidth(STAGE_WIDTH);
		gameStackPane.getChildren().addAll(gameScreenBackground, map, countdownText, hbox1, hbox2);
		Scene scene = new Scene(gameStackPane);
		stage.setScene(scene);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode()==KeyCode.ESCAPE)
				{
					popupScreen(stage);

				}
				if (k.getCode() == KeyCode.TAB) {
					gameStackPane.getChildren().remove(e);
					gameStackPane.getChildren().remove(vbox);
					if (countdown > 0){
						gameScreenTimer.start();
					}
					else {
						map.start();
					}

				}
			}
		});
	}

	public static void popupScreen(Stage stage) {
		map.stop();
		gameScreenTimer.stop();
		if(!gameStackPane.getChildren().contains(e) || !gameStackPane.getChildren().contains(vbox)) {
			gameStackPane.getChildren().addAll(e, vbox);
		}
		e.setOpacity(20);
		e.setFill(Color.BLACK);
		pauseLabel.setStyle("-fx-text-fill: white; -fx-font-family: Courier; -fx-font-weight: bold; -fx-font-size: 50;");
		returnToGameButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-size: 30;");
		quitButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-size: 30;");
		if(!vbox.getChildren().contains(pauseLabel) || !vbox.getChildren().contains(returnToGameButton) || !vbox.getChildren().contains(quitButton)) {
			vbox.getChildren().addAll(pauseLabel, returnToGameButton, quitButton);
		}

		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(25);
		returnToGameButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle (ActionEvent event){
				gameStackPane.getChildren().remove(e);
				gameStackPane.getChildren().remove(vbox);
				if (countdown > 0){
					gameScreenTimer.start();
				}
				else {
					map.start();
				}
			}
		});

		quitButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				mainScreen(stage);
			}
		});

	}
}