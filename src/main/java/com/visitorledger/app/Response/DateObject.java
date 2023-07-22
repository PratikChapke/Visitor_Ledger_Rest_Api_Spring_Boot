package com.visitorledger.app.Response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateObject {

	private LocalDate now;
	private LocalDate previous;

	public LocalDate getNow() {
		return now;
	}

	public void setNow(LocalDate now) {
		this.now = now;
	}

	public LocalDate getPrevious() {
		return previous;
	}

	public void setPrevious(LocalDate previous) {
		this.previous = previous;
	}

}
