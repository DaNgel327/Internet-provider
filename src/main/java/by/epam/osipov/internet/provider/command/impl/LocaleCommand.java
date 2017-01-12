package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.resource.ConfigurationManager;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class LocaleCommand implements Command {
    @Override
    public String execute(RequestContent content) {
        String page = ConfigurationManager.getProperty("path.page.index");
        String choosedLocale = content.getParameter("lang");
        String locale;


        try {

            locale = choosedLocale + "_" + choosedLocale.toUpperCase();

            content.setAttribute("lang", locale);
            content.setSessionAttribute("lang", locale);
        } catch (IllegalArgumentException e) {

        }

        return page;
    }
}
