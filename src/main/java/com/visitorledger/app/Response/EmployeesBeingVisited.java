package com.visitorledger.app.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesBeingVisited {

	private DateAndEmployee one;
	private DateAndEmployee two;
	private DateAndEmployee three;
	private DateAndEmployee four;
	private DateAndEmployee five;
	private DateAndEmployee six;
	private DateAndEmployee seven;

	public DateAndEmployee getOne() {
		return one;
	}

	public void setOne(DateAndEmployee one) {
		this.one = one;
	}

	public DateAndEmployee getTwo() {
		return two;
	}

	public void setTwo(DateAndEmployee two) {
		this.two = two;
	}

	public DateAndEmployee getThree() {
		return three;
	}

	public void setThree(DateAndEmployee three) {
		this.three = three;
	}

	public DateAndEmployee getFour() {
		return four;
	}

	public void setFour(DateAndEmployee four) {
		this.four = four;
	}

	public DateAndEmployee getFive() {
		return five;
	}

	public void setFive(DateAndEmployee five) {
		this.five = five;
	}

	public DateAndEmployee getSix() {
		return six;
	}

	public void setSix(DateAndEmployee six) {
		this.six = six;
	}

	public DateAndEmployee getSeven() {
		return seven;
	}

	public void setSeven(DateAndEmployee seven) {
		this.seven = seven;
	}

}
