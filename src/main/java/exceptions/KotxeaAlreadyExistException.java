package exceptions;

public class KotxeaAlreadyExistException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public KotxeaAlreadyExistException()  {
    super();
  }

  public KotxeaAlreadyExistException(String s)
  {
    super(s);
  }

}