package com.hpm.redmione.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.H1;
import com.hp.gagawa.java.elements.Head;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Script;
import com.hp.gagawa.java.elements.Style;
import com.hp.gagawa.java.elements.Table;
import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;
import com.hp.gagawa.java.elements.Tr;
import com.hpm.redmine.util.XmlParser;

public class DynamicHtml {
	XmlParser xmlData = new XmlParser();

	public static void main(String[] args) {
		Html html = new Html();
		Head head = new Head();

		html.appendChild(head);

		Title title = new Title();
		title.appendChild(new Text("Reporting Page"));
		head.appendChild(title);

		Style s = new Style("");
		s.appendChild(new Text("body{ font-family: Arial;}"));
		s.appendChild(new Text(
				"table {background #fff; border-collapse: collapse;}"));
		s.appendChild(new Text("td {border: 1px #000 solid;  padding: 3}"));
		s.appendChild(new Text("td.projectDetails{border: none;}"));
		s.appendChild(new Text(
				"table.projectConstantDetails { background #fff;border-collapse: inherit; }"));

		html.appendChild(s);

		Script script = new Script("");

		script.appendChild(new Text(
				"function updateTotal() { document.getElementById('total_value').innerHTML = '<tr><td class=projectDetails><b><font color=green>SpentHours</font</b></td><td class=projectDetails><b><font color=Maroon>'+document.getElementById('totVal').value+'</font></b></td></tr>';document.getElementById('periodTime').innerHTML = ' ( ' + document.getElementById('period_time').value + ' ) ';}"));
		html.appendChild(script);

		Body body = new Body();
		body.setAttribute("onLoad", "updateTotal();");

		Table mainTable = new Table();
		mainTable.setWidth("100%");
		Tr mainTableTr = new Tr();
		mainTable.appendChild(mainTableTr);
		Td mainTableTd=new Td();
		mainTableTd.setWidth("80%");
		mainTable.appendChild(mainTableTd);
		
		

		H1 h1 = new H1();
		h1.appendChild(new Text("Example Page Header"));
		body.appendChild(h1);
		html.appendChild(body);
		System.out.println(html.write());
		try {
			File output = new File("test.html");
			PrintWriter out = new PrintWriter(new FileOutputStream(output));
			out.println(html.write());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void getHeadingLogo()
	{
		Div div=new Div();
		div.setStyle("");
		
	}
}
