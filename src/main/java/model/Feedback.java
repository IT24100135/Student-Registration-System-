//This part is implimented By Mathusan(IT24100704)
package model;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String studentId;
    private String feedbackText;
    private long timestamp;

    public Feedback(String studentId, String feedbackText, long timestamp) {
        this.studentId = studentId;
        this.feedbackText = feedbackText;
        this.timestamp = timestamp;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return studentId + "," + feedbackText + "," + timestamp;
    }
}