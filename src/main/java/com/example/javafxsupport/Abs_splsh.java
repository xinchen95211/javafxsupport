package com.example.javafxsupport;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

class LoadingView extends StackPane {
    private Label label;
    int i = 0;
    public LoadingView() {
        label = new Label("Loading");
        label.setFont(Font.font(40));
        getChildren().add(label);
        // 开始动画
        animateDots();
    }
    private void animateDots() {
        // 创建一个timeline来循环动画
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.3), event -> addDot())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void addDot() {
        String text = label.getText();
        label.setText(text + ".");
        i++;
        if (i == 5){
            i = 0;
            label.setText("loading");
        }
    }
}
public class Abs_splsh {
    public Scene load(){
        LoadingView view = new LoadingView();
        Scene scene = new Scene(view, 200, 100);
        scene.setFill(null);
        return scene;
    }
}

