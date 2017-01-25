package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import org.apache.log4j.Logger;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class LocaleCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(LocaleCommand.class);

    @Override
    public String execute(RequestContent content) {

        // String page = ConfigurationManager.getProperty("path.page.index");
        String choosedLocale = content.getParameter("lang");
        String locale;

        locale = choosedLocale + "_" + choosedLocale.toUpperCase();
        content.setAttribute("lang", locale);
        content.setSessionAttribute("lang", locale);

        return "/";
    }
}
