package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestsController {

	@FXML
	private TableView<Request> requestsTable;
	@FXML
	private TableColumn<Request, Integer> requestIDColumn; // Integer 
	@FXML
	private TableColumn<Request, String> startDateColumn;
	@FXML
	private TableColumn<Request, String> homeTechTypeColumn;
	@FXML
	private TableColumn<Request, String> homeTechModelColumn;
	@FXML
	private TableColumn<Request, String> problemDescriptionColumn;
	@FXML
	private TableColumn<Request, String> requestStatusColumn;
	@FXML
	private TableColumn<Request, String> completionDateColumn;
	@FXML
	private TableColumn<Request, String> repairPartsColumn;
	@FXML
	private TableColumn<Request, Integer> masterIDColumn; // Integer
	@FXML
	private TableColumn<Request, Integer> clientIDColumn; // Integer
	
	 @FXML
	    private TextField startDateField;
	    @FXML
	    private TextField homeTechTypeField;
	    @FXML
	    private TextField homeTechModelField;
	    @FXML
	    private TextField problemDescriptionField;
	    @FXML
	    private TextField requestStatusField;
	    @FXML
	    private TextField completionDateField;
	    @FXML
	    private TextField repairPartsField;
	    @FXML
	    private TextField masterIDField;
	    @FXML
	    private TextField clientIDField;
	
    private Connection connection;

    private ObservableList<Request> requestList;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dem", "root", "r10270707");
            requestList = FXCollections.observableArrayList();

            requestIDColumn.setCellValueFactory(new PropertyValueFactory<>("requestID"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            homeTechTypeColumn.setCellValueFactory(new PropertyValueFactory<>("homeTechType"));
            homeTechModelColumn.setCellValueFactory(new PropertyValueFactory<>("homeTechModel"));
            problemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("problemDescription"));
            requestStatusColumn.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
            completionDateColumn.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
            repairPartsColumn.setCellValueFactory(new PropertyValueFactory<>("repairParts"));
            masterIDColumn.setCellValueFactory(new PropertyValueFactory<>("masterID"));
            masterIDColumn.setCellFactory(column -> new TextFieldTableCell<>(new IntegerStringConverter()));
            clientIDColumn.setCellValueFactory(new PropertyValueFactory<>("clientID"));
            clientIDColumn.setCellFactory(column -> new TextFieldTableCell<>(new IntegerStringConverter()));



            requestsTable.setItems(requestList);

            loadRequests();
        } catch (SQLException e) {
            showAlert("Ошибка подключения", e.getMessage(), AlertType.ERROR);
        }
        
     // Добавляем слушатель для выбора записи в таблице
        requestsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Загружаем данные выбранной записи в текстовые поля
                loadSelectedRequest(newSelection);
            }
        });
    }
    
    private void loadSelectedRequest(Request selectedRequest) {
        startDateField.setText(selectedRequest.getStartDate());
        homeTechTypeField.setText(selectedRequest.getHomeTechType());
        homeTechModelField.setText(selectedRequest.getHomeTechModel());
        problemDescriptionField.setText(selectedRequest.getProblemDescription());
        requestStatusField.setText(selectedRequest.getRequestStatus());
        completionDateField.setText(selectedRequest.getCompletionDate());
        repairPartsField.setText(selectedRequest.getRepairParts());
        masterIDField.setText(String.valueOf(selectedRequest.getMasterID()));
        clientIDField.setText(String.valueOf(selectedRequest.getClientID()));
    }


    private void loadRequests() {
        String query = "SELECT * FROM requests";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            requestList.clear();

            while (rs.next()) {
                int requestID = rs.getInt("requestID");
                String startDate = rs.getDate("startDate") != null ? rs.getDate("startDate").toString() : "";
                String homeTechType = rs.getString("homeTechType");
                String homeTechModel = rs.getString("homeTechModel");
                String problemDescription = rs.getString("problemDescription");
                String requestStatus = rs.getString("requestStatus");
                String completionDate = rs.getDate("completionDate") != null ? rs.getDate("completionDate").toString() : "";
                String repairParts = rs.getString("repairParts");
                int masterID = rs.getInt("masterID");
                int clientID = rs.getInt("clientID");

                Request request = new Request(requestID, startDate, homeTechType, homeTechModel, problemDescription, requestStatus, completionDate, repairParts, masterID, clientID);
                requestList.add(request);
            }

        } catch (SQLException e) {
            showAlert("Ошибка загрузки данных", e.getMessage(), AlertType.ERROR);
        }
    }
    @FXML
    private void handleAddRequest() {
        // Получаем данные из текстовых полей
        String startDate = startDateField.getText();
        String homeTechType = homeTechTypeField.getText();
        String homeTechModel = homeTechModelField.getText();
        String problemDescription = problemDescriptionField.getText();
        String requestStatus = requestStatusField.getText();
        String completionDate = completionDateField.getText();
        String repairParts = repairPartsField.getText();
        int masterID = Integer.parseInt(masterIDField.getText());
        int clientID = Integer.parseInt(clientIDField.getText());

        try {
            // Добавляем новый запрос в базу данных
            String query = "INSERT INTO requests (startDate, homeTechType, homeTechModel, problemDescription, requestStatus, completionDate, repairParts, masterID, clientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, startDate);
            statement.setString(2, homeTechType);
            statement.setString(3, homeTechModel);
            statement.setString(4, problemDescription);
            statement.setString(5, requestStatus);
            statement.setString(6, completionDate);
            statement.setString(7, repairParts);
            statement.setInt(8, masterID);
            statement.setInt(9, clientID);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Успех", "Новый запрос успешно добавлен.", Alert.AlertType.INFORMATION);
                // Обновляем таблицу запросов
                loadRequests();
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при добавлении запроса: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        startDateField.clear();
        homeTechTypeField.clear();
        homeTechModelField.clear();
        problemDescriptionField.clear();
        requestStatusField.clear();
        completionDateField.clear();
        repairPartsField.clear();
        masterIDField.clear();
        clientIDField.clear();
    }

    @FXML
    private void handleDeleteRequest() {
        // Получаем выбранный запрос из таблицы
        Request selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest == null) {
            showAlert("Ошибка", "Пожалуйста, выберите запрос для удаления.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Удаляем выбранный запрос из базы данных
            String query = "DELETE FROM requests WHERE requestID=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, selectedRequest.getRequestID());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert("Успех", "Запрос успешно удален.", Alert.AlertType.INFORMATION);
                // Обновляем таблицу запросов
                loadRequests();
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при удалении запроса: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    
    
    @FXML
    private void handleUpdateRequest() {
        // Получаем выбранный запрос из таблицы
        Request selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest == null) {
            showAlert("Ошибка", "Пожалуйста, выберите запрос для изменения.", Alert.AlertType.ERROR);
            return;
        }

        // Получаем данные из текстовых полей
        String startDate = startDateField.getText();
        String homeTechType = homeTechTypeField.getText();
        String homeTechModel = homeTechModelField.getText();
        String problemDescription = problemDescriptionField.getText();
        String requestStatus = requestStatusField.getText();
        String completionDate = completionDateField.getText();
        String repairParts = repairPartsField.getText();
        int masterID = Integer.parseInt(masterIDField.getText());
        int clientID = Integer.parseInt(clientIDField.getText());

        try {
            // Обновляем данные выбранного запроса в базе данных
            String query = "UPDATE requests SET startDate=?, homeTechType=?, homeTechModel=?, problemDescription=?, requestStatus=?, completionDate=?, repairParts=?, masterID=?, clientID=? WHERE requestID=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, startDate);
            statement.setString(2, homeTechType);
            statement.setString(3, homeTechModel);
            statement.setString(4, problemDescription);
            statement.setString(5, requestStatus);
            statement.setString(6, completionDate);
            statement.setString(7, repairParts);
            statement.setInt(8, masterID);
            statement.setInt(9, clientID);
            statement.setInt(10, selectedRequest.getRequestID());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Успех", "Данные запроса успешно изменены.", Alert.AlertType.INFORMATION);
                // Обновляем таблицу запросов
                loadRequests();
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Ошибка", "Ошибка при изменении запроса: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleRefresh() {
        loadRequests();
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
