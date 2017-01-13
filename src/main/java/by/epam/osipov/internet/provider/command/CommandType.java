package by.epam.osipov.internet.provider.command;

import by.epam.osipov.internet.provider.command.impl.LocaleCommand;
import by.epam.osipov.internet.provider.command.impl.LoginCommand;
import by.epam.osipov.internet.provider.command.impl.LogoutCommand;
import by.epam.osipov.internet.provider.command.impl.ShowUsersCommand;

/**
 * Created by Lenovo on 11.01.2017.
 */
public enum CommandType {


    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    LOCALE(new LocaleCommand()),
    SHOW_USERS(new ShowUsersCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

}
