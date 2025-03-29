package com.intuitive.care.webscraping.exception;

/**
 * Exceção personalizada para erros no download de arquivos.
 */
public class DownloadException extends Exception {

    /**
     * Construtor para exceções com uma mensagem.
     *
     * @param message Mensagem de erro.
     */
    public DownloadException(String message) {
        super(message);
    }

    /**
     * Construtor para exceções com uma mensagem e uma causa original.
     *
     * @param message Mensagem de erro.
     * @param cause Causa original do erro.
     */
    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}


