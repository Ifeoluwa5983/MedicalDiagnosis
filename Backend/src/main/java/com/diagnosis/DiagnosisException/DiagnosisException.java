package com.diagnosis.DiagnosisException;

public class DiagnosisException extends Exception{
    public DiagnosisException() {
        super();
    }

    public DiagnosisException(String message) {
        super(message);
    }

    public DiagnosisException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiagnosisException(Throwable cause) {
        super(cause);
    }

    protected DiagnosisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
