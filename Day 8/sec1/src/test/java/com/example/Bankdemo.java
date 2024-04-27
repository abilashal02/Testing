package com.example;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Bankdemo {
    @Test(dataProvider = "unpas")
    public void test1(String uname,String pass) throws InterruptedException
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://dbankdemo.com/bank/login");
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys(uname);
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(pass);
        driver.findElement(By.xpath("//*[@id='submit']")).submit();
       

    }
    @Test(dataProvider = "unpas")
    public void test2(String uname,String pass) throws InterruptedException
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://dbankdemo.com/bank/login");
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys(uname);
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(pass);
        driver.findElement(By.xpath("//*[@id='submit']")).submit();
        driver.findElement(By.xpath("//*[@id='deposit-menu-item']")).click();
        WebElement element = driver.findElement(By.xpath("//*[@id='selectedAccount']"));
        Select select = new Select(element);
        select.selectByValue("434969");
        driver.findElement(By.xpath("//*[@id='amount']")).sendKeys("5000");
        driver.findElement(By.xpath("//*[@id='right-panel']/div[2]/div/div/div/div/form/div[2]/button[1]")).submit();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,600)","");
        String depamnt = driver.findElement(By.xpath("//*[@id='transactionTable']/tbody/tr[1]/td[4]")).getText();
        if(depamnt.contains("5000"))
        System.out.print("Deposit Found");
        else
        System.out.print("Deposit Not Found");
        
    }
    @Test(dataProvider = "unpas")
    public void test3(String uname,String pass) throws InterruptedException
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://dbankdemo.com/bank/login");
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys(uname);
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(pass);
        driver.findElement(By.xpath("//*[@id='submit']")).submit();
        driver.findElement(By.xpath("//*[@id='withdraw-menu-item']")).click();
        WebElement element = driver.findElement(By.xpath("//*[@id='selectedAccount']"));
        Select select = new Select(element);
        select.selectByValue("434969");
        driver.findElement(By.xpath("//*[@id='amount']")).sendKeys("3000");
        driver.findElement(By.xpath("//*[@id='right-panel']/div[2]/div/div/div/div/form/div[2]/button[1]")).submit();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,600)","");
        String depamnt = driver.findElement(By.xpath("//*[@id='transactionTable']/tbody/tr[1]/td[4]")).getText();
        if(depamnt.contains("3000"))
        System.out.print("Withdrawl Found");
        else
        System.out.print("Withdrawl Not Found");
        
    }
    @DataProvider(name = "unpas")
    public Object[][] fetchdata() throws IOException
    {
        Object[][] data = new Object[1][2];
        data[0][0] = "S@gmail.com";
        data[0][1] = "P@ssword12";
        return data;
    }
}
