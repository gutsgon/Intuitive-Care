package com.intuitive.care.webscraping.downloader;

import com.intuitive.care.webscraping.exception.DownloadException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;

/**
 * A classe {@code FileDownloader} gerencia o download de múltiplos arquivos de forma paralela e otimizada.
 * Utiliza um pool de threads para executar múltiplas solicitações de download simultaneamente,
 * garantindo eficiência no processo e controle adequado dos recursos do sistema.
 */
public class FileDownloader {

    /** Tamanho do buffer utilizado para leitura e escrita durante o download (256 KB) */
    private static final int BUFFER_SIZE = 256 * 1024;

    /** Número máximo de threads concorrentes para download */
    private static final int THREAD_POOL_SIZE = 10;

    /** Tempo limite (em milissegundos) para conexão e leitura de dados */
    private static final int TIMEOUT = 1_000;

    /** Gerenciador de threads para execução de downloads em paralelo */
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    /**
     * Realiza o download de múltiplos arquivos em paralelo.
     * Cada URL fornecida será baixada para o respectivo caminho de destino.
     *
     * @param fileUrls Lista de URLs dos arquivos a serem baixados.
     * @param destinationPaths Lista de caminhos de destino para salvar os arquivos.
     * @throws InterruptedException Se a execução for interrompida enquanto aguarda a finalização dos downloads.
     * @throws DownloadException Se ocorrer um erro ao tentar baixar algum arquivo.
     */
    public void downloadFiles(List<String> fileUrls, List<String> destinationPaths) throws InterruptedException, DownloadException {
        if (fileUrls.size() != destinationPaths.size()) {
            throw new IllegalArgumentException("O número de URLs e caminhos de destino deve ser o mesmo.");
        }

        List<Future<?>> futures = new CopyOnWriteArrayList<>();

        for (int i = 0; i < fileUrls.size(); i++) {
            final String fileUrl = fileUrls.get(i);
            final String destinationPath = destinationPaths.get(i);

            futures.add(executor.submit(() -> {
                try {
                    downloadFile(fileUrl, destinationPath);
                } catch (DownloadException e) {
                    System.out.println("Erro ao baixar o arquivo: " + e.getMessage());
                }
            }));
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                System.err.println("Erro em uma das threads: " + e.getCause());
            }
        }
    }

    /**
     * Realiza o download de um único arquivo de maneira eficiente.
     * Se o arquivo já existir e for válido, o download será ignorado.
     *
     * @param fileUrl URL do arquivo a ser baixado.
     * @param destinationPath Caminho onde o arquivo será salvo.
     * @throws DownloadException Se ocorrer um erro ao tentar baixar o arquivo.
     */
    private void downloadFile(String fileUrl, String destinationPath) throws DownloadException {
        File file = new File(destinationPath);
        if (file.exists() && file.length() > 0) {
            System.out.println("Arquivo já existe: " + destinationPath);
            return;
        }

        try {
            Path parentDir = Paths.get(destinationPath).getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new DownloadException("Erro HTTP " + responseCode + " ao acessar " + fileUrl);
            }

            try (InputStream in = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
                 BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(destinationPath)), BUFFER_SIZE)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            connection.disconnect();
            System.out.println("Download concluído: " + destinationPath);
        } catch (Exception e) {
            throw new DownloadException("Erro ao baixar " + fileUrl, e);
        }
    }

    /**
     * Finaliza o executor de maneira segura, garantindo que todas as tarefas sejam concluídas.
     * Se as tarefas não forem finalizadas no tempo esperado, o executor será encerrado forçadamente.
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}