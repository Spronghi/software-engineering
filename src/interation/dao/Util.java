package interation.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Util {
	public static int getCarStatusId(String status){
		switch(status){
		case "avaible":
			return 1;
		case "hired":
			return 2;
		case "routine maintenance":
			return 3;
		case "emergency maintenance":
			return 4;
		}
		return 0;
	}
	public static String getCarStatus(int id){
		switch(id){
		case 1:
			return "avaible";
		case 2:
			return "hired";
		case 3:
			return "routine maintenance";
		case 4:
			return "emergency maintenance";
		}
		return "";
	}
	public static int getCarCategoryId(char category){
		switch(category){
		case 'A':
			return 1;
		case 'B':
			return 2;
		case 'C':
			return 3;
		case 'D':
			return 4;
		}
		return 0;
	}
	public static char getCarCategory(int id){
		switch(id){
		case 1:
			return 'A';
		case 2:
			return 'B';
		case 3:
			return 'C';
		case 4:
			return 'D';
		}
		return '/';
	}
	static int getPaymentTypeId(String type){
		switch(type){
		case "cash":
			return 1;
		case "credit/debit card":
			return 2;
		}
		return 0;
	}
	static String getPaymentType(int id){
		switch(id){
		case 1:
			return "cash";
		case 2:
			return "credit/debit card";
		}
		return "";
	}
	public static int getCurrencyId(String currency){
		switch(currency){
		case "euro":
			return 1;
		case "dollar":
			return 2;
		case("pound"):
			return 3;
		}
		return 0;
	}
	public static String getCurrency(int id){
		switch(id){
		case 1:
			return "euro";
		case 2:
			return "dollar";
		case 3:
			return "pund";
		}
		return "";
	}
	public static char getCurrencySymbol(int id){
		switch(id){
		case 1:
			return '€';
		case 2:
			return '$';
		case 3:
			return '£';
		}
		return '/';
	}
	public static int getContractTypeId(String type){
		switch(type){
		case "one-day pass":
			return 1;
		case "one-week pass":
			return 2;
		}
		return 0;
	}
	public static String getTimestampFormatString(LocalDate date, LocalTime time){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = date.atTime(time);
		return (Timestamp.valueOf(dateTime.format(formatter))).toString();
	}
	public static String getTimestampFormatString(LocalDate date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(formatter).toString();
	}
}
