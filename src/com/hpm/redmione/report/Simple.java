package com.hpm.redmione.report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
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
import com.taskadapter.redmineapi.RedmineException;
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
		 si.getDetails(args[0],args[1]);
		//si.getDetails("1-4-2013", "30-4-2013");
	}

	public String getDetails(String fromDate, String toDate)
			throws IOException, SAXException, ParserConfigurationException {
		RedmineManager mgr = new RedmineManager(redmineHost, apiAccessKey);
		DateSplitter util = new DateSplitter();
		String s = null;

		try {
			for (TeamMember loopVariable : xmlData.XmlParserinit()) {
				List<Date> listofDates = new ArrayList<Date>();
				List<DataTable> tableObjects=new ArrayList<DataTable>();
				float totalHoursSpent = 0;

				out = new BufferedWriter(new FileWriter(
						loopVariable.getTeamMember() + ".html"));
				out.newLine();
				out.append(getHeader(loopVariable.getTeamMember()));
				out.append(getInnerDetailsTable(loopVariable.getProjectName(),
						loopVariable.getManagerName(), loopVariable.getPO(),
						loopVariable.getProjectCode(),
						loopVariable.getTaskCode(),
						loopVariable.getCostCenter(),
						loopVariable.getProjectCode()));

				out.append(getInnerHeader());
				Date beforeDate = null;
				Date afterDate = null;
				for (int id : issudeIdGenerator(loopVariable.getIssueIds())) {
					List<TimeEntry> TimeEntryList = new ArrayList<>();

					TimeEntryList = mgr.getTimeEntriesForIssue(id);

					for (TimeEntry temp3 : TimeEntryList) {

						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd-MM-yyyy");
						Date date1 = sdf.parse(util.getDisplayDate(temp3
								.getSpentOn()));
						beforeDate = sdf.parse(fromDate);
						afterDate = sdf.parse(toDate);

						if (date1.compareTo(beforeDate) >= 0
								&& date1.compareTo(afterDate) <= 0) {

							listofDates.add(temp3.getSpentOn());
							/*System.out
									.println("-------------------------------------------------");
							System.out.println("weekDay is "
									+ util.generateDay(temp3.getSpentOn()));
							System.out.println("day is "
									+ util.getDisplayDate(temp3.getSpentOn()));
							System.out.println("comment is "
									+ temp3.getComment());
							System.out.println("spent time is "
									+ temp3.getHours());

							System.out.println("-------------------------------------------------");*/
							/*s = "</td>  \n\r<td>"
									+ util.getDisplayDate(temp3.getSpentOn())
									+ "</td>  \n\r <td>" + temp3.getComment()
									+ "</td>  \n\r <td>" + temp3.getHours()
									+ "</td>  </tr>";*/
							DataTable tableobj=new DataTable();
							tableobj.setComment(temp3.getComment());
							tableobj.setDate(temp3.getSpentOn());
							tableobj.setHours(temp3.getHours());
							
							tableObjects.add(tableobj);
							

						}

					}

				}

				
				Collections.sort(tableObjects, DataTable.dataTableComparator);
				
				for(DataTable t:tableObjects)
				{
					s = "</td>  \n\r<td>"
							+ util.getDisplayDate(t.getDate())
							+ "</td>  \n\r <td>" + t.getComment()
							+ "</td>  \n\r <td>" + t.getHours()
							+ "</td>  </tr>";
					out.append(s);
					totalHoursSpent = t.getHours()
							+ totalHoursSpent;
				}
				out.append("<input type='hidden' value='"
						+ Float.toString(totalHoursSpent)
						+ "' id='totVal'></input>");
				out.append("<input type='hidden' value='"
						+ getDateNearest(listofDates, beforeDate)
						+ "' id='period_time'></input>");
				out.append("</table>");
				out.append(getSignature());
				
				out.flush();
			}
		}

		catch (RedmineException | ParseException e) {
			e.printStackTrace();
			System.out.println("RedmineException or Parse Exception had occured"+e.getLocalizedMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return s;
	}

	public static String getHeader(String UserName) {
		return "<!DOCTYPE html>  <html lang=\"en\"> \n\r  <head>  \n\r  <title>Report</title> \n\r<style>body{ font-family: Arial;} table {background #fff; border-collapse: collapse;} td {border: 1px #000 solid;  padding: 3} td.projectDetails{border: none;} table.projectConstantDetails { background #fff;border-collapse: inherit; }   </style>" +
				"		<script> " +
				"			function updateTotal() " +
				"			{ " +
				"					document.getElementById('total_value').innerHTML = '<tr><td width=17% class=projectDetails><b><font color=green>Total hours</font</b></td><td class=projectDetails>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>'+document.getElementById('totVal').value+'</b></td></tr>';" +
				"					document.getElementById('periodTime').innerHTML = ' ( ' + document.getElementById('period_time').value + ' ) ';}</script>  </head>  \n\r  " +
				"									<body onLoad=updateTotal();>  " +
				"										<table  width='100%'> " +
				"											<tr>" +
				"												<td width='80%' class='projectDetails'>" +
				"													<div style='text-align: left;valign=middle'>" +
				"														<b> TimeSheets of "
				+ UserName
				+ "														</b>" +
				"														<b id='periodTime'></b> " +
				"														</div>" +																				
				"												</td>" +
				"												<td class='projectDetails'>" +
				"													<img  src='http://upload.wikimedia.org/wikipedia/en/f/f0/Happiest_Minds_Logo.jpg' width='150' height='75' style='float:right'>" +
				"												</td>" +
				"											</tr>" +
				"										</table>" +
				"										<table>";
	}

	public static String getInnerHeader() {
		String innerHeader = " <br> <table border='1'  width='100%' > \n\r <tr> \n\r <td width='17%' bgcolor='#FFFFCC'><b>Date</b></td> <td width='70%' bgcolor='#FFFFCC'><b>Comments</b></td> <td bgcolor='#FFFFCC'><b>Spent Hours </b></td> </tr> <tr><tr> \n\r";
		return innerHeader;
	}

	public String getInnerDetailsTable(String projectName, String ManagerName,
			String po, String ProjectCode, float TaskCode, String CostCenter,
			String projectCode) {
		String innerDetails = " <table border='1' width='100%' class='projectConstantDetails'>"
				+ "<tr><td width='15%' class='projectDetails'><b><font color='green'>Project Name</font></b></td><td class='projectDetails'>"
				+ projectName + " </td> </tr>" 
				+ "<tr><td class='projectDetails'><b><font color='green'> Manager Name </font</b></td><td class='projectDetails'>"
				+ ManagerName + " </td> </tr>"
				+ "<tr><td class='projectDetails'><b><font color='green'> PO Number</font</b></td><td class='projectDetails'>"
				+ po + " </td> </tr>"
				+ "<tr><td class='projectDetails'><b><font color='green'> TaskCode</font</b></td></td><td class='projectDetails'>"
				+ projectCode + " </td> </tr>"
				+ "<tr><td class='projectDetails'><b><font color='green'> ProjectCode</font</b></td></td><td class='projectDetails'>"
				+ TaskCode + " </td> </tr>"
				+ "<tr><td class='projectDetails'><b><font color='green'> CostCenter</font</b></td> <td class='projectDetails'>"
				+ CostCenter + " </td> </tr>"
				+ "</table>"
				+ "<table border='1' width='100%' class='projectConstantDetails'>"
				+ "<tr id='total_value'> </tr>" 
				+ "</table>";
;
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
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMMM-YYYY");
		if (dates.size() > 0) {
			Collections.sort(dates);

		/*	System.out.println(dates.size() + "size of dates");
			System.out.println("$$$" + sdf.format(dates.get(dates.size() - 1)));
			System.out.println("$$$--" + sdf.format(dates.get(0)))*/;
			sdf.format(dates.get(dates.size() - 1));
			return sdf.format(dates.get(0)).concat(
					" to " + sdf.format(dates.get(dates.size() - 1)));
		} else {
			//String s=sdf.format(targetDate);
			return "1-April-2013";
			//return s;
		}
	}

	private String getSignature() {
		return "<font face=arial size=1>* This is a system generated timesheet and requires no signature </font><br><br> \n\r <br>  \n\r <table align='center'>  \n\r <td class='projectDetails' style=' text-align: center;'> \n\r <b>Thank you for choosing Happiest Minds</b> <br><br> Happiest Minds Technologies Private Limited, Block II, Velankani Tech Park, <br>43 Electronic City, Hosur Road, Bangalore - 560 100, India \n \r </td>\n \r </table>";

	}

}