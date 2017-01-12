package by.epam.osipov.internet.provider.resource;

import java.util.ResourceBundle;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class MessageManager {
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("localization");

    private MessageManager(){}

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }
}
