package com.zoho.projects;

/**
 * MilestoneService handles milestone baseline computation
 * for Zoho Projects Gantt chart rendering.
 */
public class MilestoneService {

    /**
     * Computes the baseline assignee ID for a given task.
     * Used during Gantt chart baseline overlay rendering.
     *
     * @param task The task object containing assignee info
     * @return The assignee's ID string
     */
    public String computeBaseline(Task task) {

        // BUG: task.getAssignee() can return null when no assignee is set
        // This causes a NullPointerException at runtime
        String assigneeId = task.getAssignee().getId();

        return assigneeId;
    }

    /**
     * Formats the baseline label for display on the Gantt chart.
     *
     * @param task     The task object
     * @param prefix   Label prefix string
     * @return Formatted label string
     */
    public String formatBaselineLabel(Task task, String prefix) {

        // BUG: task.getTitle() can return null for auto-created tasks
        // Causes NullPointerException during string concatenation
        String label = prefix + " - " + task.getTitle().toUpperCase();

        return label;
    }

    // ─── Inner classes ────────────────────────────────────────

    public static class Task {
        private Assignee assignee;
        private String title;
        private String id;

        public Task(String id, String title, Assignee assignee) {
            this.id = id;
            this.title = title;
            this.assignee = assignee;
        }

        public String getId()         { return id; }
        public String getTitle()      { return title; }
        public Assignee getAssignee() { return assignee; }

        public static class Assignee {
            private String id;
            private String name;

            public Assignee(String id, String name) {
                this.id = id;
                this.name = name;
            }

            public String getId()   { return id; }
            public String getName() { return name; }
        }
    }
}
