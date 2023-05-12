package com.example.javafxsupport;

import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUIState {
	private static Map<String, GUIState> states = new HashMap<>();

	public Scene scene;
	public Stage stage;
	public String title;
	public HostServices hostServices;
	public SystemTray systemTray;

	private GUIState() {}

	public static GUIState getInstance() {
		return getInstance("default");
	}

	public static GUIState getInstance(String name) {
		if (states.containsKey(name)) {
			return states.get(name);
		} else {
			GUIState state = new GUIState();
			state.stage = new Stage();
			states.put(name, state);
			return state;
		}

	}


	public static GUIState getStateByName(String name) {
		return states.get(name);
	}

	public static String getTitle() {
		return getInstance().title;
	}

	public static Scene getScene() {
		return getInstance().scene;
	}

	public static Stage getStage() {
		return getInstance().stage;
	}

	public static HostServices getHostServices() {
		return getInstance().hostServices;
	}

	public static SystemTray getSystemTray() {
		return getInstance().systemTray;
	}

	public static void setTitle(final String title) {
		getInstance().title = title;
	}

	public static void setScene(final Scene scene) {
		getInstance().scene = scene;
	}

	public static void setStage(final Stage stage) {
		getInstance().stage = stage;
	}

	public static void setHostServices(HostServices hostServices) {
		getInstance().hostServices = hostServices;
	}

	public static void setSystemTray(SystemTray systemTray) {
		getInstance().systemTray = systemTray;
	}
}
