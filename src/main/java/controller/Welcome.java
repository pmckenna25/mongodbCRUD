package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

@Path("")
public class Welcome {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getMessage() throws FileNotFoundException, URISyntaxException {

        return new FileInputStream(getFile());
    }

    private File getFile() throws URISyntaxException {
        java.nio.file.Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("readme.html")).toURI());
        return new File(path.toString());
    }
}
