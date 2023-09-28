package com.rossw01.chess.Chat;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Objects;
public class ChatController {

    @FXML
    public Button sound1;
    @FXML
    private TextField messageField;
    @FXML
    private ListView<String> messageHistory;

    private boolean isSoundEnabled = true;

    public void onMuteButtonClick() {
        isSoundEnabled = !isSoundEnabled;
    }

    public void onButton1Click() {
        if (!isSoundEnabled) {
            return;
        }
        String url = Objects.requireNonNull(getClass().getResource("/Sounds/wth.mp3")).toExternalForm();
        Media sound = new Media(url);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void onSendMessage() {
        String message = messageField.getText();
        if (Objects.equals(message, "")) {
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();

        message = String.format("[%s] %s", dtf.format(now), message);

        messageHistory.getItems().add(message);
        messageField.setText("");

        messageHistory.scrollTo(messageHistory.getItems().size());
    }
}
