package com.example.javafxsupport;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载fxml静态工厂类
 */


@Component
public class FXMLLoaderFactory {
    //获取classpath路径
    private static Logger LOGGER = LoggerFactory.getLogger(FXMLLoaderFactory.class);
    private static final Map<String, FXMLLoader> fxmlLoaders  = new HashMap<>();

    public static FXMLLoader getRoot(
                                Class<?> aclacc,
                                String fxmlName) {
        if (!fxmlLoaders.containsKey(fxmlName)) {
            FXMLLoader fxmlLoader = loadFXML(aclacc,fxmlName);
            if (fxmlLoader != null){
                fxmlLoaders.put(fxmlName, fxmlLoader);
            }else {
                return null;
            }
        }
        return fxmlLoaders.get(fxmlName);
    }

    private static FXMLLoader loadFXML(
            Class<?> aclacc,
            String fxmlPath) {
        try {
//            ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();
//           Resource resource = resourceLoader.getResource("classpath:/"+ fxmlPath);
//            LOGGER.error(resource.getURL().toString());

            FXMLLoader fxmlLoader = new FXMLLoader(aclacc.getResource(fxmlPath));
            return fxmlLoader;
        } catch (Exception e) {
//            System.out.println(e);
            LOGGER.error(e.getMessage());
            LOGGER.error("没有找到对应的 + "+fxmlPath+"文件");
            return null;
        }
    }
}
