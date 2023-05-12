package com.example.javafxsupport;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class FXMLLoaderFactory {
    //获取classpath路径
    private static Logger LOGGER = LoggerFactory.getLogger(FXMLLoaderFactory.class);
    private static final Map<String, FXMLLoader> fxmlLoaders  = new HashMap<>();

    public static FXMLLoader getRoot(String fxmlName) {
        if (!fxmlLoaders.containsKey(fxmlName)) {
            FXMLLoader fxmlLoader = loadFXML(fxmlName);
            if (fxmlLoader != null){
                fxmlLoaders.put(fxmlName, fxmlLoader);
            }else {
                return null;
            }
        }
        return fxmlLoaders.get(fxmlName);
    }

    private static FXMLLoader loadFXML(String fxmlPath) {
        try {
            ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();
            Resource resource = resourceLoader.getResource("classpath:" + fxmlPath);
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.load();
            return fxmlLoader;
        } catch (Exception e) {
            LOGGER.error("没有找到对应的 + "+fxmlPath+"文件");
            return null;
        }
    }
}
