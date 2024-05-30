package application;

public class Request {
    private int requestID;
    private String startDate;
    private String homeTechType;
    private String homeTechModel;
    private String problemDescription;
    private String requestStatus;
    private String completionDate;
    private String repairParts;
    private int masterID;
    private int clientID;

    public Request(int requestID, String startDate, String homeTechType, String homeTechModel, String problemDescription, String requestStatus, String completionDate, String repairParts, int masterID, int clientID) {
        this.requestID = requestID;
        this.startDate = startDate;
        this.homeTechType = homeTechType;
        this.homeTechModel = homeTechModel;
        this.problemDescription = problemDescription;
        this.requestStatus = requestStatus;
        this.completionDate = completionDate;
        this.repairParts = repairParts;
        this.masterID = masterID;
        this.clientID = clientID;
    }

    public int getRequestID() {
        return requestID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getHomeTechType() {
        return homeTechType;
    }

    public String getHomeTechModel() {
        return homeTechModel;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public String getRepairParts() {
        return repairParts;
    }

    public int getMasterID() {
        return masterID;
    }

    public int getClientID() {
        return clientID;
    }
}
