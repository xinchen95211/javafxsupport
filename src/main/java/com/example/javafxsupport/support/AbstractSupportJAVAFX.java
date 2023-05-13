package com.example.javafxsupport.support;

import com.example.javafxsupport.Abs_splsh;
import com.example.javafxsupport.AbstractFxmlController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CompletableFuture;


/**
 * 有bug，无法自动扫描，已切到新的启动方式
 */
@Deprecated
public class AbstractSupportJAVAFX extends Application {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractSupportJAVAFX.class);
    private static ConfigurableApplicationContext applicationContext;

    static Abs_splsh init_splsh;
    private static Class<?> newBoxApplicationClass;
    private final CompletableFuture<Runnable> splashIsShowing = new CompletableFuture<>();
    private static Class<? extends AbstractFxmlController> loginControllerClass;
    private static String[] savedArgs = new String[0];
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
                        SpringApplication.run(newBoxApplicationClass,savedArgs)).
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
        AbstractFxmlController view = (AbstractFxmlController) applicationContext.getBean(window);
        view.show_view();
    }
    public static void launcher(Class<?> BoxApplicationClass,
                                Class<? extends AbstractFxmlController> loginController,
                                String[] args){
        launcher(BoxApplicationClass,loginController,new Abs_splsh(),args);

    }
    public static void launcher(Class<?> BoxApplicationClass,
                                Class<? extends AbstractFxmlController> loginController,
                                 Abs_splsh is,
                                String[] args){
        savedArgs = args;
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
