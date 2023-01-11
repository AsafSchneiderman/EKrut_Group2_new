package Entities;

import java.io.Serializable;

public class Report implements Serializable{
	
	private static final long serialVersionUID = 583542510006825068L;
	
	private ReportType reportType;
	private String month;
	private String year;
	
	public Report(ReportType reportType, String month, String year) {
		this.reportType = reportType;
		this.month = month;
		this.year = year;
	}
	
	public ReportType getReportType() {
		return reportType;
	}
	public String getMonth() {
		return month;
	}
	public String getYear() {
		return year;
	}
	
}
