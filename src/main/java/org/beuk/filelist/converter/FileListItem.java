package org.beuk.filelist.converter;

import java.time.*;
import java.util.*;
import java.util.regex.*;

public class FileListItem {

	private static Locale locale = new Locale("nl", "NL");

	public String title;

	public String thumpURL;

	public String fileURL;

	public String club;

	public LocalDateTime date;

	public short year;

	public short monthNumber;

	public short decade;

	public int number;

	public String month;

	private final String prefix;

	public FileListItem(String club, String prefix) {

		this.club = club;
		this.prefix = prefix;
	}

	public String getClub() {

		return club;
	}

	public LocalDateTime getDate() {

		return date;
	}

	public short getDecade() {

		return decade;
	}

	public String getFileURL() {

		return fileURL;
	}

	public String getMonth() {

		return month;
	}

	public int getMonthNumber() {

		return monthNumber;
	}

	public int getNumber() {

		return number;
	}

	public String getThumpURL() {

		return thumpURL;
	}

	public String getTitle() {

		return title;
	}

	public int getYear() {

		return year;
	}

	public boolean parse(String filename) {

		// sch1984-04-76.pdf
		// System.out.println("fn: " + filename);
		final Pattern pattern = Pattern.compile("(((" + prefix + "([0-9]{4})-([0-9]{2})-([0-9].*)))\\.pdf)");
		final Matcher a = pattern.matcher(filename);
		// System.out.println("line : " + line);
		if (a.find()) {
			fileURL = a.group(1);
			thumpURL = a.group(2) + "-1.png";
			title = a.group(3);
			year = Short.parseShort(a.group(4));
			decade = (short) ((year / 10) * 10);
			monthNumber = Short.parseShort(a.group(5));
			number = Integer.parseInt(a.group(6));
			return true;
		} else {
			return false;
		}
	}

	public void setClub(String club) {

		this.club = club;
	}

	public void setDate(LocalDateTime date) {

		this.date = date;
	}

	public void setDecade(short decade) {

		this.decade = decade;
	}

	public void setFileURL(String fileURL) {

		this.fileURL = fileURL;
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public void setMonthNumber(short monthNumber) {

		this.monthNumber = monthNumber;
	}

	public void setNumber(int number) {

		this.number = number;
	}

	public void setThumpURL(String thumpURL) {

		this.thumpURL = thumpURL;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public void setYear(short year) {

		this.year = year;
	}
}
