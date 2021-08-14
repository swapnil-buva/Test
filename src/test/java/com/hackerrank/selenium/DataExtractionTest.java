package com.hackerrank.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.hackerrank.selenium.server.JettyServer;
import com.hackerrank.selenium.server.StatsServlet;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DataExtractionTest {
    private static JettyServer server = null;
    private static WebDriver driver = null;
    private static String pagUrl = null;

    private static Integer seed;
    private static String rand;
    private static List<String> doc;

    @BeforeClass
    public static void setup() throws IOException {
        driver = new HtmlUnitDriver(BrowserVersion.CHROME, true) {
            @Override
            protected WebClient newWebClient(BrowserVersion version) {
                WebClient webClient = super.newWebClient(version);
                webClient.getOptions().setThrowExceptionOnScriptError(false);

                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

                return webClient;
            }
        };
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        server = new JettyServer();
        server.start();

        pagUrl = "http://localhost:8080/home.html";

        //rands
        seed = new Random().nextInt(99) + 1;
        rand = "Random" + seed + "=" + "Random" + seed;
        driver.get(pagUrl + "?seed=" + seed + "&rand=" + rand);

        doc = IOUtils.readLines(StatsServlet.class.getResourceAsStream("/country_continent.txt"), StandardCharsets.UTF_8);
        doc.add(rand);
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
        server.stop();
    }

    @Test
    public void testFindTotalCasesByContinent() throws InterruptedException {
        Map<String, Integer> actual = DataExtractor.findTotalCasesByContinent(driver, pagUrl);
        Map<String, Long> expected = doc.stream().map(k -> k.split("=")[1]).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Assert.assertEquals(expected.size(), actual.size());
        for (String k : expected.keySet()) {
            Assert.assertEquals(((seed * 1000) + 700) * expected.get(k), actual.get(k).longValue());
        }
    }

    @Test
    public void testFindTotalCountryByContinent() {
        Map<String, Integer> actual = DataExtractor.findTotalCountryByContinent(driver, pagUrl);
        Map<String, Long> expected = doc.stream().map(k -> k.split("=")[1]).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Assert.assertEquals(expected.size(), actual.size());
        for (String k : expected.keySet()) {
            Assert.assertEquals(expected.get(k).intValue(), actual.get(k).intValue());
        }
    }
}
