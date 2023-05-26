package com.example.javafxsupport;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpringJavaFX{
    private static Class<? extends SpringJavaFX> aClass;
    private static final CompletableFuture<Runnable> splashIsShowing = new CompletableFuture<>();
    static Class<run> runClass = run.class;
    static Abs_splsh init_splsh;
    static ConfigurableApplicationContext applicationContext;

    static Class< ?extends AbstractFxmlController> cclass;

    private static String[] arg = new String[0];
//    public static void main(String[] args) {
//        CompletableFuture<Runnable> first = CompletableFuture.supplyAsync(() -> {
//            Application.launch(runClass,arg);
//            return () -> {};
//        });
//        first.thenAcceptBothAsync(splashIsShowing,(r1, r2) -> {
//            Platform.runLater(r2);
//        });
//
//
//
//    }
    public static void init(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() ->
                SpringApplication.run(aClass, arg), executor
        ).whenComplete((ctx, throwable) -> {
            if (throwable != null) {
                executor.shutdown();
            } else {
                Platform.runLater(() -> {
                    launchApplicationView(ctx);
                });
            }
        }).thenAcceptBothAsync(splashIsShowing, (ctx, closeSplash) -> {
            executor.shutdown();
            Platform.runLater(closeSplash);
        });

        splashIsShowing.complete(() -> {
            showView(cclass);
            close();
        });
    }


    private static void launchApplicationView(final ConfigurableApplicationContext ctx) {
        applicationContext = ctx;
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
        init();
        cclass = controller;
        init_splsh = Objects.requireNonNullElseGet(init, Abs_splsh::new);

        GUIState.getInstance().setData(init_splsh);
        Application.launch(runClass,arg);



    }
    public static void showView(Class<? extends AbstractFxmlController> window) {
            AbstractFxmlController view = applicationContext.getBean(window);
            view.show_view();
    }
    public static <T extends AbstractFxmlController> T getView(Class<T> window) {
        return applicationContext.getBean(window);
    }
    public static <T extends AbstractFxmlController> T show_and_getView(Class<T> window){
        T view = getView(window);
        view.show_view();
        return view;
    }

    public static void close(){
//        Platform.runLater(() -> {
            try {
                runClass.getMethod("close").invoke(arg);
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println(e.getMessage());
            }
//        });
    }
}
