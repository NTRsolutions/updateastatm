package com.atm.ast.astatm.model;

/**
 * @author AST Inc. 12/8/2015.
 */
public class TaskListDataModel {

    String taskId;
    String taskName;
    String taskLastUpdated;

    public String getTaskLastUpdated() {
        return taskLastUpdated;
    }

    public void setTaskLastUpdated(String taskLastUpdated) {
        this.taskLastUpdated = taskLastUpdated;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
