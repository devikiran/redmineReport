package com.hpm.redmione.report;

import java.util.ArrayList;
import java.util.List;

import com.hpm.redmine.util.DateSplitter;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.TimeEntry;

public class Simple {
	private static String redmineHost = "https://smiles.happiestminds.com/";
	private static String apiAccessKey = "ee9a3489a768a50486f6a4f5f1a8dfe129006a9b";

	public static void main(String[] args) {
		RedmineManager mgr = new RedmineManager(redmineHost, apiAccessKey);
		DateSplitter util=new DateSplitter();
		try {
			List<TimeEntry> TimeEntryList = new ArrayList<>();
		
			TimeEntryList = mgr.getTimeEntriesForIssue(30601);
			for (TimeEntry temp3 : TimeEntryList) {
			
				//System.out.println("spent date is " + temp3.getSpentOn());
				//System.out.println("comment is " + temp3.getComment()+"hrs is "+temp3.getHours());
				System.out.println("-------------------------------------------------");
				System.out.println("weekDay is "+util.generateDay(temp3.getSpentOn()));
				System.out.println("day is "+util.getDisplayDate(temp3.getSpentOn()));
				System.out.println("comment is " + temp3.getComment());
				System.out.println("spent time is "+temp3.getHours());
				System.out.println("-------------------------------------------------");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}