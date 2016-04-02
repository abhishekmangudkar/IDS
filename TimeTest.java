package com.grad.ids;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {
	
//	day month hour min
	
	public static void main(String args[])
	{
		int day = Calendar.DAY_OF_YEAR;
		int mon = Calendar.MONTH;
		int year = Calendar.YEAR;
		
		int hour = Calendar.HOUR;
		int min = Calendar.MINUTE;
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
		
		
		System.out.println(day);
		System.out.println(mon);
		System.out.println(year);
		System.out.println(hour);
		System.out.println(min);
		
		System.out.println(day+""+mon+""+year+""+hour+""+min);
	}

}
