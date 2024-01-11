package university.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({RequestException.class, 
		IllegalArgumentException.class,
		MissingServletRequestParameterException.class})  // Có thể bắt nhiều loại exception
    public ResponseEntity<String> handleRequestException(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class,
		HttpMediaTypeNotSupportedException.class}) 
    public ResponseEntity<String> handleNotSupportedException(Exception e) {
        return ResponseEntity.status(405).body(e.getMessage());
    }
	@ExceptionHandler({AuthenticationCredentialsNotFoundException.class,
		BadCredentialsException.class,
		InternalAuthenticationServiceException.class}) 
    public ResponseEntity<String> handleTokenException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
	@ExceptionHandler({Exception.class, DatabaseException.class})
	public ResponseEntity<String> handleServerException(Exception e){
		e.printStackTrace();
		return ResponseEntity.status(500).body("Unknown error");
	}
}
