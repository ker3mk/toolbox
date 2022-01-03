package rcrypt;


class RcryptException extends Exception
{

    RcryptException()
    {
    }

    RcryptException(String message)
    {
        super(message);
    }

    RcryptException(Throwable cause)
    {
        super(cause);
    }

    RcryptException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
