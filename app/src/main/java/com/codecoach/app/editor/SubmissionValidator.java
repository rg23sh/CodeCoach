package com.codecoach.app.editor;

import java.util.Stack;

public class SubmissionValidator {

    public static final int MAX_LENGTH = 5000;

    public static String validate(String code, String starterCode) {
        if (code == null || code.trim().isEmpty()) {
            return "Please write some code before submitting.";
        }

        if (starterCode != null && code.trim().equals(starterCode.trim())) {
            return "You have not changed the starter code yet.";
        }

        if (code.length() > MAX_LENGTH) {
            return "Your code is too long (max " + MAX_LENGTH + " characters).";
        }

        if (!bracketsBalanced(code)) {
            return "Your brackets ( ), { } or [ ] do not match.";
        }

        return null;
    }

    public static boolean bracketsBalanced(String code) {
        Stack<Character> stack = new Stack<>();
        int i = 0;

        while (i < code.length()) {
            char c = code.charAt(i);

            if (c == '/' && i + 1 < code.length() && code.charAt(i + 1) == '/') {
                while (i < code.length() && code.charAt(i) != '\n') {
                    i++;
                }
                continue;
            }

            if (c == '/' && i + 1 < code.length() && code.charAt(i + 1) == '*') {
                int end = code.indexOf("*/", i + 2);
                if (end == -1) {
                    return false;
                }
                i = end + 2;
                continue;
            }

            if (c == '"' || c == '\'') {
                char quote = c;
                i++;
                while (i < code.length() && code.charAt(i) != quote) {
                    if (code.charAt(i) == '\\') {
                        i++;
                    }
                    i++;
                }
                if (i >= code.length()) {
                    return false;
                }
                i++;
                continue;
            }

            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if ((c == ')' && open != '(') || (c == '}' && open != '{') || (c == ']' && open != '[')) {
                    return false;
                }
            }
            i++;
        }

        return stack.isEmpty();
    }
}
