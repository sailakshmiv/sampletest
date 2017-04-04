package com.boomerang.canvas.pageactions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.boomerang.canvas.testbase.Testbase;

public class Headerwidget extends Testbase {

  

  public void sentDates(String startDate, String endDate) throws Exception {
	  Thread.sleep(2000);
  getobject("date_placeholder").click();
   WebElement startdate = getobject("start_date_text");
   startdate.clear();
   startdate.sendKeys(startDate);
   startdate.sendKeys(Keys.ENTER);
   WebElement enddate = getobject("end_date_text");
   enddate.clear();
   enddate.sendKeys(endDate);
   enddate.sendKeys(Keys.ENTER);
   Thread.sleep(3000);
   getobject("date_placeholder").click();
  }
  
  public String getRevenueText() throws Exception {
	    return getobject("hd_text_revenue").getText();
	  }

	  public String getMaginText() throws Exception {
	    return getobject("hd_margin_text").getText();
	  }
  public String getRevenueValueInDollars() throws Exception {
    return getobject("hd_revenue_value").getText();
  }

  public String getRevenuePercentYoY() throws Exception {
    return getobject("hd_revenue_percent_yoy").getText();
  }

  public String getRevenuePercentPvP() throws Exception {
    return getobject("hd_revenue_percent_pvp").getText();
  }

  public String getMarginValueInDollars() throws Exception {
    return getobject("hd_margin_value").getText();
  }

  public String getMarginPercentYoY() throws Exception {
    return getobject("hd_margin_percent_yoy").getText();
  }

  public String getMarginPercentPvP() throws Exception {
    return getobject("hd_margin_percent_pvp").getText();
  }
}
