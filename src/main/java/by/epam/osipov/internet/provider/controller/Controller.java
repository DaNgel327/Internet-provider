package by.epam.osipov.internet.provider.controller;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.command.factory.CommandFactory;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "controller")
public class Controller extends HttpServlet {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Controller.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = CommandFactory.defineCommand(requestContent);
        String page = null;

        try {
            page = command.execute(requestContent);
        } catch (CommandException e) {
            LOGGER.error(e);
            //requestContent.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            //page = MappingManager.ERROR_PAGE_500;
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

            LOGGER.error(e);
            //requestContent.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            // page = MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
        }


        requestContent.insertValues(request);
        getServletContext().getRequestDispatcher(page).forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        /*
        if (ConnectionPool.isInitialized()) {
            ConnectionPool.getInstance().closeAll();
        }
        */
    }
}