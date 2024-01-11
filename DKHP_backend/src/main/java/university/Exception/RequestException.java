package university.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestException extends RuntimeException{
	public RequestException(String message) {
		super(message);
	}
	
}
