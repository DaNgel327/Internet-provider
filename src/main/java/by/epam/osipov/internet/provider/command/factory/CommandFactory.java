package by.epam.osipov.internet.provider.command.factory;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.command.CommandType;
import by.epam.osipov.internet.provider.command.impl.EmptyCommand;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.resource.MessageManager;
import org.apache.log4j.Logger;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class CommandFactory {

    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

    public  static Command defineCommand(RequestContent request){
        Command current = new EmptyCommand();
        String action = request.getParameter("command");
        LOGGER.info(action);
        if(action == null || action.isEmpty()){
            return current;
        }
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCommand();
        } catch (IllegalArgumentException e){
            request.setAttribute("wrong action", action + MessageManager.getProperty("message.wrongaction"));
        }

        return current;
    }

}
