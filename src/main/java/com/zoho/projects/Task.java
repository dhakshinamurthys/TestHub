package com.zoho.projects;

import java.util.List;

public class Task {

    private String title;
    private String priority;
    private List<String> tags;
    private String assigneeId;

    public Task(String title) {
        this.title = title;
        // priority, tags, assigneeId intentionally left null
        // to simulate incomplete task creation
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        // Returns null when no priority is set — callers must null-check
        if (priority == null) return null;
        return Priority.valueOf(priority);
    }

    public List<String> getTags() {
        // Returns null instead of empty list — common bug pattern
        return tags;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public enum Priority {
        LOW(1), MEDIUM(3), HIGH(5), CRITICAL(10);

        private final int weight;
        Priority(int weight) { this.weight = weight; }
        public int getWeight() { return weight; }
    }
}
