package pt.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {
    private SoundManager() { }

    private static MediaPlayer mp;

    public static MediaPlayer play(String filename) {
        try {
            var url = SoundManager.class.getResource("sounds/" + filename);
            if (url == null) return null;
            String path = url.toExternalForm();
            Media music = new Media(path);
            if ((mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING))
                mp.stop();
            mp = new MediaPlayer(music);
            mp.setVolume(0.2);
            mp.setCycleCount(MediaPlayer.INDEFINITE);
            mp.setStartTime(Duration.ZERO);
            mp.setStopTime(music.getDuration());
            mp.setAutoPlay(true);
        } catch (Exception e) {
            return null;
        }
        return mp;
    }
}