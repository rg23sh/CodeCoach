package com.codecoach.app.editor;

public class Submission {

    private String questionId;
    private String questionTitle;
    private String questionDescription;
    private String code;
    private long submittedAt;

    public Submission(String questionId, String questionTitle, String questionDescription,
                      String code, long submittedAt) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionDescription = questionDescription;
        this.code = code;
        this.submittedAt = submittedAt;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public String getCode() {
        return code;
    }

    public long getSubmittedAt() {
        return submittedAt;
    }
}
