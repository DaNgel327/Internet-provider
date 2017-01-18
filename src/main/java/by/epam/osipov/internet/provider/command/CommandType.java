package by.epam.osipov.internet.provider.command;

import by.epam.osipov.internet.provider.command.impl.*;

/**
 * Created by Lenovo on 11.01.2017.
 */
public enum CommandType {


    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    LOCALE(new LocaleCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    REGISTER_USER(new RegisterCommand()),
    DELETE_USER(new DeleteUserCommand()),
    BAN_USER(new BanUserCommand()),
    UNBAN_USER(new UnbanUserCommand()),
    SHOW_SERVICE(new ShowServiceCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

}
