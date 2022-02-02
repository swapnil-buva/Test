package Selenium_challenge;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.Collections;

public class SwagLabs {
    public static void main(String[] args)
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver =  new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        ArrayList<WebElement> item_price = new ArrayList<>(driver.findElements(By.cssSelector(".inventory_item_price")));
        ArrayList<Float> price = new ArrayList<>();
        System.out.println("Size: "+item_price.size());
        for(WebElement item:item_price)
        {
//            System.out.println(item.getText());
//            System.out.println(Float.parseFloat(item.getText().substring(1)));
            price.add(Float.parseFloat(item.getText().substring(1)));
        }
        Collections.sort(price);
        System.out.println(price.get(price.size() - 1));
        driver.findElement(By.xpath("//div[@class='inventory_item_price' and text()='"+ (price.get(price.size() - 1)) +"']//following-sibling::button")).click();
        driver.findElement(By.cssSelector("#shopping_cart_container>a")).click();
        Float cart_price = Float.parseFloat(driver.findElement(By.xpath("//div[@class='cart_item_label']//parent::div//div[@class='inventory_item_price']")).getText().substring(1));
//        System.out.println(cart_price);
        Assert.assertEquals(cart_price, price.get(price.size()-1));
        driver.quit();

    }
}
