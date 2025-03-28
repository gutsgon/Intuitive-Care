package com.intuitive.care.webscraping.scraper;

// Connection imports
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// Exception imports
import com.intuitive.care.webscraping.exception.ScraperException;
import java.io.IOException;

// Util imports
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por realizar Web Scraping em páginas HTML para extrair links de arquivos PDF.
 * <p>
 * Utiliza a biblioteca Jsoup para conectar-se à URL e buscar os links desejados.
 * </p>
 */
public class WebScraper {

    /**
     * Realiza scraping na página informada e retorna uma lista de links de arquivos PDF.
     * <p>
     * O método conecta-se à página, busca todos os links que terminam em ".pdf" ou ".PDF",
     * filtra apenas os que possuem os textos "Anexo I" ou "Anexo II" e retorna a lista de URLs completas.
     * </p>
     *
     * @param url URL da página onde os PDFs estão localizados.
     * @return Lista de URLs dos arquivos PDF encontrados.
     * @throws ScraperException Se ocorrer um erro durante o scraping ou se nenhum PDF for encontrado.
     */
    public List<String> scrapePDFLinks(String url) throws ScraperException, IOException {
        try {
            System.out.println("Iniciando scraping na URL: " + url);

            // Conectando à página e extraindo o documento HTML
            Document document = Jsoup.connect(url).get();

            // Selecionando todos os links que terminam com ".pdf" ou ".PDF"
            Elements pdfElements = document.select("a[href$=.pdf], a[href$=.PDF]");

            if (pdfElements.isEmpty()) {
                throw new ScraperException("Nenhum link de PDF foi encontrado na página: " + url);
            }

            // Filtrando links que possuem os textos "Anexo I" ou "Anexo II"
            List<String> pdfLinks = pdfElements.stream()
                    .filter(element -> {
                        String text = element.text().trim().replaceAll("[^a-zA-Z0-9 ]", "");
                        return text.equalsIgnoreCase("Anexo I") || text.equalsIgnoreCase("Anexo II");
                    })
                    .map(element -> element.attr("href"))
                    .map(link -> link.startsWith("http") ? link : "https://www.gov.br" + link)
                    .collect(Collectors.toList());

            if (pdfLinks.isEmpty()) {
                throw new ScraperException("Nenhum link de PDF com os critérios especificados foi encontrado na página.");
            }

            return pdfLinks;

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (ScraperException e) {
            throw new ScraperException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
