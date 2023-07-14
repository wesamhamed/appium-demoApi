package Android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class FirstAndroidTest {

	AndroidDriver driver;
	Actions actions;
	File classPath,imgDir,img;

	@BeforeMethod
	public void setUp() {


		String applicationPath = System.getProperty("user.dir")+"/src/test/resources/apps/Demo.apk";
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "android");
		caps.setCapability("automationName","UiAutomator2");
		caps.setCapability("platformVersion","11");
		caps.setCapability("deviceName","emulator11");
		caps.setCapability("app",applicationPath);
		caps.setCapability("noReset", true);
//		caps.setCapability("fullReset", true);
//        caps.setCapability("appPackage","com.android.calculator2");
//        caps.setCapability("appActivity",".Calculator");
		try {
			driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"),caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void scroll_test(){

		WebElement viewElement = driver.findElement(By.xpath("//android.widget.TextView[@text='Views']"));

		//Tap
		actions = new Actions(driver);
//		actions.click(viewElement).build().perform();
		viewElement.click();
		WebElement listElement = driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
						".scrollIntoView(new UiSelector().text(\"Lists\"))"));

//		actions.moveToElement(listElement).click().build().perform();

	}
	@Test
	public void drag_drop(){
		WebElement viewElement = driver.findElement(By.xpath("//android.widget.TextView[@text='Views']"));
		viewElement.click();
		WebElement drag_dropElement = driver.findElement(By.xpath("//android.widget.TextView[@text='Drag and Drop']"));
		drag_dropElement.click();
		WebElement dragElement  = driver.findElement(By.id("drag_dot_1"));
		WebElement dropElement = driver.findElement(By.id("drag_dot_2"));
		actions = new Actions(driver);

//		actions.clickAndHold(dragElement).moveToElement(dropElement).release().build().perform();
		actions.dragAndDrop(dragElement,dropElement);
	}
	@Test
	public void swipe(){
		WebElement viewElement = driver.findElement(By.xpath("//android.widget.TextView[@text='Views']"));
		viewElement.click();
		WebElement galleryElement = driver.findElement(By.xpath("//android.widget.TextView[@text='Gallery']"));
		galleryElement.click();
		WebElement photo1Element = driver.findElement(By.xpath("//android.widget.TextView[@text='1. Photos']"));
		photo1Element.click();
		WebElement photoElement = driver.findElement(By.xpath("//android.widget.ImageView[@index='2']"));
		photoElement.click();

	}
	@Test
	public void send_photo() throws IOException {
		classPath = new File(System.getProperty("user.dir"));
		imgDir = new File(classPath,"/test/resources/images");
		img = new File("img.png");

		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(null)).click();

		String Android_Photo_Path = "mnt/sdcard/Pictures";
		driver.pushFile(Android_Photo_Path+"/"+img.getName(),img);
		wait.until(ExpectedConditions.numberOfElementsToBe(null,1))
	}
	@Test
	public void clipboard_test(){
		String text = "Hello";
		driver.setClipboardText(text);

		WebElement photoElement = driver.findElement(By.xpath("//android.widget.ImageView[@index='2']"));

		photoElement.clear();
		photoElement.sendKeys(driver.getClipboardText());

	}
	@AfterMethod
	public void tearDown(){
		if(driver!=null){
			driver.quit();
		}
	}
}