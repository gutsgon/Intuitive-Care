package com.intuitive.care.webscraping;

//Dependências
import com.intuitive.care.webscraping.downloader.FileDownloader;
import com.intuitive.care.webscraping.scraper.WebScraper;
import com.intuitive.care.webscraping.compressor.Compressor;

// Exceções utilizadas
import com.intuitive.care.webscraping.exception.CompressException;
import com.intuitive.care.webscraping.exception.DownloadException;
import com.intuitive.care.webscraping.exception.ScraperException;
import java.io.IOException;

// Utilitários utilizados
import java.util.List;

/**
 * Classe principal responsável por integrar os processos de Web Scraping, Download e Compactação de arquivos PDF.
 *
 * Este programa realiza as seguintes operações:
 * 1. Realiza o scraping de links de arquivos PDF de uma página web especificada.
 * 2. Faz o download dos arquivos PDF para um diretório local.
 * 3. Compacta os arquivos PDF baixados em um arquivo ZIP.
 *
 * O processo é realizado na seguinte sequência:
 * 1. Scraping de links de PDFs.
 * 2. Download dos arquivos PDF.
 * 3. Compactação dos arquivos PDF.
 *
 * As exceções tratadas incluem problemas com a extração dos links, falhas no download dos arquivos e falhas durante o processo de compactação.
 *
 * Exceções:
 * - {@link ScraperException}: Lançada se ocorrer falha durante o processo de scraping (por exemplo, erro de conexão ou erro ao extrair os links).
 * - {@link IOException}: Lida durante erros de entrada/saída, como falhas de rede durante o download dos arquivos.
 * - {@link DownloadException}: Lançada se ocorrer falha ao tentar baixar os arquivos PDF.
 * - {@link CompressException}: Lançada se ocorrer falha ao tentar compactar os arquivos PDF.
 *
 * @author [Gustavo Gonçalves]
 */
public class Main {

    /**
     * Método principal que executa as operações de scraping, download e compactação de arquivos PDF.
     *
     * @param args Parâmetros passados na linha de comando (não utilizados).
     * @throws IOException Se ocorrer erro de I/O durante o download dos arquivos.
     * @throws CompressException Se ocorrer erro ao tentar compactar os arquivos.
     * @throws DownloadException Se ocorrer erro ao tentar baixar os arquivos.
     * @throws ScraperException Se ocorrer erro durante o scraping da página para obter os links de PDF.
     */
    public static void main(String[] args) throws IOException, CompressException, DownloadException, ScraperException {
        // URL da página onde os PDFs estão localizados
        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

        // Diretório de download dos arquivos PDF
        String downloadDir = "src/test/java/com/intuitive/care/webscraping/downloads";

        // Caminho do arquivo ZIP onde os PDFs compactados serão armazenados
        String zipFilePath = "src/test/java/com/intuitive/care/webscraping/downloads/anexos.zip";

        // Instanciamento das classes que realizam o scraping, download e compressão
        WebScraper scraper = new WebScraper();
        FileDownloader downloader = new FileDownloader();
        Compressor compressor = new Compressor();

        try {
            // 1. Obter links dos PDFs da página
            System.out.println("Iniciando o scraping dos links de PDF...");
            List<String> pdfLinks = scraper.scrapePDFLinks(url);

            if (pdfLinks.isEmpty()) {
                // Caso não sejam encontrados links de PDF
                throw new ScraperException("Nenhum link de PDF foi encontrado na página.");
            }

            System.out.println("Total de PDFs encontrados: " + pdfLinks.size());

            // 2. Baixar os arquivos PDF
                String fileName1 = pdfLinks.getFirst().substring(pdfLinks.getFirst().lastIndexOf("/") + 1);
                String fileName2 = pdfLinks.getLast().substring(pdfLinks.getLast().lastIndexOf("/") + 1);
                List<String> destinationPath = List.of(
                        downloadDir + "/" + fileName1,
                        downloadDir + "/" + fileName2
                );

                try {
                    System.out.println("Baixando arquivo: " + fileName1);
                    System.out.println("Baixando arquivo: " + fileName2);
                    downloader.downloadFiles(pdfLinks, destinationPath);
                } catch (DownloadException e) {
                    // Captura falha ao tentar baixar o arquivo
                    throw new DownloadException("Erro ao baixar: " + fileName1 + "\n" + fileName2, e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            // 3. Compactar os arquivos baixados
            System.out.println("Iniciando a compactação dos arquivos baixados...");
            try {
                compressor.compress(downloadDir, zipFilePath);
            } catch (CompressException e) {
                // Captura falha ao tentar compactar os arquivos
                throw new CompressException("Erro durante a compressão dos arquivos.", e);
            }

            System.exit(0);

        } catch (ScraperException | IOException e) {
            // Captura falha no processo de scraping ou de I/O
            throw new ScraperException("Erro durante o scraping dos PDFs.", e);
        }catch (DownloadException e){
            System.out.println(e.getMessage());
        }
        catch (CompressException e){
            System.out.println(e.getMessage());
        }
    }
}
