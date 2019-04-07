package model;

import sun.applet.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyModel {

    Properties properties = new Properties();

    public PropertyModel() throws IOException, URISyntaxException {

        properties.load(new FileInputStream(getProperty()));
    }

    private File getProperty() throws URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource("system.properties").toURI());
         //String path = Main.class.getClassLoader().getResource("system.properties").getPath();
         return new File(path.toString());
    }

    public Properties getProperties() {
        return properties;
    }
}
