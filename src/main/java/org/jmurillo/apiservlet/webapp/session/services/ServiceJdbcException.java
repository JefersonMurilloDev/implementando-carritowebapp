package org.jmurillo.apiservlet.webapp.session.services;

public class ServiceJdbcException extends RuntimeException{

    public ServiceJdbcException(String message) {
        super(message);
    }

    public ServiceJdbcException(String message, Throwable cause) {
        super(message, cause);
    }
}
