package com.technical.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author Tom Longdon
 */
@Entity
public class Activity {

    @Id
    @GeneratedValue
    private Long activityId;

    private Long contactId;
    private String title;
    private String notes;
    private ActivityType activityType;
    private LocalDate dueDate;

    protected Activity(){
    }

    public Activity(Long contactId, String title, String notes, ActivityType activityType, LocalDate dueDate) {
        this.contactId = contactId;
        this.title = title;
        this.notes = notes;
        this.activityType = activityType;
        this.dueDate = dueDate;
    }

    public Long getActivityId() {
        return activityId;
    }


    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId){
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public String getActivityTypeString(){
        return activityType.toString();
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", contact=" + contactId +
                //", contact=" + contact.toString() +
                ", title='" + title + '\'' +
                ", notes='" + notes + '\'' +
                ", activityType=" + activityType +
                ", dueDate=" + dueDate +
                '}';
    }
}
