package com.harini.todoapp;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task {
    private String name;
    private String description;
    private Date endDate;
    private Priority priority;

    public Task(String name, String description, Date endDate, Priority priority) {
        this.name = name;
        this.description = description;
        this.endDate = endDate;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return getDaysLeft();
    }

    public String getPriority() {
        return priority.toString();
    }

    private String getDaysLeft() {
        long diffInMillies = endDate.getTime() - new Date().getTime();
        long daysLeft = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (daysLeft < 0) {
            return "already due";
        } else {
            return daysLeft + " days left";
        }
    }
}
