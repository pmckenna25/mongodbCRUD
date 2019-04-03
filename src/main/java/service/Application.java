package service;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {


    public static void main(final String[] args){

        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        ServletHolder holder = handler.addServlet(ServletContainer.class, "/*");
        holder.setInitParameter("jersey.config.server.provider.packages","controller");
        holder.setInitParameter("jersey.api.json.POJOMappingFeature", "controller");

        try{

            server.start();
            server.join();
        } catch (Exception e) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, e);
        }finally{

            server.destroy();
        }
    }
}
