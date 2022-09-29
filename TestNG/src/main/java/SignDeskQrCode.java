import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SignDeskQrCode {
	WebDriver driver;
	
	@Test
	public void getqrcode() throws InterruptedException, IOException {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
		driver.get("https://signdesk.in/emandate-uat/#/inviteemandate/create/495cb5bdf6d9bbb686d8d0a71b230925900dfa13e331482f/invite");
		driver.findElement(By.xpath("//img[@src='./assets/img/pdf-preview.png']")).click();
		Thread.sleep(5000);
		WebElement ele = driver.findElement(By.xpath("//div[@class='textLayer']"));
		Rectangle dimension = ele.getRect();
		System.out.println(dimension.getDimension());
		File file =ele.getScreenshotAs(OutputType.FILE);
		File DestFile=new File("C:\\Users\\Amit\\git\\repository\\TestNG\\src\\main\\resources\\image.png");
		FileUtils.copyFile(file, DestFile);
		BufferedImage croppedImage = DestFile.getSubimage(x, y, width, height);
		
	}
	
	@AfterTest
	public void endtest() {
		driver.quit();
	}

}
