package application;

public class Comment {
    private int commentID;
    private String message;
    private int masterID;
    private int requestID;

    public Comment(int commentID, String message, int masterID, int requestID) {
        this.commentID = commentID;
        this.message = message;
        this.masterID = masterID;
        this.requestID = requestID;
    }

    // Getters
    public int getCommentID() {
        return commentID;
    }

    public String getMessage() {
        return message;
    }

    public int getMasterID() {
        return masterID;
    }

    public int getRequestID() {
        return requestID;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
}