package com.intuitive.care.webscraping.compressor;

// Exception imports
import com.intuitive.care.webscraping.exception.CompressException;

// Util imports
import java.io.*;
import java.util.logging.Logger;
import java.util.zip.*;

/**
 * Classe responsável por compactar arquivos PDF contidos em um diretório específico em um arquivo ZIP.
 * <p>
 * O método {@code compress} verifica se a compactação já foi realizada antes de processar os arquivos.
 * Se um arquivo ZIP existente for encontrado e não estiver vazio, a compactação será ignorada.
 * </p>
 */
public class Compressor {

    /**
     * Compacta todos os arquivos PDF de um diretório em um arquivo ZIP.
     * <p>
     * Antes de iniciar a compactação, o método verifica se o arquivo ZIP já existe e não está vazio.
     * Se o arquivo ZIP já foi criado anteriormente, a operação será ignorada para evitar duplicação.
     * </p>
     *
     * @param sourceDir   Caminho do diretório onde os arquivos PDF estão armazenados.
     * @param zipFilePath Caminho onde o arquivo ZIP será salvo.
     * @throws IOException       Se houver um erro ao acessar arquivos ou diretórios.
     * @throws CompressException Se ocorrer um erro durante a compactação.
     */
    public void compress(String sourceDir, String zipFilePath) throws IOException, CompressException, IllegalArgumentException {
        // Referências para os arquivos e diretórios
        File sourceFolder = new File(sourceDir);
        File zipFile = new File(zipFilePath);

            // Verifica se o arquivo ZIP já existe e se não está vazio
            if (zipFile.exists() && zipFile.length() > 0) {
                throw new CompressException("O arquivo ZIP já existe e não está vazio. Pulando a compactação.");
            }

            // Validação do diretório de origem
            if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
                throw new IllegalArgumentException("O diretório de origem não é válido: " + sourceDir);
            }


        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            File[] files = sourceFolder.listFiles();

            // Verifica se existem arquivos no diretório
            if (files == null || files.length == 0) {
                throw new IOException("Nenhum arquivo encontrado no diretório para compactação.");
            }

            // Processa apenas arquivos PDF dentro do diretório
            for (File file : files) {
                if (!file.getName().endsWith(".pdf")) {
                    continue; // Ignora arquivos que não sejam PDFs
                }

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] buffer = new byte[4096]; // Buffer de 4 KB para otimizar a escrita e consumir menos memória
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, bytesRead);
                    }

                    zipOut.closeEntry();
                }

                // Exclui o arquivo PDF após a compactação
                file.delete();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
}
