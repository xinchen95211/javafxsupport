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

	public Object data() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object data;

	private GUIState() {}

	public static GUIState getInstance() {
		return getInstance("default");
	}

	public static GUIState getInstance(String name) {
		if (states.containsKey(name)) {
			return states.get(name);
		} else {
			GUIState state = new GUIState();
			if (! "default".equals(name)){
				state.stage = new Stage();
			}
			states.put(name, state);
			return state;
		}

	}


	public static GUIState getStateByName(String name) {
		return states.get(name);
	}

	public static String getTitle(String name) {
		return getInstance(name).title;
	}

	public static Scene getScene(String name) {
		return getInstance(name).scene;
	}

	public static Stage getStage(String name) {
		return getInstance(name).stage;
	}

	public static HostServices getHostServices(String name) {
		return getInstance(name).hostServices;
	}

	public static SystemTray getSystemTray(String name) {
		return getInstance(name).systemTray;
	}

	public static void setTitle(final String name,final String title) {
		getInstance(name).title = title;
	}

	public static void setScene(final String name,final Scene scene) {
		getInstance(name).scene = scene;
	}

	public static void setStage(final String name,final Stage stage) {
		getInstance(name).stage = stage;
	}

	public static void setHostServices(String name,HostServices hostServices) {
		getInstance(name).hostServices = hostServices;
	}

	public static void setSystemTray(String name,SystemTray systemTray) {
		getInstance(name).systemTray = systemTray;
	}
}
