package com.rossw01.chess.Widgets;

import javafx.scene.Node;
import javafx.scene.control.*;

public class ReconnectAlert extends Alert {
    public ReconnectAlert() {
        super(Alert.AlertType.ERROR);
        setTitle("Server Connection Failed");
        setHeaderText("Failed to connect to the remote game server.");

        ButtonType reconnectButton = new ButtonType("Reconnect");
        ButtonType continueOfflineButton = new ButtonType("Continue Offline");
        ButtonType quitButton = new ButtonType("Quit");

        getButtonTypes().setAll(reconnectButton, continueOfflineButton, quitButton);
    }
}
