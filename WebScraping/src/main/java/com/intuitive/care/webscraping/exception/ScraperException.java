package com.intuitive.care.webscraping.exception;
/**
 * Exceção personalizada para erros na obtenção do link dos arquivos.
 */
public class ScraperException extends Exception {
    /**
     * Construtor para exceções com uma mensagem e uma causa original.
     *
     * @param message Mensagem de erro.
     * @param cause Causa original do erro.
     */
    public ScraperException(String message, Throwable cause){super(message, cause);}

    /**
     * Construtor para exceções com uma mensagem.
     *
     * @param message Mensagem de erro.
     */
    public ScraperException(String message){super(message);}
}
