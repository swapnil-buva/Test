package com.hackerrank.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataExtractor {

    public static Map<String, Integer> findTotalCasesByContinent(final WebDriver driver, final String pageUrl) {
      ArrayList<WebElement> continent = new ArrayList<>(driver.findElements(By.xpath("//tr/td[5]")));  
      // System.out.println(continent.size());
      Map<String, Integer> count = new HashMap<String, Integer>();
      return count;
    }

    public static Map<String, Integer> findTotalCountryByContinent(final WebDriver driver, final String pageUrl) {
        return null;
    }
}
