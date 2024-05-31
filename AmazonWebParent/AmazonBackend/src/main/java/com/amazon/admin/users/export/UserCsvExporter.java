package com.amazon.admin.users.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.amazon.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class UserCsvExporter {

	public void export(List<User> listUsers,HttpServletResponse response) throws IOException {
		
		DateFormat dataformatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	
		String timestamp =dataformatter.format(new Date());
		
		String fileName="users_" + timestamp + ".csv";
		
		response.setContentType("text/csv");
		
		String headerKey="Content-Disposition";
		String headerValue = "attachment; fileName=" + fileName;
		response.setHeader(headerKey, headerValue);
		
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
	
		String[] csvHeader= {"User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled"};
		
		// map the header column to the field ( user entity field)
		String [] fieldMapping = {"id","email","firstName","lastName","roles","enabled"};
		
		// iterate each user
		for(User user: listUsers) {
			csvWriter.write(user, fieldMapping);
		}
		
		csvWriter.writeHeader(csvHeader);
		
		csvWriter.close();
		
		
		
		
		
		
		
		
		
	}
	
}
