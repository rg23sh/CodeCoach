package com.codecoach.app.editor;

import android.os.Handler;
import android.os.Looper;

public class FakeSubmissionService implements SubmissionService {

    @Override
    public void submit(Submission submission, Callback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            String feedback = "Logic: Looks correct for the examples.\n"
                    + "Time complexity: O(n)\n"
                    + "Space complexity: O(n)\n"
                    + "Edge cases: Check an empty array.\n"
                    + "Suggestion: Use clearer variable names.";
            callback.onSuccess(feedback);
        }, 2000);
    }
}
