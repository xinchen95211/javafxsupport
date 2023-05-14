package com.example.javafxsupport;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Deprecated
public class AbstractSupportJAVAFX extends Application {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractSupportJAVAFX.class);
    private static ConfigurableApplicationContext applicationContext;

    static Abs_splsh init_splsh;
    private static Class<?> newBoxApplicationClass;
    private final CompletableFuture<Runnable> splashIsShowing = new CompletableFuture<>();
    private static Class<? extends AbstractFxmlController> loginControllerClass;
    private static String[] saveargs = new String[0];
    @Override
    public void start(Stage stage) {

        Scene scene = init_splsh.load();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        splashIsShowing.complete(()->{
            showView(loginControllerClass);
            stage.hide();
            stage.setScene(null);
        });
    }

    @Override
    public void init() {
        CompletableFuture.supplyAsync(() ->
                SpringApplication.run(newBoxApplicationClass,saveargs)).
                whenComplete((ctx, throwable) -> {
                    lunch_ctx(ctx);
        }).thenAcceptBothAsync(splashIsShowing, (ctx, closeSplash) -> {
            Platform.runLater(closeSplash);
        });
    }
    public void lunch_ctx(final ConfigurableApplicationContext context){
        applicationContext = context;
    }
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
    public static void showView(Class<? extends AbstractFxmlController> window) {
        AbstractFxmlController view =applicationContext.getBean(window);
        view.show_view();
    }

    /**
     * 启动器
     * @param BoxApplicationClass spingboot的启动类
     * @param loginController 初始启动页面
     * @param args args
     */
    public static void launcher(Class<?> BoxApplicationClass,
                                Class<? extends AbstractFxmlController> loginController,
                                String[] args){
        launcher(BoxApplicationClass,loginController,new Abs_splsh(),args);

    }

    /**
     *
     * @param BoxApplicationClass spingboot的启动类
     * @param loginController 初始启动页面
     * @param is 启动动画类
     * @param args args
     */
    public static void launcher(Class<?> BoxApplicationClass,
                                Class<? extends AbstractFxmlController> loginController,
                                 Abs_splsh is,
                                String[] args){
        saveargs = args;
        if (is != null){
            init_splsh = is;
        }else {
            init_splsh = new Abs_splsh();
        }
        newBoxApplicationClass = BoxApplicationClass;
        loginControllerClass = loginController;
        Application.launch(AbstractSupportJAVAFX.class,args);
    }
}
