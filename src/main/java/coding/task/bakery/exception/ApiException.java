package coding.task.bakery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class ApiException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3342972854123293128L;
	public static final String INVALID_REQUEST = "INVALID_REQUEST";
	public static final String NO_PACK_FOUND = "NO_PACK_FOUND";
	public static final String INVALID_PACKCODE = "INVALID_PACKCODE";
	
	private final String error;

	public ApiException(String error) {
		super(error);
		this.error = error;
	}

	public ApiException() {
		this(INVALID_REQUEST);
	}

}
