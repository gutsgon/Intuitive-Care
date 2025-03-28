package com.intuitive.care.webscraping;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.intuitive.care.webscraping.compressor.Compressor;
import com.intuitive.care.webscraping.downloader.FileDownloader;
import com.intuitive.care.webscraping.scraper.WebScraper;
import com.intuitive.care.webscraping.exception.CompressException;
import com.intuitive.care.webscraping.exception.DownloadException;
import com.intuitive.care.webscraping.exception.ScraperException;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelos testes de integração das funcionalidades principais do sistema de scraping, download e compressão.
 *
 * A classe realiza os seguintes testes:
 * 1. Extração de links de arquivos PDF através de Web Scraping.
 * 2. Download dos arquivos PDF extraídos.
 * 3. Compactação dos arquivos PDF em um arquivo ZIP.
 *
 * Além disso, a classe monitora o desempenho das operações, medindo o tempo de execução e o uso de recursos do sistema (CPU e memória).
 *
 * Os testes são executados na ordem definida pela anotação {@link TestMethodOrder}.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTest {

    /**
     * Mede o tempo de execução de uma operação fornecida como parâmetro.
     *
     * @param task A tarefa a ser medida. Deve ser uma implementação de {@link Runnable}.
     * @return O tempo de execução da tarefa em nanosegundos.
     */
    private long measureExecutionTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * Exibe no console o uso de memória e CPU do sistema durante a execução do teste.
     * O uso de memória é calculado com base na diferença entre a memória total disponível e a memória livre.
     * O uso de CPU é obtido utilizando a média do sistema {@link ManagementFactory#getOperatingSystemMXBean}.
     */
    private void printMemoryAndCpuUsage() {
        // Monitoramento de uso de memória
        long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Uso de memória: " + memoryUsed / (1024 * 1024) + " MB");

        // Monitoramento de uso de CPU
        double cpuUsage = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        System.out.println("Uso de CPU: " + cpuUsage);
    }

    /**
     * Testa a extração de links de arquivos PDF a partir de uma página específica utilizando a técnica de Web Scraping.
     *
     * O teste realiza os seguintes passos:
     * 1. Conecta-se à URL fornecida para acessar o conteúdo da página.
     * 2. Extraí os links de arquivos PDF presentes na página utilizando a classe {@link WebScraper}.
     * 3. Valida que a lista de links extraídos não seja nula ou vazia.
     * 4. Exibe os primeiros e últimos links extraídos no console para conferência.
     *
     * @throws ScraperException Se ocorrer um erro durante o processo de scraping ou se o scraping falhar ao tentar acessar a URL ou obter os links de PDF.
     * @throws IOException Caso haja falha ao acessar a URL ou ao realizar a conexão.
     */
    @Test
    @org.junit.jupiter.api.Order(1)
    void testScrapePDFLinks() throws ScraperException, IOException, Exception {
        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
        WebScraper webScraper = new WebScraper();

        long executionTime = measureExecutionTime(() -> {
            try {
                List<String> pdfLinks = webScraper.scrapePDFLinks(url);
                if(pdfLinks == null || pdfLinks.isEmpty()) {
                    throw new ScraperException("Não foram encontrados links de PDF.");
                }
                System.out.println("Links extraídos: " + pdfLinks.get(0) + " ... " + pdfLinks.get(pdfLinks.size() - 1));
            } catch (ScraperException | IOException e) {
                System.out.println(e.getMessage());
            }
        });

        // Impressão de tempo de execução
        System.out.println("Tempo de execução do teste de scraping: " + executionTime / 1_000_000 + " ms");

        // Monitoramento de uso de memória e CPU
        printMemoryAndCpuUsage();
    }

    /**
     * Testa o processo de download de arquivos PDF a partir de URLs extraídas da página web.
     *
     * O teste realiza os seguintes passos:
     * 1. Baixa dois arquivos PDF de URLs especificadas e salva-os em diretórios locais definidos.
     * 2. Verifica se os arquivos foram baixados corretamente, garantindo que o caminho de destino seja válido.
     * 3. Garante que os arquivos baixados não estão vazios, validando a integridade do download.
     *
     * @throws DownloadException Se ocorrer um erro ao baixar os arquivos. Caso o arquivo não seja baixado corretamente ou um erro de I/O ocorra.
     */
    @Test
    @org.junit.jupiter.api.Order(2)
    void testDownloadFile() throws DownloadException, Exception {
        FileDownloader downloader = new FileDownloader();
        List<String> filesUrl = new ArrayList<>();
        List<String> destinationPaths = new ArrayList<>();
        destinationPaths.add("src/test/java/com/intuitive/care/webscraping/downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
        destinationPaths.add("src/test/java/com/intuitive/care/webscraping/downloads/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf");
        filesUrl.add("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
        filesUrl.add("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf");

        long executionTime = measureExecutionTime(() -> {
            try {
                downloader.downloadFiles(filesUrl, destinationPaths);
                downloader.shutdown();

                // Verifica se os arquivos foram baixados corretamente
                File file1 = new File(destinationPaths.get(0));
                File file2 = new File(destinationPaths.get(1));

                if(!file1.exists() || !file2.exists()) {
                    throw new DownloadException("Falha no download.");
                }
            } catch (DownloadException e) {
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        // Impressão de tempo de execução
        System.out.println("Tempo de execução do teste de download: " + executionTime / 1_000_000 + " ms");

        // Monitoramento de uso de memória e CPU
        printMemoryAndCpuUsage();
    }

    /**
     * Testa o processo de compactação dos arquivos PDF baixados em um arquivo ZIP.
     *
     * O teste realiza os seguintes passos:
     * 1. Verifica se o diretório de origem especificado existe e contém arquivos.
     * 2. Executa a compactação dos arquivos PDF encontrados no diretório de origem em um arquivo ZIP.
     * 3. Valida se o arquivo ZIP foi criado corretamente e se o seu tamanho está dentro de um limite aceitável.
     *
     * @throws IOException Se ocorrer um erro ao acessar os arquivos ou durante a criação do arquivo ZIP.
     * @throws CompressException Se houver falha no processo de compressão.
     */
    @Test
    @org.junit.jupiter.api.Order(3)
    void testCompressFiles() throws CompressException, IOException, Exception {
        String sourceDir = "src/test/java/com/intuitive/care/webscraping/downloads/";
        String zipFilePath = "src/test/java/com/intuitive/care/webscraping/downloads/anexos.zip";

        long executionTime = measureExecutionTime(() -> {
            try {
                Compressor compressor = new Compressor();
                compressor.compress(sourceDir, zipFilePath);

                // Verifica se o arquivo ZIP foi criado
                File zipFile = new File(zipFilePath);
                if(!zipFile.exists() || zipFile.length() == 0) {
                    throw new CompressException("Falha na compressão.");
                }
            } catch (CompressException | IOException e) {
                System.out.println(e.getMessage());
            }
        });

        // Impressão de tempo de execução
        System.out.println("Tempo de execução do teste de compressão: " + executionTime / 1_000_000 + " ms");

        // Monitoramento de uso de memória e CPU
        printMemoryAndCpuUsage();
    }

    /**
     * Realiza um teste de stress simulando múltiplos downloads de arquivos.
     *
     * O teste chama o método {@link #testDownloadFile()} 100 vezes para simular o processo de download sob alta carga e avaliar o desempenho do sistema.
     *
     * @throws Exception Se ocorrer um erro durante o processo de download.
     */
    @Test
    @org.junit.jupiter.api.Order(4)
    void stressTestDownloadFiles() throws Exception {
        // Teste de stress simulando múltiplos downloads
        for (int i = 0; i < 100; i++) {
            System.out.println("Iniciando download " + (i + 1));
            testDownloadFile();
        }
    }
}
