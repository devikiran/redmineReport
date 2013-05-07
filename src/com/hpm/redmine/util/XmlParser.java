package com.hpm.redmine.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser extends DefaultHandler {
	private TeamMember teamMember;
	private String temp;
	private ArrayList<TeamMember> tmList = new ArrayList<TeamMember>();

	public static void main(String[] args) throws IOException, SAXException,ParserConfigurationException {
		XmlParser x=new XmlParser();
		x.XmlParserinit();
		//x.readList();
	}
	public  List<TeamMember>  XmlParserinit() throws IOException, SAXException,ParserConfigurationException {

		// Create a "parser factory" for creating SAX parsers
		SAXParserFactory spfac = SAXParserFactory.newInstance();

		// Now use the parser factory to create a SAXParser object
		SAXParser sp = spfac.newSAXParser();

		// Create an instance of this class; it defines all the handler methods
		XmlParser handler = new XmlParser();

		// Finally, tell the parser to parse the input and notify the handler
		sp.parse("Schema.xml", handler);

		//handler.readList();
		//System.out.println("No of  the accounts in bank '" + tmList.size());
		/*for(TeamMember temp:tmList)
		{
			System.out.println(temp.getTeamMember()+"in the for");
		}*/
		return handler.readList();
	}

	/*
	 * When the parser encounters plain text (not XML elements), it calls(this
	 * method, which accumulates them in a string buffer
	 */
	@Override
	public void characters(char[] buffer, int start, int length) {
		temp = new String(buffer, start, length);
	}

	/*
	 * Every time the parser encounters the beginning of a new element, it calls
	 * this method, which resets the string buffer
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		temp = "";
		if (qName.equalsIgnoreCase("TeamMember")) {
			teamMember = new TeamMember();
			// teamMember.setType(attributes.getValue("type"));

		}
	}

	/*
	 * When the parser encounters the end of an element, it calls this method
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equalsIgnoreCase("Project")) {
			// add it to the list
			tmList.add(teamMember);

		} else if (qName.equalsIgnoreCase("ProjectName")) {
			teamMember.setProjectName(temp);
		} else if (qName.equalsIgnoreCase("TeamMemberName")) {
			teamMember.setTeamMember(temp);
			
		} else if (qName.equalsIgnoreCase("ManagerName")) {
			teamMember.setManagerName(temp);
		} else if (qName.equalsIgnoreCase("PO")) {
			teamMember.setPO(temp);
		} else if (qName.equalsIgnoreCase("ProjectCode")) {
			teamMember.setProjectCode(temp);
		} else if (qName.equalsIgnoreCase("TaskCode")) {
			teamMember.setTaskCode(Float.parseFloat(temp));
		} else if (qName.equalsIgnoreCase("CostCenter")) {
			teamMember.setCostCenter(temp);
		} else if (qName.equalsIgnoreCase("IssueIds")) {
			teamMember.setIssueIds(temp);
			//System.out.println("in the end"+temp);
			//
		}

	}

	public List<TeamMember> readList() {
		//System.out.println("No of  the accounts in bank '" + tmList.size()	+ "'.");
		/*Iterator<TeamMember> it = tmList.iterator();
		while (it.hasNext()) {
			System.out.println("in the loop "+it.next().getTeamMember());
		}*/
		for(TeamMember temp:tmList)
		{
			//System.out.println(temp.getTeamMember()+"in the read list r");
		}
		return tmList;
	}

}
