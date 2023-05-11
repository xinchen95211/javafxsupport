package com.example.newbox.Annotation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.net.URL;


public abstract class AbstractFxmlController implements ApplicationContextAware {

    //注解
    private final FxmlController annotation;
    private ApplicationContext applicationContext;
    //资源路径
    private Stage stage;
    private final URL resource;

    private String GUIState_name;


    private GUIState guiState;

    protected AbstractFxmlController() {
        annotation = getAnnotation();
        resource = getResource(annotation);
        GUIState_name = getGUIState_name(annotation);


    }

    private URL getResource(final FxmlController annotation) {
        return getClass().getResource(annotation.value());
    }

    public FxmlController getAnnotation(){
        final Class<? extends AbstractFxmlController> theClass = this.getClass();
        final FxmlController annotation = theClass.getAnnotation(FxmlController.class);
        return annotation;
    }
    private String getGUIState_name(final FxmlController annotation){
        return annotation.value().substring(0,annotation.value().lastIndexOf("."));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext != null) {
            return;
        }
        this.applicationContext = applicationContext;
    }
//    加载fxml
    public FXMLLoader fxmlLoader(final URL resource){
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(this::load_controller);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return loader;
    }
    //设置控制器
    public Object load_controller(final Class<?> type){
        return applicationContext.getBean(type);
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

        if (stage.getScene() == null){
            FXMLLoader fxmlLoader = fxmlLoader(resource);
            final Parent parent = fxmlLoader.getRoot();
            stage.setScene(new Scene(parent));
        }

        stage.show();
    }
    public void hide(){
        if (stage != null) stage.hide();
    }




}
