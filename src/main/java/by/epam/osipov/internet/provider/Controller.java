package by.epam.osipov.internet.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;


@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            LOGGER.info("doGet method");
            String lang = request.getParameter("lang");
            LOGGER.info("language - " + lang.toUpperCase());

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");

            request.setAttribute("locale", lang+"_"+lang.toUpperCase());
            dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("doPost method");
    }

}