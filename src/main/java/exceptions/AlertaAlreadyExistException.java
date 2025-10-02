package exceptions;

public class AlertaAlreadyExistException extends Exception {
	private static final long serialVersionUID = 1L;

	public AlertaAlreadyExistException()  {
		super();
	}

	public AlertaAlreadyExistException(String s)
	{
		super(s);
	}

}