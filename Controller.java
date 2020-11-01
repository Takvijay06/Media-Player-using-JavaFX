package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	@FXML
	MediaPlayer Player;
	public MenuItem about;
	public MenuItem plays;
	public MenuItem ExitWindow;
	public MenuItem sot;
	public MenuItem bot;
	public MenuItem OpenFile;
	public Button NextButton;
	public Button PlayButton;
	public Button PrevButton;
	public Slider SliderBar;
	public MediaView MV;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		PrevButton.setOnAction(event -> {
			double d = Player.getCurrentTime().toSeconds();
			d=d-10;
			if(d>10){
				Player.seek(new Duration(d*1000));}
			else {
				Player.seek(new Duration(d*0));}
		});

		ExitWindow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});

		about.setOnAction(event -> {
			aboutus();
		});

          NextButton.setOnAction(event -> {
          	  double time = Player.getMedia().getDuration().toSeconds();
	          double d = Player.getCurrentTime().toSeconds();
	          d=d+10;
	          if(d<=time){
	          Player.seek(new Duration(d*1000));}
	          else {
		          Player.seek(new Duration(d*0));}
          });

		sot.setOnAction(event -> {
			double time = Player.getMedia().getDuration().toSeconds();
			double d = Player.getCurrentTime().toSeconds();
			d=d+10;
			if(d<=time){
				Player.seek(new Duration(d*1000));}
			else {
				Player.seek(new Duration(d*0));}
		});

		bot.setOnAction(event -> {
			double d = Player.getCurrentTime().toSeconds();
			d=d-10;
			if(d>10){
				Player.seek(new Duration(d*1000));}
			else {
				Player.seek(new Duration(d*0));}
		});

		plays.setOnAction(event -> {
			MediaPlayer.Status status = Player.getStatus();

				Player.play();
				PlayButton.setText("Pause");

		});

          OpenFile.setOnAction(event -> {
          	try {
	            System.out.println("Open the file !!");
	            FileChooser chooser = new FileChooser();
	            File file = chooser.showOpenDialog(null);
	            Media m = new Media(file.toURI().toURL().toString());
	            if(Player!=null){
	            	Player.dispose();
	            }
	            Player = new MediaPlayer(m);
	            MV.setMediaPlayer(Player);
	            Player.setOnReady(()->{
	            	SliderBar.setMin(0);
	            	SliderBar.setMax(Player.getMedia().getDuration().toSeconds());
	            	SliderBar.setValue(0);

	            });

	            Player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
		            @Override
		            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
			            Duration d = Player.getCurrentTime();
			            SliderBar.setValue(d.toSeconds());
		            }
	            });
	            SliderBar.valueProperty().addListener(new ChangeListener<Number>() {
		            @Override
		            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			            if (SliderBar.isPressed()) {
				            double val = SliderBar.getValue();
				            Player.seek(new Duration(val * 1000));
			            }
		            }
	            });
            }catch (Exception e){
          		e.printStackTrace();
            }
          });
          PlayButton.setOnAction(event -> {
          	    MediaPlayer.Status status = Player.getStatus();

          	    if(status==MediaPlayer.Status.PLAYING){
          	    	Player.pause();
          	    	PlayButton.setText("Play");
                }
          	    else {
          	    	Player.play();
          	    	PlayButton.setText("Pause");
                }
          });
	}

	private void aboutus() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("MEDIA PLAYER VJ");
		alert.setContentText("This is basic media player to play video and audio files, having additional Various functionalities. This was created by Vijay Tak.");
		alert.show();
	}
}
