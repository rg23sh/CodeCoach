package com.codecoach.app.editor;

public interface SubmissionService {

    void submit(Submission submission, Callback callback);

    interface Callback {
        void onSuccess(String feedback);

        void onError(String message);
    }
}
