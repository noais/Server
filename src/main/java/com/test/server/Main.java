package com.test.server;

import org.apache.commons.cli.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author David Ferreira
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String PREFIX = "./server.sh ";
    private static final String HEADER = "\nServer:";
    private static final String USAGE = "-p <port> ";

    private static final String EXAMPLES = "\nUsage examples:\n"
            + "1: "
            + PREFIX + "-p 8081\n\n";
    private static final String FOOTER = "For more instructions, please contact david.vicenteferreira@gmail.com.";


    /**
     * Print help message of the program.
     *
     * @param options Command line arguments.
     * @param msg     Message to be displayed.
     */
    private static void printHelp(final Options options, final String msg) {
        if (msg.length() != 0) {
            logger.error(msg);
        }
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(150, PREFIX + USAGE, HEADER, options, EXAMPLES + FOOTER);
    }

    /**
     * Main program to run master.
     *
     * @param args Options to configure master instance.
     */
    public static void main(final String... args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "help", false, "Print this usage information.");
        options.addOption("p", "port", true, "Port of the server.");

        CommandLine commandLine = null;
        try {
            // Parse the program arguments
            commandLine = parser.parse(options, args);
        } catch (ParseException ex) {
            logger.error("There was a problem processing the input arguments.", ex);
            return;
        }

        // Show help text
        if (commandLine.hasOption('h')) {
            printHelp(options, "");
            return;
        }

        // No options
        if (commandLine.getOptions().length == 0) {
            printHelp(options, "");
            return;
        }

        // Get master port
        Integer port = null;
        if (commandLine.hasOption("p")) {
            port = Integer.parseInt(commandLine.getOptionValue("p"));
        } else {
            printHelp(options, "Please specify the server port.");
            return;
        }

        // Get server configured
        Server server = getServer(port);

        // Start server
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error("There was a problem starting the server.", e);
        }
    }

    private static Server getServer(final int port) {
        // Configure resource
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(Resources.class.getPackage().getName());
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(CORSResponseFilter.class);

        // Set servlet
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);

        Server server = new Server(port);
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        servletContextHandler.addServlet(servletHolder, "/*");
        server.setHandler(servletContextHandler);

        return server;
    }
}
