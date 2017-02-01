package by.epam.osipov.internet.provider.controller;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.command.factory.CommandFactory;
import by.epam.osipov.internet.provider.command.impl.GenerateCSVCommand;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.generator.CoverageFileGenerator;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/controller")
public class Controller extends HttpServlet {


    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (ConnectionPool.isInitialized()) {
            ConnectionPool.getInstance().closeAll();
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = CommandFactory.defineCommand(requestContent);
        String page = null;

        try {
            page = command.execute(requestContent);
        } catch (CommandException e) {
            LOGGER.error("Error while trying to execute command ", e);
        }


        requestContent.insertValues(request);
        getServletContext().getRequestDispatcher(page).forward(request, response);


    }
}

