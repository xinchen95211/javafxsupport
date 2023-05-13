package com.example.javafxsupport;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Component;


@Component
public class run extends Application {


    private static Stage stages;

    @Override
    public void start(Stage stage) throws Exception {
        Abs_splsh sence = (Abs_splsh) GUIState.getInstance().data();
        GUIState.getInstance().stage = stage;
        stages = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
//        Scene load = new Abs_splsh().load();
        Scene load = sence.load();
        stage.setScene(load);
        stage.show();
    }
    public static void close(){
        stages.hide();
    }
}
