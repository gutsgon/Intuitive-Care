package com.intuitive.care.webscraping.exception;
/**
 * Exceção personalizada para erros na compressão de arquivos.
 */
public class CompressException extends Exception{
    /**
     * Construtor para exceções com uma mensagem e uma causa original.
     *
     * @param message Mensagem de erro.
     * @param cause Causa original do erro.
     */
    public CompressException (String message, Throwable cause){
        super(message, cause);
    }
    /**
     * Construtor para exceções com uma mensagem.
     *
     * @param message Mensagem de erro.
     */
    public CompressException (String message){
        super(message);
    }
}
