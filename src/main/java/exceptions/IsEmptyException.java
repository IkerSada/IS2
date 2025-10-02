package exceptions;
public class IsEmptyException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public IsEmptyException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public IsEmptyException(String s)
  {
    super(s);
  }
}