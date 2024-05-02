package com.example;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Unit test for simple App.
 */
public class AppTest 
{ 
    WebDriver driver;
   Logger logger=Logger.getLogger(getClass());
   @BeforeTest
   public void BeforeTest()
   {  
    PropertyConfigurator.configure("E:\\Users\\Model Lab\\OpenTable(Model)\\Model Lab\\log4j2.properties");
    WebDriverManager.chromedriver().setup();
    driver=new ChromeDriver();
    driver.get("https://www.opentable.com/");
    // driver.manage().window().maximize();
    logger.info("Open Url");

   }
   @Test(dataProvider = "ptd")
   public void Test1(String cityname) throws InterruptedException
   {  
       WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"home-page-autocomplete-input\"]")));
       logger.warn("Wait until element is located");
       driver.findElement(By.xpath("//*[@id=\"home-page-autocomplete-input\"]")).sendKeys(cityname);
       logger.info("Data Fetched from excel and entered in Search Box");
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[2]/button")));
       driver.findElement(By.xpath("//*[@id='mainContent']/header/div/span/div/div/div[2]/div[2]/button")).click();
       logger.info("Lets go Button clicked");
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]")));
       driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]")).click();
       logger.info("Asian checkbox is clicked under cuisine filter");
       Thread.sleep(2000);
       logger.warn("sleep for 2 secs");
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/main/div/div/div[2]/div/div[2]/div/div[1]/a/h6")));
       driver.findElement(By.xpath("/html/body/div[1]/div/div/main/div/div/div[2]/div/div[2]/div/div[1]/a/h6")).click();
       logger.info("Click on first result");
       Set<String> tabid = driver.getWindowHandles();
        String window = "";
        for(String k:tabid)
        {
            window=k;
        }
        driver.switchTo().window(window);
        Thread.sleep(3000);
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,500)", "");
        WebElement element = driver.findElement(By.xpath("//*[@id=\"restProfileMainContentDtpPartySizePicker\"]"));
        Select select = new Select(element);
        select.selectByValue("4");
        logger.info("selected 4 people");
        driver.findElement(By.xpath("//*[@id=\"restProfileMainContentDtpDayPicker-label\"]")).click();
        String month="";
        while(!month.contains("November"))
        {
        driver.findElement(By.xpath("//*[@id=\"restProfileMainContentDtpDayPicker-wrapper\"]/div/div/div/div/div[2]/button[2]")).click();
        month = driver.findElement(By.xpath("//*[@id='react-day-picker-1']")).getText();
        }
        logger.info("Selected November");
      
       driver.findElement(By.xpath("//*[@id='restProfileMainContentDtpDayPicker-wrapper']/div/div/div/table/tbody/tr[3]/td[2]/button")).click();
       logger.info("Date 11 is selected");
       Thread.sleep(2000);
       WebElement time=driver.findElement(By.xpath("//*[@id='restProfileMainContenttimePickerDtpPicker']"));
       Select stime=new Select(time);
       stime.selectByVisibleText("6:30 PM");
    }
   Object[] obj=new Object[1];
   @org.testng.annotations.DataProvider(name="ptd")
   public Object[] DataProvider() throws IOException
   {
       FileInputStream fi=new FileInputStream("E:\\Users\\Model Lab\\OpenTable(Model)\\Model Lab\\example.xlsx");
       XSSFWorkbook workbook=new XSSFWorkbook(fi);
       XSSFSheet sheet=workbook.getSheetAt(0);
       Row r=sheet.getRow(0);
       String cityname=r.getCell(0).getStringCellValue();
       obj[0]=cityname;
       return obj;
   }
}
