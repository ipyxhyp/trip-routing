package org.ptr.routing.exception;

public class Failure extends RuntimeException{

    private ReasonType reason;
    private Integer code;
    private String detailedMessage;

    public Failure(String message, int failureCode, ReasonType reason) {
        super(message);
        this.code = failureCode;
        this.reason = reason;
        detailedMessage = message;
    }

    public enum ReasonType {

        CODE_400(400),
        CODE_500(500);

        private int code;

        ReasonType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
}
