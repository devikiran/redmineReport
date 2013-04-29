package com.hpm.redmione.report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.hpm.redmine.util.DateSplitter;
import com.hpm.redmine.util.TeamMember;
import com.hpm.redmine.util.XmlParser;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.TimeEntry;

public class Simple {
	private static String redmineHost = "https://smiles.happiestminds.com/";
	private static String apiAccessKey = "b0ccff4fc0c8b416e4bbae151985a6b2da457586";
	BufferedWriter out = null;
	XmlParser xmlData = new XmlParser();

	public static void main(String[] args) throws IOException, SAXException,
			ParserConfigurationException {

		Simple si = new Simple();
		si.getDetails("1-04-2013");
	}

	public String getDetails(String afterDate) throws IOException,
			SAXException, ParserConfigurationException {
		RedmineManager mgr = new RedmineManager(redmineHost, apiAccessKey);
		DateSplitter util = new DateSplitter();
		String s = null;
		

		try {
			for (TeamMember loopVariable : xmlData.XmlParserinit()) {
				List<Date> listofDates = new ArrayList<Date>();
				float totalHoursSpent = 0;

				out = new BufferedWriter(new FileWriter(
						loopVariable.getTeamMember() + ".html"));
				out.newLine();
				out.append(getHeader(loopVariable.getTeamMember()));
				out.append(getInnerDetailsTable(loopVariable.getProjectName(),
						loopVariable.getManagerName(), loopVariable.getPO(),
						loopVariable.getProjectCode(),
						loopVariable.getTaskCode(),
						loopVariable.getCostCenter()));

				out.append(getInnerHeader());
				// out.append(getTotalNumberOfSpentHours(Float.toString(totalHoursSpent)));
				Date date2 = null;
				for (int id : issudeIdGenerator(loopVariable.getIssueIds())) {
					List<TimeEntry> TimeEntryList = new ArrayList<>();

					TimeEntryList = mgr.getTimeEntriesForIssue(id);

					for (TimeEntry temp3 : TimeEntryList) {

						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd-MM-yyyy");
						Date date1 = sdf.parse(util.getDisplayDate(temp3
								.getSpentOn()));
						date2 = sdf.parse(afterDate);
						if (date1.compareTo(date2) >= 0) {

							listofDates.add(temp3.getSpentOn());
							System.out
									.println("-------------------------------------------------");
							System.out.println("weekDay is "
									+ util.generateDay(temp3.getSpentOn()));
							System.out.println("day is "
									+ util.getDisplayDate(temp3.getSpentOn()));
							System.out.println("comment is "
									+ temp3.getComment());
							System.out.println("spent time is "
									+ temp3.getHours());

							System.out
									.println("-------------------------------------------------");
							s = "</td>  \n\r<td>"
									+ util.getDisplayDate(temp3.getSpentOn())
									+ "</td>  \n\r <td>" + temp3.getComment()
									+ "</td>  \n\r <td>" + temp3.getHours()
									+ "</td>  </tr>";

							out.append(s);
							totalHoursSpent = temp3.getHours()
									+ totalHoursSpent;

						}

					}

				}

				out.append("<input type='hidden' value='"
						+ Float.toString(totalHoursSpent)
						+ "' id='totVal'></input>");
				out.append("<input type='hidden' value='"
						+ getDateNearest(listofDates, date2)
						+ "' id='period_time'></input>");

				out.flush();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String getHeader(String UserName) {
		return "<!DOCTYPE html>  <html lang=\"en\"> \n\r  <head>  \n\r  <title>Report</title> \n\r<style>body{ font-family: Arial;} table {background #fff; border-collapse: collapse;} td {border: 1px #000 solid;  padding: 3}</style><script> function updateTotal() { document.getElementById('total_value').innerHTML = '<tr><td><b><font color=green>SpentHours</font</b></td><td><b><font color=Maroon>'+document.getElementById('totVal').value+'</font></b></td></tr>';document.getElementById('periodTime').innerHTML = ' ( ' + document.getElementById('period_time').value + ' ) ';}</script>  </head>  \n\r  <body onLoad=updateTotal();> \n\r <table  width='100%'> <tr>\n \r<td width='80%'><div align='left' ><b> TimeSheets of "
				+ UserName
				+ "</b><b id='periodTime'></b></div></td> <td> <div align='right'> <img  src='http://www.happiestminds.com/sites/default/files/Happiest_Minds_Logo_Aug_26.png' width='150' height='75'>  </div></td>";
	}

	public static String getInnerHeader() {
		String innerHeader = " <br> <table border='1'  width='100%' > \n\r <tr> \n\r <td width='12%' bgcolor='#898080'><b>Date</b></td> <td width='76%' bgcolor='#898080'><b>Comments</b></td> <td width='12%' bgcolor='#898080'><b>Spent Hours </b></td> </tr> <tr><tr> \n\r";
		return innerHeader;
	}

	public String getInnerDetailsTable(String projectName, String ManagerName,
			String po, String ProjectCode, float TaskCode, String CostCenter) {
		String innerDetails = " <table border='1' width='100%'> \n\r <tr> \n\r "
				+ " </td></tr><td width='12%'><b><font color='green'>Project Name</font></b></td><td>"
				+ projectName
				+ " </td> </tr><td><b><font color='green'> Manager Name </font</b></td><td>"
				+ ManagerName
				+ "</td> </tr> <td><b><font color='green'> PO Number</font</b></td><td>"
				+ po
				+ "</td>  </tr><td><b><font color='green'> TaskCode</font</b></td></td><td>"
				+ TaskCode
				+ "</td> </tr><td><b><font color='green'> CostCenter</font</b></td> <td>"
				+ CostCenter
				+ "</td> <tr id='total_value'> </tr> <tr><tr> </table>\n\r";
		return innerDetails;
	}

	public String getTotalNumberOfSpentHours(String numberofHours) {
		String s = "<table border='1' width='100%'> <tr>   <td><b>TotalHours Spent</b> </td> <td width='65%'><b>"
				+ numberofHours + "</b></td> </table> </tr>";
		return s;
	}

	// value='109.0' id='totVal'
	public String writeSpenthoursdiv() {
		String s = "<div id='total_value'></div>";

		return s;
	}

	public List<Integer> issudeIdGenerator(String issueIdinString) {
		List<Integer> issueids = new ArrayList<Integer>();
		if (issueIdinString.contains(",")) {
			String arr[] = issueIdinString.split(",");

			for (String temp : arr) {
				issueids.add(Integer.parseInt(temp));
			}
		} else {
			issueids.add(Integer.parseInt(issueIdinString));

		}
		return issueids;

	}

	private String getDateNearest(List<Date> dates, Date targetDate) {

		Collections.sort(dates);
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMMM-YYYY");
		System.out.println("$$$" + sdf.format(dates.get(dates.size() - 1)));
		System.out.println("$$$--" + sdf.format(dates.get(0)));
		sdf.format(dates.get(dates.size() - 1));
		return sdf.format(dates.get(0)).concat(
				" to " + sdf.format(dates.get(dates.size() - 1)));
	}

}