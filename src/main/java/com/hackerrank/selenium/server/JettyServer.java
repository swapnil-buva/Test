package com.hackerrank.selenium.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
    private Server server;

    public void start() {
        try {
            //1. Creating the server on port 8080
            server = new Server(8080);

            //2. Creating the WebAppContext for the created content
            WebAppContext ctx = new WebAppContext();
            ctx.setResourceBase("website");
            ctx.setContextPath("/");
            ctx.addServlet("com.hackerrank.selenium.server.StatsServlet", "/home.html");

            //2. Setting our secured handler
            server.setHandler(ctx);

            //3. Starting the Server
            server.start();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}