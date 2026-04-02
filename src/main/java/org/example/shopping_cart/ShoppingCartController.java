package org.example.shopping_cart;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.example.shopping_cart.dao.CartItemDAO;
import org.example.shopping_cart.dao.CartRecordDAO;
import org.example.shopping_cart.dao.LocalizationStringDAO;
import org.example.shopping_cart.dao.DatabaseConnection;
import org.example.shopping_cart.service.LocalizationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartController {

    @FXML private AnchorPane root;
    @FXML private ChoiceBox<String> langChoiceBox;
    @FXML private Button langBtn;
    @FXML private Label numLabel;
    @FXML private TextField numText;
    @FXML private Button calBtn;
    @FXML private ScrollPane itemScrollPane;
    @FXML private Button resultBtn;
    @FXML private Label resultLabel;
    @FXML private Label resultCal;

    private final List<TextField> priceFields    = new ArrayList<>();
    private final List<TextField> quantityFields = new ArrayList<>();
    private GridPane itemGrid;

    // DB에서 불러온 현재 언어의 번역 문자열 저장
    private Map<String, String> currentStrings = new HashMap<>();

    private Connection connection;
    private int currentLangIndex = 0;

    private static final String[] LANG_NAMES = {"English", "Finnish", "Swedish", "Japanese", "Arabic"};
    private static final String[] LANG_CODES = {"EN",      "FI",      "SV",      "JP",       "AR"};

    public ShoppingCartController(Connection connection) {
        this.connection = connection;
    }

    public ShoppingCartController() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.err.println("DB connection error: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        langChoiceBox.getItems().addAll(LANG_NAMES);
        langChoiceBox.setValue("English");

        itemGrid = new GridPane();
        itemGrid.setHgap(8);
        itemGrid.setVgap(8);
        itemGrid.setPadding(new Insets(8));
        itemScrollPane.setContent(itemGrid);

        // 앱 시작 시 기본 언어(EN) DB에서 로드
        loadLanguage("EN");

        langBtn.setOnAction(e   -> onConfirmLanguage());
        calBtn.setOnAction(e    -> onEnterItems());
        resultBtn.setOnAction(e -> onCalculateTotal());
    }

    // ── Language ──────────────────────────────────────────────────────────────────

    private void onConfirmLanguage() {
        int idx = langChoiceBox.getSelectionModel().getSelectedIndex();
        if (idx < 0) return;
        currentLangIndex = idx;
        loadLanguage(LANG_CODES[idx]);
    }

    /**
     * DB의 localization_strings 테이블에서 해당 언어 코드의 모든 키-값을 불러와
     * currentStrings 맵에 저장하고 UI 전체에 적용한다.
     */
    public void loadLanguage(String langCode) {
        // 1. DB에서 해당 언어 문자열 전부 로드
        Map<String, String> dbStrings = LocalizationStringDAO.getStringsByLanguage(langCode);

        if (dbStrings.isEmpty()) {
            System.err.println("No strings found in DB for language: " + langCode
                    + " — falling back to ResourceBundle.");
            // DB에 데이터 없으면 기존 LocalizationService 폴백
            LocalizationService.getInstance().setLanguage(langCode);
            for (String key : List.of(
                    "confirm.language","enter.num.items","num.items.prompt","enter.items",
                    "calculate.total","total.cost","item.header","price.header",
                    "quantity.header","item.label","price.prompt","quantity.prompt",
                    "error.invalid.number","error.no.items","error.invalid.item")) {
                String val = LocalizationService.getInstance().getLocalizedString(key);
                if (val != null) dbStrings.put(key, val);
            }
        }

        currentStrings = dbStrings;

        // 2. RTL 처리 (아랍어)
        boolean isRtl = langCode.equals("AR");
        root.setNodeOrientation(isRtl ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);

        // 3. 모든 UI 텍스트 갱신
        langBtn.setText(t("confirm.language"));
        numLabel.setText(t("enter.num.items"));
        numText.setPromptText(t("num.items.prompt"));
        calBtn.setText(t("enter.items"));
        resultBtn.setText(t("calculate.total"));
        resultLabel.setText(t("total.cost"));

        // 4. 입력 초기화
        resetItems();
    }

    /** DB에서 특정 locale + key 로 단일 번역값 조회 (원본 메서드 유지) */
    protected String getTranslation(String locale, String key) {
        String query = "SELECT value FROM localization_strings WHERE language = ? AND `key` = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, locale);
            pstmt.setString(2, key);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ── Item grid ─────────────────────────────────────────────────────────────────

    private void onEnterItems() {
        int count;
        try {
            count = Integer.parseInt(numText.getText().trim());
            if (count <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showAlert(t("error.invalid.number"));
            return;
        }
        buildItemGrid(count);
    }

    private void buildItemGrid(int count) {
        itemGrid.getChildren().clear();
        priceFields.clear();
        quantityFields.clear();

        itemGrid.add(boldLabel(t("item.header")),     0, 0);
        itemGrid.add(boldLabel(t("price.header")),    1, 0);
        itemGrid.add(boldLabel(t("quantity.header")), 2, 0);

        for (int i = 0; i < count; i++) {
            TextField priceField    = new TextField();
            TextField quantityField = new TextField();
            priceField.setPromptText(t("price.prompt"));
            quantityField.setPromptText(t("quantity.prompt"));
            priceField.setPrefWidth(90);
            quantityField.setPrefWidth(55);

            priceFields.add(priceField);
            quantityFields.add(quantityField);

            itemGrid.add(new Label(t("item.label") + " " + (i + 1)), 0, i + 1);
            itemGrid.add(priceField,                                  1, i + 1);
            itemGrid.add(quantityField,                               2, i + 1);
        }
    }

    private void resetItems() {
        itemGrid.getChildren().clear();
        priceFields.clear();
        quantityFields.clear();
        numText.clear();
        resultCal.setText("");
    }

    // ── Calculation + DB save ─────────────────────────────────────────────────────

    private void onCalculateTotal() {
        if (priceFields.isEmpty()) {
            showAlert(t("error.no.items"));
            return;
        }

        List<double[]> items = new ArrayList<>();
        double total = 0;

        for (int i = 0; i < priceFields.size(); i++) {
            try {
                double price    = Double.parseDouble(priceFields.get(i).getText().trim());
                int    quantity = Integer.parseInt(quantityFields.get(i).getText().trim());
                total += ShoppingCartCalculator.itemTotal(price, quantity);
                items.add(new double[]{price, quantity});
            } catch (NumberFormatException ex) {
                showAlert(t("error.invalid.item") + " " + (i + 1));
                return;
            }
        }

        resultCal.setText(String.format("%.2f", total));
        saveToDatabase(items, total);
    }

    private void saveToDatabase(List<double[]> items, double total) {
        try {
            String langCode = LANG_CODES[currentLangIndex];
            int recordId = CartRecordDAO.insertCartRecord(items.size(), total, langCode);
            if (recordId == -1) {
                System.err.println("DB save failed: could not insert cart record.");
                return;
            }
            CartItemDAO.insertAllCartItems(recordId, items);
            System.out.println("Saved to DB — record ID: " + recordId);
        } catch (Exception e) {
            System.err.println("DB save error: " + e.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────────

    /** currentStrings(DB에서 로드된 맵)에서 키 조회, 없으면 키 자체 반환 */
    private String t(String key) {
        return currentStrings.getOrDefault(key, key);
    }

    private Label boldLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-weight: bold;");
        return l;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}