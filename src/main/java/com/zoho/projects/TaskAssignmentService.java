package com.zoho.projects;

import java.util.List;

public class TaskAssignmentService {

    /**
     * Assigns a task to a user and sends a notification.
     * BUG: Does not check for null task or null user before accessing properties.
     */
    public String assignTask(Task task, User user) {
        // NPE risk 1: task could be null
        String taskName = task.getTitle().toUpperCase();

        // NPE risk 2: user.getEmail() could be null if user has no email set
        String email = user.getEmail().trim();

        // NPE risk 3: task.getTags() could return null instead of empty list
        int tagCount = task.getTags().size();

        return "Assigned " + taskName + " to " + email + " with " + tagCount + " tags.";
    }

    /**
     * Calculates the workload score for a user across all assigned tasks.
     * BUG: Does not handle null task list or tasks with null priority.
     */
    public int calculateWorkload(User user) {
        List<Task> tasks = user.getAssignedTasks();

        int score = 0;
        for (Task t : tasks) {
            // NPE risk: t.getPriority() returns null for tasks with no priority set
            score += t.getPriority().getWeight();
        }
        return score;
    }
}
