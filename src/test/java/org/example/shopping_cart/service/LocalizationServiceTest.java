package org.example.shopping_cart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceTest {

    private LocalizationService service;

    @BeforeEach
    void setUp() {
        service = LocalizationService.getInstance();
        service.setLanguage("EN"); // 매 테스트 전 초기화
    }

    @Test
    void getInstance_shouldReturnSameInstance() {
        LocalizationService a = LocalizationService.getInstance();
        LocalizationService b = LocalizationService.getInstance();
        assertSame(a, b);
    }

    @Test
    void setLanguage_EN_shouldSetLangintZero() {
        service.setLanguage("EN");
        assertEquals(0, service.getLangint());
    }

    @Test
    void setLanguage_FI_shouldSetLangintOne() {
        service.setLanguage("FI");
        assertEquals(1, service.getLangint());
    }

    @Test
    void setLanguage_SV_shouldSetLangintTwo() {
        service.setLanguage("SV");
        assertEquals(2, service.getLangint());
    }

    @Test
    void setLanguage_JP_shouldSetLangintThree() {
        service.setLanguage("JP");
        assertEquals(3, service.getLangint());
    }

    @Test
    void setLanguage_AR_shouldSetLangintFour() {
        service.setLanguage("AR");
        assertEquals(4, service.getLangint());
    }

    @Test
    void setLanguage_unknown_shouldDefaultToEN() {
        service.setLanguage("XX");
        assertEquals(0, service.getLangint());
    }

    @Test
    void getLocalizedString_shouldReturnValueForValidKey() {
        service.setLanguage("EN");
        String key = service.getResourceBundle().keySet().iterator().next();
        String result = service.getLocalizedString(key);
        assertNotNull(result);
    }

    @Test
    void setCurrentPage_shouldUpdateCurrentPage() {
        service.setCurrentPage("cart");
        assertEquals("cart", service.getCurrentPage());
    }

    @Test
    void getCurrentLocale_shouldMatchLanguage() {
        service.setLanguage("FI");
        assertEquals("fi", service.getCurrentLocale().getLanguage());
    }
}
