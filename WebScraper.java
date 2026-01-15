import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class WebScraper {
    public static void main(String[] args) {
        String url = "https://books.toscrape.com/";
        String csvFile = "products.csv";

        try {

            Document doc = Jsoup.connect(url).get();
            Elements products = doc.select("article.product_pod");
            FileWriter writer = new FileWriter(csvFile);
            writer.append("Name,Price,Rating\n");

            for (Element product : products) {

                String name = product.select("h3 a").attr("title");
                String price = product.select(".price_color").text();
                String ratingClass = product.select("p.star-rating").attr("class");
                String rating = ratingClass.replace("star-rating", "").trim();
                writer.append(name.replace(",", " "))
                        .append(",")
                        .append(price)
                        .append(",")
                        .append(rating)
                        .append("\n");
            }
            writer.flush();
            writer.close();
            System.out.println("Data successfully saved to products.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
