package com.example.javafxsupport;

import javafx.application.Application;
import javafx.application.Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SpringJavaFX {
    private static Class<? extends SpringJavaFX> aClass;
    private static final CompletableFuture<Runnable> splashIsShowing = new CompletableFuture<>();
    static Class<run> runClass = run.class;
    static Abs_splsh init_splsh;
    static ConfigurableApplicationContext applicationContext;
    private static String[] arg = new String[0];
    public static void main(String[] args) {
        CompletableFuture<Runnable> first = CompletableFuture.supplyAsync(() -> {
            Application.launch(runClass,arg);
            return () -> {};
        });
        first.thenAcceptBothAsync(splashIsShowing,(r1, r2) -> {
            Platform.runLater(r2);
        });



    }
    public static void lunch(Class<? extends SpringJavaFX> bclacc,
                             Class<? extends AbstractFxmlController> controller,
                             String[] args){
        lunch(bclacc,controller,new Abs_splsh(),args);
    }
    public static void lunch(Class<? extends SpringJavaFX> bclacc,
                             Class<? extends AbstractFxmlController> controller,
                             Abs_splsh init,
                             String[] args){

        aClass = bclacc;
        arg = args;
        CompletableFuture<Runnable> first = CompletableFuture.supplyAsync(() -> {
            init_splsh = Objects.requireNonNullElseGet(init, Abs_splsh::new);
            GUIState.getInstance().setData(init_splsh);
            Application.launch(runClass,arg);
            return () -> {};
        });
        first.thenAcceptBothAsync(splashIsShowing,(r1, r2) -> {
            Platform.runLater(r2);
        });
        applicationContext = SpringApplication.run(bclacc, args);
        showView(controller);
        close();

    }
    public static void showView(Class<? extends AbstractFxmlController> window) {
        Platform.runLater(() -> {
            AbstractFxmlController view = applicationContext.getBean(window);
            view.show_view();
        });
    }

    public static void close(){
        Platform.runLater(() -> {
            try {
                runClass.getMethod("close").invoke(arg);
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
