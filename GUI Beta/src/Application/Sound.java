package Application;

import javafx.scene.media.*;
import java.io.*;

public class Sound {
    private final String dir = "./src/Application/Media/";
    private final String fileType = ".mp3";
    private Media actionMusic;
    private MediaPlayer actionPlayer;
    
    //For Background music
    private static final String BACKGROUND = "./src/Application/Media/intro.mp3";
    private final Media music = new Media(new File(BACKGROUND).toURI().toString());
    private final MediaPlayer player = new MediaPlayer(music);

    public Sound() {
        player.setAutoPlay(true);
        player.setCycleCount(Integer.MAX_VALUE);
    }
    
    public Sound(String name){
        String fullDir = dir+name+fileType;
        actionMusic = new Media(new File(fullDir).toURI().toString());
        actionPlayer = new MediaPlayer(actionMusic);
    }
    
    public void playSound(Sound s){
        s.actionPlayer.play();
    }
    
    public void fadeSound(Sound s){
        s.player.setVolume(0.2);
    }

    public void loudSound(Sound s){
        s.player.setVolume(1.0);
    }
}