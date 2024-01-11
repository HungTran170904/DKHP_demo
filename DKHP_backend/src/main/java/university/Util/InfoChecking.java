package university.Util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class InfoChecking {
	public int checkUsernameType(String username) {
		if(Pattern.matches("^\\d{8}$", username)) return 1;
		else if(Pattern.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", username)) return 2;
		else return 0;
	}
	public String getSubjectId(String courseId) {
		if(courseId==null) return "";
		String s="";
		int i=0;
		while(i<courseId.length()&&courseId.charAt(i)!='.') {
			s+=courseId.charAt(i);
			i++;
		}
		return s;
	}
	public boolean checkEmail(String email) {
		return Pattern.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$",email);
	}
}
