import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args){
        WebDriver driver = new ChromeDriver();
        String url = "https://books.toscrape.com/catalogue/%s/index.html";
        String[] bookNamesWithId = {"set-me-free_988", "a-light-in-the-attic_1000", "soumission_998"};

        for (String i : bookNamesWithId) {
            try {
                String formattedUrl = String.format(url, i);
                driver.get(formattedUrl);
                WebElement productMainDiv = driver.findElement(By.cssSelector("div.col-sm-6.product_min"));
                WebElement h1Element = productMainDiv.findElement(By.tagName("h1"));
                String h1Text = h1Element.getText();

                System.out.printf("Book name inside the page: '%s' \n Book name in the URL: '%s' \n Book name in the URL after conversion: '%s' \n Both names are %s  \n\n"
                        , h1Text, i, convertToTitleCase(i), compareNames(h1Text, convertToTitleCase(i)));

            } catch (Exception  e) { System.err.printf("An error has occurred, verify whether or not the elements still exist \n\n %s \n\n", e.getMessage());}
        }
        driver.quit();
    }

    // Convert book name from URL format to title case
    public static String convertToTitleCase(String bookNameToConvert){
        bookNameToConvert = bookNameToConvert.replaceAll("-", " ");
        StringBuilder sb = new StringBuilder();
        String[] parts = bookNameToConvert.split(" ");

        // Capitalize the first letter of each word and append to StringBuilder
        for (String part : parts) {
            sb.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1))
                    .append(" ");
        }
        // Converts sb to a string, splits it and stores into an array containing: 0 - name, 1 - id
        String[] nameAndId = sb.toString().split("_");

        return nameAndId[0];
    }

    public static String compareNames(String expectedText, String actualText){
        return (expectedText.equalsIgnoreCase(actualText)) ? "IDENTICAL" : "DIFFERENT";
    }
}