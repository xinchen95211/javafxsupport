package com.example.javafxsupport;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Objects;


/**
 * 控制器类自动加载注入类
 */
public abstract class AbstractFxmlController implements ApplicationContextAware {


    //注解
    private final FxmlController annotation;
    private ApplicationContext applicationContext;
    //资源路径
    public Stage stage;

    private String GUIState_name;


    private GUIState guiState;

    /**
     * 初始化一些数据
     */
    protected AbstractFxmlController() {
        this.annotation = getAnnotation();
        this.GUIState_name = getGUIState_name(annotation);

    }

    /**
     * 弃用，改用classpath获取
     * @param annotation
     * @return
     */
    @Deprecated
    private URL getResource(final FxmlController annotation) {
            return this.getClass().getResource(annotation.value());
    }

    public FxmlController getAnnotation(){
        final Class<? extends AbstractFxmlController> theClass = this.getClass();
        final FxmlController annotation = theClass.getAnnotation(FxmlController.class);
        return annotation;
    }

    /**
     * 把注解的value设置为gui管理的名称
     * @param annotation
     * @return
     */
    private String getGUIState_name(final FxmlController annotation){
        return annotation.value();
    }

    /**
     * 注入application对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext != null) {
            return;
        }
        this.applicationContext = applicationContext;
    }

    /**
     * 加载fxmlloader设置控制器
     * @param GUIState_name
     * @return
     */
    public FXMLLoader fxmlLoader(String GUIState_name){
        FXMLLoader loader = FXMLLoaderFactory.getRoot(this.getClass(),GUIState_name);

        try {
            Objects.requireNonNull(loader).setControllerFactory(this::load_controller);
            loader.load();
            return loader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Object load_controller(final Class<?> type){
        return applicationContext.getBean(type);
    }

    /**
     * 展示页面
     * @param stage
     */
    public void show_view(Stage stage){
        if (stage != null){
            stage.setScene(null);
            guiState = GUIState.getInstance(GUIState_name);
            guiState.stage = stage;
        }
        show_view();

    }
    /**
     * 展示页面
     */
    public void show_view(){
        firstinit();
        hide();
        stage.show();
    }

    /**
     * 初始化启动
     */
    private void firstinit() {
        guiState = GUIState.getInstance(GUIState_name);
        if (stage == null) {
            stage = guiState.stage;
        }
        if (stage.getScene() == null){
            FXMLLoader fxmlLoader = fxmlLoader(GUIState_name);
            fxmlLoader.setController(this);
            final Parent parent = fxmlLoader.getRoot();
            stage.setScene(new Scene(parent));
        }
    }

    /**
     * 隐藏界面
     */

    public void hide(){
        if (stage != null) {stage.hide();}
    }

    /**
     * 关闭界面
     */
    public void close(){
        if (stage != null) {stage.close();}
    }

    public void shutdown(){
        Platform.exit();
        SpringApplication.exit(applicationContext);
    }



}
