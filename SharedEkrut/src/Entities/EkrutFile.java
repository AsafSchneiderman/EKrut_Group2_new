package Entities;
import java.io.Serializable;
import java.security.Timestamp;
public class EkrutFile implements Serializable{
	private static final long serialVersionUID = 4124897926133951907L;
	private String reportType=null;
	private String fileName=null;
	private String month,year;
	public byte[] myByteArray;
	int size;
	public EkrutFile(String fileName) {
		this.fileName=fileName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year=year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month=month;
	}
	public void initArray(int size) {
		myByteArray=new byte[size];
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size=size;
	}
	public byte[] getMyByteArray() {
		return myByteArray;
	}
	public byte getMyByteArray(int i) {
		return myByteArray[i];
	}
	public void setMyByteArray(byte[] myByteArray) {
		for(int i=0;i<myByteArray.length;i++)
			this.myByteArray[i]=myByteArray[i];
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType=reportType;
	}
	//for test
	@Override
	public boolean equals(Object obj) {
		EkrutFile f=(EkrutFile)obj;
		if(f.getYear().equals(this.getYear()) && f.getMonth().equals(this.getMonth())&& f.myByteArray.length==this.myByteArray.length)
			for(int i=0;i<this.getMyByteArray().length;i++)
				if(!(this.getMyByteArray(i)==f.getMyByteArray(i)))
					return false;
		return true;
	}
}
