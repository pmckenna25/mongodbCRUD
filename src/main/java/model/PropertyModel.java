package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class PropertyModel {

    private Properties properties = new Properties();

    public PropertyModel() {

        try {
            properties.load(new FileInputStream(getProperty()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private File getProperty() throws URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("system.properties")).toURI());
         return new File(path.toString());
    }

    public Properties getProperties() {
        return properties;
    }

    public String getDatabaseName(){
        return this.properties.getProperty("my.dbname");
    }
}
