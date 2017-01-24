package by.epam.osipov.internet.provider.command.factory;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.command.CommandType;
import by.epam.osipov.internet.provider.command.impl.EmptyCommand;
import by.epam.osipov.internet.provider.content.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class CommandFactory {


    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND_PARAM = "command";

    public static Command defineCommand(RequestContent request) {
        Command current = new EmptyCommand();
        String action = request.getParameter(COMMAND_PARAM);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCommand();

        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument " + e);
        }

        return current;
    }

}
