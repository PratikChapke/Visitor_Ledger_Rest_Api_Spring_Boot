package com.visitorledger.app.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateResponse {

	private int today;
	private int yesterday;
	private int thisWeek;
	private int previousWeek;
	private int thisMonth;
	private int previousMonth;

	public int getToday() {
		return today;
	}

	public void setToday(int today) {
		this.today = today;
	}

	public int getYesterday() {
		return yesterday;
	}

	public void setYesterday(int yesterday) {
		this.yesterday = yesterday;
	}

	public int getThisWeek() {
		return thisWeek;
	}

	public void setThisWeek(int thisWeek) {
		this.thisWeek = thisWeek;
	}

	public int getPreviousWeek() {
		return previousWeek;
	}

	public void setPreviousWeek(int previousWeek) {
		this.previousWeek = previousWeek;
	}

	public int getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(int thisMonth) {
		this.thisMonth = thisMonth;
	}

	public int getPreviousMonth() {
		return previousMonth;
	}

	public void setPreviousMonth(int previousMonth) {
		this.previousMonth = previousMonth;
	}
	//	public void setYesterday(int size) {
	//		// TODO Auto-generated method stub
	//		
	//	}

}
