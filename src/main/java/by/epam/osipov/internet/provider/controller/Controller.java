package by.epam.osipov.internet.provider.controller;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.command.factory.CommandFactory;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.exception.CommandException;

import by.epam.osipov.internet.provider.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {


     private static final Logger LOGGER = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = CommandFactory.defineCommand(requestContent);
        String page = null;

        try {
            page = command.execute(requestContent);
        } catch (CommandException e) {
           for(StackTraceElement element: e.getStackTrace()){
               LOGGER.error(element);
           }
        }

        requestContent.insertValues(request);
        response.sendRedirect(page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = CommandFactory.defineCommand(requestContent);
        String page = null;

        try {
            page = command.execute(requestContent);
        } catch (CommandException e) {
            LOGGER.error("SKA OSHIBKA",e);
        }


        requestContent.insertValues(request);
        getServletContext().getRequestDispatcher(page).forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (ConnectionPool.isInitialized()) {
            ConnectionPool.getInstance().closeAll();
        }
    }
}