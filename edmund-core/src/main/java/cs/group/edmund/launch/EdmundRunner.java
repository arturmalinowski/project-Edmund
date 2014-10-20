package cs.group.edmund.launch;

import cs.group.edmund.servlet.IndexServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static java.lang.Integer.parseInt;

public class EdmundRunner {

    private final ServletContextHandler context;
    private final Server server;

    public EdmundRunner(int port) {
        server = new Server(port);

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/edmund");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new IndexServlet()), "");

        try {
            server.start();
        } catch (Exception e) {
            System.out.println("Edmund server failed to start! " + e);
        }

    }

    public void stopServer() {
        try {
            server.stop();
        } catch (Exception e) {
            System.out.println("Failed to stop server = " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        new EdmundRunner(parseInt(webPort));

    }


}
