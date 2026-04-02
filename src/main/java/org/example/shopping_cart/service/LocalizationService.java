package org.example.shopping_cart.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

//Singleton class
//Only change make call and get resource bundle when language change and app start up, reduce lag
public class LocalizationService {
    private static Locale currentLocale;
    private static ResourceBundle resourceBundle;
    private static LocalizationService instance;
    private static Map<String, String> localizedStrings = new HashMap<>();
    private static int langint;
    private static String currentPage = "home";

    private LocalizationService() {
        currentLocale = new  Locale("en", "GB");
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("i18n.MessagesBundle", currentLocale);
        }
        for (String resourceKey : resourceBundle.keySet()) {
            localizedStrings.put(resourceKey, resourceBundle.getString(resourceKey));
        }
        langint = 0;
    }
    public static LocalizationService getInstance() {
        if (instance == null) {
            instance = new LocalizationService();
        }
        return instance;
    }

    // used to get current lang so that the time in eventDetailPopUp can be adjusted appropriately
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("i18n.MessagesBundle", currentLocale);
        }
        return resourceBundle;
    }

    public String getLocalizedString(String key) {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("i18n.MessagesBundle", currentLocale);
            for (String resourceKey : resourceBundle.keySet()) {
                localizedStrings.put(resourceKey, resourceBundle.getString(resourceKey));
            }
        }
        return localizedStrings.get(key);
    }

    public int getLangint() {
        return langint;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public void setLanguage(String language) {
        switch (language) {
            case "EN":
                currentLocale = new Locale("en", "GB");
                langint = 0;
                break;
            case "FI":
                currentLocale = new Locale("fi", "FI");
                langint = 1;
                break;
            case "SV":
                currentLocale = new Locale("sv", "SE");
                langint = 2;
                break;
            case "JP":
                currentLocale = new Locale("ja", "JP");
                langint = 3;
                break;
            case "AR":
                currentLocale = new Locale("ar", "SA");
                langint = 4;
                break;
            default:
                currentLocale = new Locale("en", "GB");
                langint = 0;
        }
        resourceBundle = ResourceBundle.getBundle("i18n.MessagesBundle", currentLocale);
        for (String resourceKey : resourceBundle.keySet()) {
            localizedStrings.put(resourceKey, resourceBundle.getString(resourceKey));
        }
    }

}
