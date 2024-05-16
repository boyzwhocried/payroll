package com.lawencon.payroll.util;

import java.time.LocalDateTime;

public class DateUtil {
    public static LocalDateTime toLocalDateTime(String time) {
		
		return LocalDateTime.parse(time);
	}
}
