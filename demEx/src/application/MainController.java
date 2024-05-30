//package application;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.sql.*;
//
//public class MainController {
//
//    @FXML
//    private TableView<Request> requestsTable;
//    @FXML
//    private TableColumn<Request, Integer> requestIDColumn;
//    @FXML
//    private TableColumn<Request, String> startDateColumn;
//    @FXML
//    private TableColumn<Request, String> homeTechTypeColumn;
//    @FXML
//    private TableColumn<Request, String> homeTechModelColumn;
//    @FXML
//    private TableColumn<Request, String> problemDescriptionColumn;
//    @FXML
//    private TableColumn<Request, String> requestStatusColumn;
//    @FXML
//    private TableColumn<Request, String> completionDateColumn;
//    @FXML
//    private TableColumn<Request, String> repairPartsColumn;
//    @FXML
//    private TableColumn<Request, Integer> masterIDColumn;
//    @FXML
//    private TableColumn<Request, Integer> clientIDColumn;
//    
//    @FXML
//    private TextField requestIDField;
//    @FXML
//    private TextField startDateField;
//    @FXML
//    private TextField homeTechTypeField;
//    @FXML
//    private TextField homeTechModelField;
//    @FXML
//    private TextField problemDescriptionField;
//    @FXML
//    private TextField requestStatusField;
//    @FXML
//    private TextField completionDateField;
//    @FXML
//    private TextField repairPartsField;
//    @FXML
//    private TextField masterIDField;
//    @FXML
//    private TextField clientIDField;
//    
//    @FXML
//    private Button addButton;
//    @FXML
//    private Button editButton;
//    @FXML
//    private Button deleteButton;
//
//    @FXML
//    private TableView<Comment> commentsTable;
//    @FXML
//    private TableColumn<Comment, Integer> commentIDColumn;
//    @FXML
//    private TableColumn<Comment, String> messageColumn;
//    @FXML
//    private TableColumn<Comment, Integer> commentMasterIDColumn;
//    @FXML
//    private TableColumn<Comment, Integer> commentRequestIDColumn;
//
//    @FXML
//    private TextField commentIDField;
//    @FXML
//    private TextField messageField;
//    @FXML
//    private TextField commentMasterIDField;
//    @FXML
//    private TextField commentRequestIDField;
//
//    @FXML
//    private Button addCommentButton;
//    @FXML
//    private Button editCommentButton;
//    @FXML
//    private Button deleteCommentButton;
//
//    private final ObservableList<Request> requestData = FXCollections.observableArrayList();
//    private final ObservableList<Comment> commentData = FXCollections.observableArrayList();
//
//    private Connection connection;
//
//    @FXML
//    private void initialize() {
//        // Initialize the request table with the columns
//        requestIDColumn.setCellValueFactory(new PropertyValueFactory<>("requestID"));
//        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        homeTechTypeColumn.setCellValueFactory(new PropertyValueFactory<>("homeTechType"));
//        homeTechModelColumn.setCellValueFactory(new PropertyValueFactory<>("homeTechModel"));
//        problemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("problemDescription"));
//        requestStatusColumn.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
//        completionDateColumn.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
//        repairPartsColumn.setCellValueFactory(new PropertyValueFactory<>("repairParts"));
//        masterIDColumn.setCellValueFactory(new PropertyValueFactory<>("masterID"));
//        clientIDColumn.setCellValueFactory(new PropertyValueFactory<>("clientID"));
//
//        // Initialize the comment table with the columns
//        commentIDColumn.setCellValueFactory(new PropertyValueFactory<>("commentID"));
//        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
//        commentMasterIDColumn.setCellValueFactory(new PropertyValueFactory<>("masterID"));
//        commentRequestIDColumn.setCellValueFactory(new PropertyValueFactory<>("requestID"));
//
//        // Add data to tables
//        requestsTable.setItems(requestData);
//        commentsTable.setItems(commentData);
//
//        // Connect to the database
//        connectToDatabase();
//        loadRequestsFromDatabase();
//        loadCommentsFromDatabase();
//    }
//
//    private void connectToDatabase() {
//        try {
//            // Change these details as per your database configuration
//            String url = "jdbc:mysql://localhost:3306/dem";
//            String user = "root";
//            String password = "r10270707";
//            connection = DriverManager.getConnection(url, user, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadRequestsFromDatabase() {
//        String query = "SELECT * FROM requests";
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(query)) {
//            while (resultSet.next()) {
//                Request request = new Request(
//                        resultSet.getInt("requestID"),
//                        resultSet.getDate("startDate").toString(),
//                        resultSet.getString("homeTechType"),
//                        resultSet.getString("homeTechModel"),
//                        resultSet.getString("problemDescription"),
//                        resultSet.getString("requestStatus"),
//                        resultSet.getDate("completionDate") != null ? resultSet.getDate("completionDate").toString() : null,
//                        resultSet.getString("repairParts"),
//                        resultSet.getInt("masterID"),
//                        resultSet.getInt("clientID")
//                );
//                requestData.add(request);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadCommentsFromDatabase() {
//        String query = "SELECT * FROM comments";
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(query)) {
//            while (resultSet.next()) {
//                Comment comment = new Comment(
//                        resultSet.getInt("commentID"),
//                        resultSet.getString("message"),
//                        resultSet.getInt("masterID"),
//                        resultSet.getInt("requestID")
//                );
//                commentData.add(comment);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void handleAddButtonAction() {
//        String query = "INSERT INTO requests (startDate, homeTechType, homeTechModel, problemDescription, requestStatus, completionDate, repairParts, masterID, clientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setDate(1, Date.valueOf(startDateField.getText()));
//            preparedStatement.setString(2, homeTechTypeField.getText());
//            preparedStatement.setString(3, homeTechModelField.getText());
//            preparedStatement.setString(4, problemDescriptionField.getText());
//            preparedStatement.setString(5, requestStatusField.getText());
//            preparedStatement.setDate(6, completionDateField.getText().isEmpty() ? null : Date.valueOf(completionDateField.getText()));
//            preparedStatement.setString(7, repairPartsField.getText());
//            preparedStatement.setInt(8, Integer.parseInt(masterIDField.getText()));
//            preparedStatement.setInt(9, Integer.parseInt(clientIDField.getText()));
//            preparedStatement.executeUpdate();
//
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int requestID = generatedKeys.getInt(1);
//                Request newRequest = new Request(
//                        requestID,
//                        startDateField.getText(),
//                        homeTechTypeField.getText(),
//                        homeTechModelField.getText(),
//                        problemDescriptionField.getText(),
//                        requestStatusField.getText(),
//                        completionDateField.getText(),
//                        repairPartsField.getText(),
//                        Integer.parseInt(masterIDField.getText()),
//                        Integer.parseInt(clientIDField.getText())
//                );
//                requestData.add(newRequest);
//                clearRequestFields();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void handleEditButtonAction() {
//        Request selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
//        if (selectedRequest != null) {
//            String query = "UPDATE requests SET startDate = ?, homeTechType = ?, homeTechModel = ?, problemDescription = ?, requestStatus = ?, completionDate = ?, repairParts = ?, masterID = ?, clientID = ? WHERE requestID = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setDate(1, Date.valueOf(startDateField.getText()));
//                preparedStatement.setString(2, homeTechTypeField.getText());
//                preparedStatement.setString(3, homeTechModelField.getText());
//                preparedStatement.setString(4, problemDescriptionField.getText());
//                preparedStatement.setString(5, requestStatusField.getText());
//                preparedStatement.setDate(6, completionDateField.getText().isEmpty() ? null : Date.valueOf(completionDateField.getText()));
//                preparedStatement.setString(7, repairPartsField.getText());
//                preparedStatement.setInt(8, Integer.parseInt(masterIDField.getText()));
//                preparedStatement.setInt(9, Integer.parseInt(clientIDField.getText()));
//                preparedStatement.setInt(10, selectedRequest.getRequestID());
//                preparedStatement.executeUpdate();
//
//                selectedRequest.setStartDate(startDateField.getText());
//                selectedRequest.setHomeTechType(homeTechTypeField.getText());
//                selectedRequest.setHomeTechModel(homeTechModelField.getText());
//                selectedRequest.setProblemDescription(problemDescriptionField.getText());
//                selectedRequest.setRequestStatus(requestStatusField.getText());
//                selectedRequest.setCompletionDate(completionDateField.getText());
//                selectedRequest.setRepairParts(repairPartsField.getText());
//                selectedRequest.setMasterID(Integer.parseInt(masterIDField.getText()));
//                selectedRequest.setClientID(Integer.parseInt(clientIDField.getText()));
//                requestsTable.refresh();
//                clearRequestFields();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @FXML
//    private void handleDeleteButtonAction() {
//        int selectedIndex = requestsTable.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            Request selectedRequest = requestsTable.getItems().get(selectedIndex);
//            String query = "DELETE FROM requests WHERE requestID = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setInt(1, selectedRequest.getRequestID());
//                preparedStatement.executeUpdate();
//                requestsTable.getItems().remove(selectedIndex);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @FXML
//    private void handleAddCommentButtonAction() {
//        String query = "INSERT INTO comments (message, masterID, requestID) VALUES (?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, messageField.getText());
//            preparedStatement.setInt(2, Integer.parseInt(commentMasterIDField.getText()));
//            preparedStatement.setInt(3, Integer.parseInt(commentRequestIDField.getText()));
//            preparedStatement.executeUpdate();
//
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int commentID = generatedKeys.getInt(1);
//                Comment newComment = new Comment(
//                        commentID,
//                        messageField.getText(),
//                        Integer.parseInt(commentMasterIDField.getText()),
//                        Integer.parseInt(commentRequestIDField.getText())
//                );
//                commentData.add(newComment);
//                clearCommentFields();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void handleEditCommentButtonAction() {
//        Comment selectedComment = commentsTable.getSelectionModel().getSelectedItem();
//        if (selectedComment != null) {
//            String query = "UPDATE comments SET message = ?, masterID = ?, requestID = ? WHERE commentID = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, messageField.getText());
//                preparedStatement.setInt(2, Integer.parseInt(commentMasterIDField.getText()));
//                preparedStatement.setInt(3, Integer.parseInt(commentRequestIDField.getText()));
//                preparedStatement.setInt(4, selectedComment.getCommentID());
//                preparedStatement.executeUpdate();
//
//                selectedComment.setMessage(messageField.getText());
//                selectedComment.setMasterID(Integer.parseInt(commentMasterIDField.getText()));
//                selectedComment.setRequestID(Integer.parseInt(commentRequestIDField.getText()));
//                commentsTable.refresh();
//                clearCommentFields();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @FXML
//    private void handleDeleteCommentButtonAction() {
//        int selectedIndex = commentsTable.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            Comment selectedComment = commentsTable.getItems().get(selectedIndex);
//            String query = "DELETE FROM comments WHERE commentID = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setInt(1, selectedComment.getCommentID());
//                preparedStatement.executeUpdate();
//                commentsTable.getItems().remove(selectedIndex);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void clearRequestFields() {
//        requestIDField.clear();
//        startDateField.clear();
//        homeTechTypeField.clear();
//        homeTechModelField.clear();
//        problemDescriptionField.clear();
//        requestStatusField.clear();
//        completionDateField.clear();
//        repairPartsField.clear();
//        masterIDField.clear();
//        clientIDField.clear();
//    }
//
//    private void clearCommentFields() {
//        commentIDField.clear();
//        messageField.clear();
//        commentMasterIDField.clear();
//        commentRequestIDField.clear();
//    }
//}
