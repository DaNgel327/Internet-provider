package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ServiceException;
import by.epam.osipov.internet.provider.service.AccessService;

/**
 * Created by Lenovo on 30.01.2017.
 */
public class ChangeLoginCommand implements Command {

    @Override
    public String execute(RequestContent content) throws CommandException {

        try {
            return tryExecute(content);
        } catch (ServiceException e) {
            throw new CommandException("Error while trying to execute Change password command", e);
        }
    }

    private String tryExecute(RequestContent content) throws ServiceException {

        String curLogin = content.getParameter("currentLogin");
        String newLogin = content.getParameter("newLogin");
        String password = content.getParameter("password");

        String login = content.getSessionAttribute("user").toString();

        if(!login.equals(curLogin)){
            content.setAttribute("passwordChanged",false);
        }

        /*
        AccessService accessService = new AccessService();
        if (accessService.isOldPassCorrect(login, curPass)) {
            accessService.changePassword(login, curPass, newPass);
            content.setSessionAttribute("done", true);
        } else {
            content.setSessionAttribute("done", false);
            return "/";
        }
*/
        return "/";
    }
}
