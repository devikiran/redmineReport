package com.hpm.redmione.report;

import java.util.Date;

public class ReportDTO {
public Date date;
public Day day;
public String comment;
public double hoursSpent;
public String projectName;



public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Day getDay() {
	return day;
}
public void setDay(Day day) {
	this.day = day;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public double getHoursSpent() {
	return hoursSpent;
}
public void setHoursSpent(double hoursSpent) {
	this.hoursSpent = hoursSpent;
}
public String getProjectName() {
	return projectName;
}
public void setProjectName(String projectName) {
	this.projectName = projectName;
}


}
