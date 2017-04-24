package com.boomerang.canvas.scoreboard;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.boomerang.canvas.testbase.Testbase;

public class ScoreboardWidgetActions extends Testbase {


  public String MarginTextPresent() throws Exception {
    return getobject("margin_text").getText();
  }
  public void Scoreboardbutton() throws Exception {
	    getobject("scoreboard_button").click();
	  }
  public String getMarginValue() throws Exception {
    return getobject("margin_value").getText();
  }

  public String getMarginYoYPercent() throws Exception {
    return getobject("margin_percent_yoy").getText();
  }

  public String getMargingPvPPercent() throws Exception {
    return getobject("margin_percent_pvp").getText();
  }

  public String isRevenueTextPresent() throws Exception {
    return getobject("revenue_text").getText();
  }

  public String getRevenueValue() throws Exception {
    return getobject("revenue_value").getText();
  }

  public String getRevenueYoYPercent() throws Exception {
    return getobject("revenue_percent_yoy").getText();
  }

  public String getRevenuePvPPercent() throws Exception {
    return getobject("revenue_percent_pvp").getText();
  }

  public String isUnitsTextPresent() throws Exception {
    return getobject("units_text").getText();
  }

  public String getUnitsValue() throws Exception {
    return getobject("units_sold_value").getText();
  }

  public String getUnitsYoYPercent() throws Exception {
    return getobject("units_sold_percent_yoy").getText();
  }

  public String getUnitsPvPPercent() throws Exception {
    return getobject("units_sold_percent_pvp").getText();
  }

  public String isAvgSalePriceTextPresent() throws Exception {
    return getobject("avg_sale_price_text").getText();
  }

  public String getAvgSalePriceValue() throws Exception {
    return getobject("avg_sale_price_value").getText();
  }

  public String getAvgSalePriceYoYPercent() throws Exception {
    return getobject("avg_sale_price_percent_yoy").getText();
  }

  public String getAvgSalePricePvPPercent() throws Exception {
    return getobject("avg_sale_price_percent_pvp").getText();
  }

  public String isConversionPresent() throws Exception {
    return getobject("conversion_text").getText();
  }

  public String getConversionValue() throws Exception {
    return getobject("conversion_value").getText();
  }

  public String getConversionYoYPercent() throws Exception {
    return getobject("conversion_percent_yoy").getText();
  }

  public String getConversionPvPPercent() throws Exception {
    return getobject("conversion_percent_pvp").getText();
  }

  public boolean getthegraphelement() throws Exception {
	  loadfile();
	  String xpath=prop.getProperty("margin_graph_value");
	  String []value=xpath.split("##");
      try{
    	  driver.findElement(By.xpath(value[1]));
    	  return true;
      }
      catch(NoSuchElementException e){
          return false;
      }
	  }
  ////////////////////////////
  public void graphtooltipdata(){
	  implicitwait();
//List<WebElement> elements=driver.findElements(By.xpath("//g[@class='c3-event-rects c3-event-rects-single']/rect"));
  WebElement toolTip = driver.findElement(By.xpath("//*[@class=' c3-event-rect c3-event-rect-22']/text()"));
  Actions act=new Actions(driver);
  act.moveToElement(toolTip).build().perform();
  System.out.println("Specify Chart Type Data");
  //for (WebElement el:  elements)
  //{
       String chartValue = toolTip.getText();
       System.out.println("gssssssssssss" + chartValue);
  
  //}
}
  
  /////////////////////////////////////////
  public String isPageViewsPresent() throws Exception {
    return getobject("pageviews_text").getText();
  }

  public String getPageViewsValue() throws Exception {
    return getobject("pageviews_value").getText();
  }

  public String getPageViewsYoYPercent() throws Exception {
    return getobject("pageviews_percent_yoy").getText();
  }

  public String getPageViewsPvPPercent() throws Exception {
    return getobject("pageviews_percent_pvp").getText();
  }
  
  public String isUnitsPerOrder() throws Exception {
    return getobject("units_per_order_text").getText();
  }

  public String getUnitsPerOrderValue() throws Exception {
    return getobject("units_per_order_value").getText();
  }

  public String getUnitsPerOrderYoYPercent() throws Exception {
    return getobject("units_per_order_percent_yoy").getText();
  }

  public String getUnitsPerOrderPvPPercent() throws Exception {
    return getobject("units_per_order_percent_pvp").getText();
  }
  // graph validation
  public void checkgraphlink() throws Exception {
    getobject("margin_graph_link").click();
    Assert.assertEquals("cursorPointer", getobject("margin_graph_navigateback").getAttribute("class"));
  }
  public void checkgraphnaivateback() throws Exception {
    getobject("margin_graph_navigateback").click();
    Assert.assertEquals("dataLayer layer", getobject("margin_graph_link").getAttribute("class"));
  }
  public void PPIwidgetbutton() throws Exception {
	    getobject("PPI_widget_button").click();
	  }
  public String GraphMarginTextPresent() throws Exception {
	    return getobject("margin_text").getText();
	  }
	 
	  public String getGraphMarginValue() throws Exception {
	    return getobject("margin_value").getText();
	  }

	  public String getGraphMarginYoYPercent() throws Exception {
	    return getobject("margin_percent_yoy").getText();
	  }

	  public String getGraphMargingPvPPercent() throws Exception {
	    return getobject("margin_percent_pvp").getText();
	  }

	  public String isGraphRevenueTextPresent() throws Exception {
	    return getobject("revenue_text").getText();
	  }

	  public String getGraphRevenueValue() throws Exception {
	    return getobject("revenue_value").getText();
	  }

	  public String getGraphRevenueYoYPercent() throws Exception {
	    return getobject("revenue_percent_yoy").getText();
	  }

	  public String getGraphRevenuePvPPercent() throws Exception {
	    return getobject("revenue_percent_pvp").getText();
	  }

	  public String isGraphUnitsTextPresent() throws Exception {
	    return getobject("units_text").getText();
	  }

	  public String getGraphUnitsValue() throws Exception {
	    return getobject("units_sold_value").getText();
	  }

	  public String getGraphUnitsYoYPercent() throws Exception {
	    return getobject("units_sold_percent_yoy").getText();
	  }

	  public String getGraphUnitsPvPPercent() throws Exception {
	    return getobject("units_sold_percent_pvp").getText();
	  }

	  public String isGraphAvgSalePriceTextPresent() throws Exception {
	    return getobject("avg_sale_price_text").getText();
	  }

	  public String getGraphAvgSalePriceValue() throws Exception {
	    return getobject("avg_sale_price_value").getText();
	  }

	  public String getGraphAvgSalePriceYoYPercent() throws Exception {
	    return getobject("avg_sale_price_percent_yoy").getText();
	  }

	  public String getGraphAvgSalePricePvPPercent() throws Exception {
	    return getobject("avg_sale_price_percent_pvp").getText();
	  }

	  public String isGraphConversionPresent() throws Exception {
	    return getobject("conversion_text").getText();
	  }

	  public String getGraphConversionValue() throws Exception {
	    return getobject("conversion_value").getText();
	  }

	  public String getGraphConversionYoYPercent() throws Exception {
	    return getobject("conversion_percent_yoy").getText();
	  }

	  public String getGraphConversionPvPPercent() throws Exception {
	    return getobject("conversion_percent_pvp").getText();
	  }

	  public String isGraphPageViewsPresent() throws Exception {
	    return getobject("pageviews_text").getText();
	  }

	  public String getGraphPageViewsValue() throws Exception {
	    return getobject("pageviews_value").getText();
	  }

	  public String getGraphPageViewsYoYPercent() throws Exception {
	    return getobject("pageviews_percent_yoy").getText();
	  }

	  public String getGraphPageViewsPvPPercent() throws Exception {
	    return getobject("pageviews_percent_pvp").getText();
	  }
	  
	  public String isGraphUnitsPerOrder() throws Exception {
	    return getobject("units_per_order_text").getText();
	  }

	  public String getGraphUnitsPerOrderValue() throws Exception {
	    return getobject("units_per_order_value").getText();
	  }

	  public String getGraphUnitsPerOrderYoYPercent() throws Exception {
	    return getobject("units_per_order_percent_yoy").getText();
	  }

	  public String getGraphUnitsPerOrderPvPPercent() throws Exception {
	    return getobject("units_per_order_percent_pvp").getText();
	  }
	  public String PPITextPresent() throws Exception {
		    return getobject("ppi_ppiscore_text").getText();
		  }
		  public String PPIAvgvalue() throws Exception {
			   return getobject("ppi_ppiscore_avgvalue").getText();
			  }
		  public String getavgyoyvalue() throws Exception {
		    return getobject("ppi_ppiscore_avgyoyvalue").getText();
		  }

		  public String getavgpvpvalue() throws Exception {
		    return getobject("ppi_ppiscore_avgpvpvalue").getText();
		  }
		  public List<WebElement> getallcompitatornames() throws Exception {
			    return getobjects("ppi_ppiscore_competitornames");
			  }
		  public List<WebElement> getallcompitatorppivalues() throws Exception {
			    return getobjects("ppi_ppiscore_competitor_ppivalue");
			  }
		  public List<WebElement> getallcompitatoryoyvalues() throws Exception {
			    return getobjects("ppi_ppiscore_competitor_yoyvalue");
			  }
		  public List<WebElement> getallcompitatorpvpvalues() throws Exception {
			    return getobjects("ppi_ppiscore_competitor_pvpvalue");
			  }
		  
		  
		  
		  public void isMarginGraphAddonbuttonforAvgSalePrice() throws Exception {
			    getobject("margin_graph_average_sale_price_button").click();
			  }
		  public boolean validatebuttonisdisabledforAvgSalePrice() throws Exception {
			    boolean b=getobject("margin_graph_average_sale_price_button").isDisplayed();
			    if(b){
			    	return true;
			    }
			    else{
			    	return false;
			    }
			  }
		  public String isMarginGraphAddonAvgSalePriceTextPresent() throws Exception {
			    return getobject("ppi_margin_avg_sale_price_text").getText();
			  }

			  public String getMarginGraphAddonAvgSalePriceValue() throws Exception {
			    return getobject("ppi_margin_avg_sale_price_value").getText();
			  }

			  public String getMarginGraphAddonAvgSalePriceYoYPercent() throws Exception {
			    return getobject("ppi_margin_avg_sale_price_percent_yoy").getText();
			  }

			  public String getMArginGraphAddonAvgSalePricePvPPercent() throws Exception {
			    return getobject("ppi_margin_avg_sale_price_percent_pvp").getText();
			  }
			  
			  public void closingtheaveragesalepricesmallwidget() throws Exception {
				    getobject("ppi_margin_avg_sale_price_close_button").click();
				  }
			  
}
