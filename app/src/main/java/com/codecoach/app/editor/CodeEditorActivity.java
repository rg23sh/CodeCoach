package com.codecoach.app.editor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codecoach.app.R;

public class CodeEditorActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_ID = "questionId";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DIFFICULTY = "difficulty";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_STARTER_CODE = "starterCode";

    private String questionId;
    private String title;
    private String difficulty;
    private String description;
    private String starterCode;

    private EditText codeInput;
    private TextView errorText;
    private ProgressBar progressBar;
    private Button submitButton;

    private SubmissionService submissionService = new FakeSubmissionService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        loadQuestion();

        TextView titleText = findViewById(R.id.titleText);
        TextView difficultyText = findViewById(R.id.difficultyText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        codeInput = findViewById(R.id.codeInput);
        errorText = findViewById(R.id.errorText);
        progressBar = findViewById(R.id.progressBar);
        submitButton = findViewById(R.id.submitButton);

        titleText.setText(title);
        difficultyText.setText("Difficulty: " + difficulty);
        descriptionText.setText(description);

        if (savedInstanceState == null) {
            codeInput.setText(starterCode);
        }

        submitButton.setOnClickListener(v -> submitCode());
    }

    private void loadQuestion() {
        questionId = getIntent().getStringExtra(EXTRA_QUESTION_ID);

        if (questionId == null) {
            questionId = "two-sum";
            title = "Two Sum";
            difficulty = "Easy";
            description = "Given an array of integers nums and an integer target, return the indices "
                    + "of the two numbers that add up to target.\n\n"
                    + "Example: nums = [2,7,11,15], target = 9  ->  [0,1]";
            starterCode = "class Solution {\n"
                    + "    public int[] twoSum(int[] nums, int target) {\n"
                    + "        \n"
                    + "    }\n"
                    + "}";
            return;
        }

        title = getIntent().getStringExtra(EXTRA_TITLE);
        difficulty = getIntent().getStringExtra(EXTRA_DIFFICULTY);
        description = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        starterCode = getIntent().getStringExtra(EXTRA_STARTER_CODE);
    }

    private void submitCode() {
        String code = codeInput.getText().toString();

        String error = SubmissionValidator.validate(code, starterCode);
        if (error != null) {
            errorText.setText(error);
            errorText.setVisibility(View.VISIBLE);
            return;
        }
        errorText.setVisibility(View.GONE);

        Submission submission = new Submission(questionId, title, description, code,
                System.currentTimeMillis());

        setLoading(true);
        submissionService.submit(submission, new SubmissionService.Callback() {
            @Override
            public void onSuccess(String feedback) {
                if (isFinishing()) {
                    return;
                }
                setLoading(false);
                new AlertDialog.Builder(CodeEditorActivity.this)
                        .setTitle("AI Feedback")
                        .setMessage(feedback)
                        .setPositiveButton("OK", null)
                        .show();
            }

            @Override
            public void onError(String message) {
                if (isFinishing()) {
                    return;
                }
                setLoading(false);
                Toast.makeText(CodeEditorActivity.this, "Submission failed: " + message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        submitButton.setEnabled(!loading);
        codeInput.setEnabled(!loading);
    }
}
