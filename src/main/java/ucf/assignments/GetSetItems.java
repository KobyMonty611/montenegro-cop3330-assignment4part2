/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Koby Montenegro
 */

package ucf.assignments;

public class GetSetItems
{
    //Primary attributes of an object
    String Description = "";
    String DueDate = "";
    String completed = "Incomplete";

    //Constructor to initialize an object
    public GetSetItems(String DueDate, String Description)
    {
        this.DueDate = DueDate;
        this.Description = Description;
    }

    //GET AND SET FOR DESCRIPTION
    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String Description)
    {
        this.Description = Description;
    }

    //GET AND SET FOR DUEDATE
    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    //GET AND SET FOR COMPLETED
    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

}
