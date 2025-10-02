package exceptions;

public class SaldoAteraException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public SaldoAteraException()  {
    super();
  }

  public SaldoAteraException(String s)
  {
    super(s);
  }

}