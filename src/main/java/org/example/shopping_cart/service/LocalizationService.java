package org.example.shopping_cart.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalizationService {
    private static final String BUNDLE_BASE_NAME = "i18n.MessagesBundle";

    private static final Map<String, Locale> LOCALE_MAP = new HashMap<>();
    static {
        LOCALE_MAP.put("EN", Locale.of("en", "GB"));
        LOCALE_MAP.put("FI", Locale.of("fi", "FI"));
        LOCALE_MAP.put("SV", Locale.of("sv", "SE"));
        LOCALE_MAP.put("JP", Locale.of("ja", "JP"));
        LOCALE_MAP.put("AR", Locale.of("ar", "SA"));
    }
    private static final Locale DEFAULT_LOCALE = Locale.of("en", "GB");

    private Locale currentLocale;
    private ResourceBundle resourceBundle;
    private static LocalizationService instance;
    private final Map<String, String> localizedStrings = new HashMap<>();
    private int langint;
    private String currentPage = "home";

    private LocalizationService() {
        currentLocale = DEFAULT_LOCALE;
        loadBundle();
        langint = 0;
    }

    public static LocalizationService getInstance() {
        if (instance == null) {
            instance = new LocalizationService();
        }
        return instance;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public String getLocalizedString(String key) {
        return localizedStrings.get(key);
    }

    public int getLangint() {
        return langint;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String page) {
        this.currentPage = page;
    }

    public void setLanguage(String language) {
        currentLocale = LOCALE_MAP.getOrDefault(language, DEFAULT_LOCALE);
        langint = new java.util.ArrayList<>(LOCALE_MAP.keySet())
                .indexOf(language) == -1 ? 0 : getLangIndex(language);
        loadBundle();
    }

    private int getLangIndex(String language) {
        String[] order = {"EN", "FI", "SV", "JP", "AR"};
        for (int i = 0; i < order.length; i++) {
            if (order[i].equals(language)) return i;
        }
        return 0;
    }

    private void loadBundle() {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, currentLocale);
        localizedStrings.clear();
        for (String key : resourceBundle.keySet()) {
            localizedStrings.put(key, resourceBundle.getString(key));
        }
    }
}