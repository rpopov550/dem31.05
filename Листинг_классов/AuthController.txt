package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {

    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logBut;

    private Connection connection;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dem", "root", "r10270707");
            System.out.println("Succes");
        } catch (SQLException e) {
            showAlert("Ошибка подключения", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        String login = loginTextField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Ошибка", "Пожалуйста, заполните все поля", AlertType.ERROR);
            return;
        }

        String query = "SELECT * FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                showAlert("Успех", "Вход выполнен!", AlertType.INFORMATION);
                openNewWindow(event, "Main.fxml", "Главная форма");
                // Дополнительные действия после успешного входа
            } else {
                showAlert("Ошибка", "Неверный логин или пароль", AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Ошибка", e.getMessage(), AlertType.ERROR);
        }
    }
    
    @FXML
    public void handleRegisterButtonAction(ActionEvent event) {
        // При нажатии на кнопку "Зарегистрироваться" загружаем форму Register.fxml
//        openNewWindow(event, "Register.fxml", "Форма регистрации");
    }
    
    private void openNewWindow(ActionEvent event, String fxmlFile, String title) {
        try {
            // Загружаем FXML-файл и создаем новую сцену
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);

            // Получаем текущее окно
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Закрываем текущее окно
            oldStage.close();

            // Показываем новое окно
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
