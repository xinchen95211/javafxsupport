package com.example.javafxsupport;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.URL;

public class AbstractFxmlView implements ApplicationContextAware {
    //注解
    private final FxmlView annotation;
    private ApplicationContext applicationContext;
    //资源路径
    private Stage stage;

    private String GUIState_name;


    private GUIState guiState;

    protected AbstractFxmlView() {
        this.annotation = getAnnotation();
        this.GUIState_name = getGUIState_name(annotation);

    }

    private URL getResource(final FxmlView annotation) {
        return this.getClass().getResource(annotation.value());


    }

    public FxmlView getAnnotation(){
        final Class<? extends AbstractFxmlView> theClass = this.getClass();
        final FxmlView annotation = theClass.getAnnotation(FxmlView.class);
        return annotation;
    }
    private String getGUIState_name(final FxmlView annotation){
        return annotation.value();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext != null) {
            return;
        }
        this.applicationContext = applicationContext;
    }
    //    加载fxml
    public FXMLLoader fxmlLoader(String GUIState_name){
        FXMLLoader loader = FXMLLoaderFactory.getRoot(GUIState_name);
        loader.setControllerFactory(this::load_controller);
        return loader;
    }
    //设置控制器
    public Object load_controller(final Class<?> type){
        return this.applicationContext.getBean(type);
    }

    public void show_view(Stage stage){

        if (stage != null){
            stage.setScene(null);
            guiState = GUIState.getInstance(GUIState_name);
            guiState.stage = stage;
        }
        show_view();

    }
    public void show_view(){

        guiState = GUIState.getInstance(GUIState_name);
        if (stage == null) {
            stage = guiState.stage;
        }
        hide();
        if (stage.getScene() == null){
            FXMLLoader fxmlLoader = fxmlLoader(GUIState_name);
            final Parent parent = fxmlLoader.getRoot();
            stage.setScene(new Scene(parent));
        }
        stage.show();
    }
    public void hide(){
        if (stage != null) {stage.hide();}
    }
}
