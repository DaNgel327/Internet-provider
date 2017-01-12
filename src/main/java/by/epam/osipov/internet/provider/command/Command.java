package by.epam.osipov.internet.provider.command;

import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.exception.CommandException;

/**
 * Created by Lenovo on 11.01.2017.
 */
public interface Command {

    String execute(RequestContent content) throws CommandException;

}
