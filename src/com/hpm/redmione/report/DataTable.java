package com.hpm.redmione.report;

import java.util.Comparator;
import java.util.Date;

public class DataTable implements Comparable<DataTable> {
	private Date date;
	private String Comment;
	private Float hours;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public Float getHours() {
		return hours;
	}

	public void setHours(Float hours) {
		this.hours = hours;
	}

	@Override
	public int compareTo(DataTable tableObj) {
		
		this.date.compareTo(tableObj.getDate());
		return this.date.compareTo(tableObj.getDate());
		
	}

	public static Comparator<DataTable> dataTableComparator = new Comparator<DataTable>() {

		public int compare(DataTable tableObj1, DataTable tableObj2) {

			Date Date1 = tableObj1.getDate();
			Date Date2 = tableObj2.getDate();

			// ascending order
			return Date1.compareTo(Date2);

		}

	};
}
