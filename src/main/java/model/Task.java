package model;

import java.time.LocalDate;



public class Task implements Comparable<Task>{

    private String title;
    private String status;
    private LocalDate deadLine;
    private int priority;

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public Task(String title, String status,int priority, LocalDate deadLine)
    {
        this.title = title;
        this.status = status;
        this.deadLine = deadLine;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(o.priority, this.priority);
    }
}