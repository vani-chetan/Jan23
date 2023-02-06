package com.mycompany.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class MLCalc {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.of(60, ChronoUnit.SECONDS));
        //implicitly wait: wait for all the webelements for this max time which in this case is 20
        driver.manage().timeouts().implicitlyWait(Duration.of(20, ChronoUnit.SECONDS));
        driver.navigate().to("https://www.mlcalc.com/");
//clear the purchase price field
        WebElement purchasePrice = driver.findElement(By.id("ma"));
        purchasePrice.clear();
        purchasePrice.sendKeys("600000");
        //explicit wait
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@id='mortgageForm']//*[text()='Show advanced options']")));
        driver.findElement(By.xpath("//form[@id='mortgageForm']//*[text()='Show advanced options']")).click();
        new Select(driver.findElement(By.cssSelector("[id='fpdd']"))).selectByVisibleText("Feb");
        driver.findElement(By.cssSelector("#mortgageForm [value='Calculate']")).click();
        //get monthly installment from the page
        // check if the monthly installment is less than 2000.00, print fail else pass.

        WebElement mon_Payment = driver.findElement(By.xpath("//table[@id='summary']//tr//tr[1]//td[1]//div[1]"));
        String monthlyPayment = mon_Payment.getText();
        String amount = monthlyPayment.replaceAll("[^a-zA-Z0-9]", "");
       // System.out.println(amount);
        double stringToDoublePayment = Double.parseDouble(amount);
        //System.out.println(stringToDoublePayment);
        double payment = 2000.000d;
        if (stringToDoublePayment < payment) {
            System.out.println("Fail");
        } else {
            System.out.println("Pass");
        }
    }
}
