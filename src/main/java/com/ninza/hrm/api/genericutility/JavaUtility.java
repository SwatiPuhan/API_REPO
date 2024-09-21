package com.ninza.hrm.api.genericutility;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.Date;
import java.util.Random;

public class JavaUtility {

	public int getRandomNumber() {
		Random random = new Random();
		int randomNum = random.nextInt(5000);
		return randomNum;
	}

	public String getSystemDateYYYYMMDD() {
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		String date1 = sdf.format(date);
		return date1;

	}

	public String getLocalDateAndTime() {
		return LocalDateTime.now().toString().replace(":", "-");
	}

}
