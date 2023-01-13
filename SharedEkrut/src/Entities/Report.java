package Entities;

import java.io.Serializable;

public class Report implements Serializable{
	
	private static final long serialVersionUID = 583542510006825068L;
	
	private ReportType reportType;
	private String month;
	private String year;
	private String reportName;
	
	public Report(ReportType reportType, String month, String year, String region) {
		this.reportType = reportType;
		this.month = month;
		this.year = year;
		reportName = reportType.toString() + "_" + month + "_" + year + "_" + region;
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
	public String getReportName(){
		return reportName;
	}
	
}
