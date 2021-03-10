package Wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WebPageWrapper {
    WebDriver driver;
    public int implicitWaitSeconds = 0;
    public int timeOutSeconds = 0;
    public long explicitWait = 10;
    public long milisecondsInterval = 100;
    WebElementWrapper testElement;


    public WebPageWrapper(WebDriver driver)
    {
        this.driver = driver;
    }

    public WebPageWrapper(String browser)
    {
        OpenBrowser(browser);
    }

    public void OpenBrowser(String browser)
    {
        switch (browser)
        {
            case "gc":
                this.driver = new ChromeDriver();
                break;
            case "ff":
                this.driver = new FirefoxDriver();
                break;
            default:
                this.driver = new ChromeDriver();
                break;

        }
    }

    public void LoadWebPage(String url)
    {
        this.driver.get(url);
    }

    public void MaximizeWindow()
    {
        this.driver.manage().window().maximize();
    }

    public void CloseBrowser()
    {
        this.driver.close();
    }

    public void RefreshBrowser()
    {
        this.driver.navigate().refresh();
    }

    public void UpdateExplicitWait(int seconds)
    {
        if(seconds > 0)
        {
            this.explicitWait = seconds;
        }
    }

    public void UpdateTimeOut(int seconds)
    {
        if(seconds > 0)
        {
            this.timeOutSeconds = seconds;
        }
    }

    public WebElementWrapper GetElementText(WebElementWrapper we)
    {
        SearchForThisElement(we);
        we.elementText = null;
        if (we.GetAmountElements() == 1)
        {
            WebElement result = we.ReturnTheIWebElementInPosition(1);
            we.elementText = result.getText();
        }
        return we;
    }

    public WebElementWrapper ClickElement(WebElementWrapper we)
    {
        we = SearchForThisElement(we);
        if (we.GetAmountElements() == 1)
        {
            WebElement result = we.ReturnTheIWebElementInPosition(1);
            Actions actions = NewActionsObject();
            actions.click(result);
            actions.perform();
        }
        return we;
    }


    public WebElementWrapper EnterTextInElement(WebElementWrapper we, String text)
    {
        we = SearchForThisElement(we);
        if (we.GetAmountElements() == 1)
        {
            WebElement result = we.ReturnTheIWebElementInPosition(1);
            result.sendKeys(text);
        }
        return we;
    }

    public Actions NewActionsObject()
    {
        Actions actions = new Actions(driver);
        return actions;
    }


    public WebElementWrapper MoveIntoViewToThisElement(WebElementWrapper we)
    {
        SearchForThisElement(we);
        if (we.GetAmountElements() == 1)
        {
            Actions actions = NewActionsObject();
            actions.moveToElement(we.allMatchingResults.get(0));
            actions.perform();
        }
        return we;
    }

    public WebElementWrapper ClearTextBoxText(WebElementWrapper we)
    {
        we = SearchForThisElement(we);
        if (we.GetAmountElements() == 1)
        {
            WebElement results = we.ReturnTheIWebElementInPosition(1);
            results.sendKeys(Keys.CONTROL + "a");
            results.sendKeys(Keys.DELETE);
        }
        return we;
    }



    public WebElementWrapper SearchForThisElement(WebElementWrapper we)
    {
        we.allMatchingResults = new ArrayList<>();
        switch(we.selectorMethod.toLowerCase(Locale.ROOT))
        {
            case "id":
                List<WebElement> elementsListID = this.driver.findElements(By.id(we.selector));
                elementsListID.forEach(iwe -> {
                    we.allMatchingResults.add(iwe);
                });
                break;
            case "class":
                List<WebElement> elementsListClass = this.driver.findElements(By.className(we.selector));
                elementsListClass.forEach(iwe -> {
                    we.allMatchingResults.add(iwe);
                });
                break;
            case "name":
                List<WebElement> elementsListName = this.driver.findElements(By.name(we.selector));
                elementsListName.forEach(iwe -> {
                    we.allMatchingResults.add(iwe);
                });
                break;
            case "css":
                List<WebElement> elementsListCSS = this.driver.findElements(By.cssSelector(we.selector));
                elementsListCSS.forEach(iwe -> {
                    we.allMatchingResults.add(iwe);
                });
                break;
            case "xpath":
                List<WebElement> elementsListXpath = this.driver.findElements(By.xpath(we.selector));
                elementsListXpath.forEach(iwe -> {
                    we.allMatchingResults.add(iwe);
                });
                break;
            case "linktext":
                List<WebElement> elementsListLinkText = this.driver.findElements(By.linkText(we.selector));
                elementsListLinkText.forEach(iwe -> {
                    we.allMatchingResults.add(iwe);
                });
                break;
        }
        we.CountMatchingElements();
        this.testElement = we;
        return we;
    }

    public boolean SearchForThisElementForAnAmountOfTime(WebElementWrapper we)
    {
        we.allMatchingResults = new ArrayList<>();
        int timer = 0;
        boolean isDisplayed = false;
        long cycles = this.explicitWait * 1000 / this.milisecondsInterval;
        switch (we.selectorMethod.toLowerCase())
        {
            case "id":
                while(timer < cycles)
                {
                    List<WebElement> elementsList = this.driver.findElements(By.id(we.selector));
                    elementsList.forEach(iwe -> {
                        we.allMatchingResults.add(iwe);
                    });
                    we.CountMatchingElements();
                    if (we.GetAmountElements() > 0)
                    {
                        isDisplayed = true;
                        break;
                    }
                    else
                    {
                        timer = timer + 1;
                    }
                    pause(milisecondsInterval);
                }
                break;
            case "class":
                while (timer < cycles)
                {
                    List<WebElement> elementsList = this.driver.findElements(By.className(we.selector));
                    elementsList.forEach(iwe -> {
                        we.allMatchingResults.add(iwe);
                    });
                    we.CountMatchingElements();
                    if (we.GetAmountElements() > 0)
                    {
                        isDisplayed = true;
                        break;
                    }
                    else
                    {
                        timer = timer + 1;
                    }
                    pause(milisecondsInterval);
                }
                break;
            case "name":
                while (timer < cycles)
                {
                    List<WebElement> elementsList = this.driver.findElements(By.name(we.selector));
                    elementsList.forEach(iwe -> {
                        we.allMatchingResults.add(iwe);
                    });
                    we.CountMatchingElements();
                    if (we.GetAmountElements() > 0)
                    {
                        isDisplayed = true;
                        break;
                    }
                    else
                    {
                        timer = timer + 1;
                    }
                    pause(milisecondsInterval);
                }
                break;
            case "css":
                while (timer < cycles)
                {
                    List<WebElement> elementsList = this.driver.findElements(By.cssSelector(we.selector));
                    elementsList.forEach(iwe -> {
                        we.allMatchingResults.add(iwe);
                    });
                    we.CountMatchingElements();
                    if (we.GetAmountElements() > 0)
                    {
                        isDisplayed = true;
                        break;
                    }
                    else
                    {
                        timer = timer + 1;
                    }
                    pause(milisecondsInterval);
                }
                break;
            case "xpath":
                while (timer < cycles)
                {
                    List<WebElement> elementsList = this.driver.findElements(By.xpath(we.selector));
                    elementsList.forEach(iwe -> {
                        we.allMatchingResults.add(iwe);
                    });
                    we.CountMatchingElements();
                    if (we.GetAmountElements() > 0)
                    {
                        isDisplayed = true;
                        break;
                    }
                    else
                    {
                        timer = timer + 1;
                    }
                    pause(milisecondsInterval);
                }
                break;
            case "linktext":
                while (timer < cycles)
                {
                    List<WebElement> elementsList = this.driver.findElements(By.linkText(we.selector));
                    elementsList.forEach(iwe -> {
                        we.allMatchingResults.add(iwe);
                    });
                    we.CountMatchingElements();
                    if (we.GetAmountElements() > 0)
                    {
                        isDisplayed = true;
                        break;
                    }
                    else
                    {
                        timer = timer + 1;
                    }
                    pause(milisecondsInterval);
                }
                break;
        }
        we.CountMatchingElements();
        this.testElement = we;
        return isDisplayed;
    }

    public void pause(long ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

}
