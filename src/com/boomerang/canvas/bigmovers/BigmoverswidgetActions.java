package com.boomerang.canvas.bigmovers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.boomerang.canvas.testbase.Testbase;

public class BigmoverswidgetActions extends Testbase{
	
	public String getBigmoversText() throws Exception {
		 getBigmoversNavigation();
	    return getobject("bigmovers_text").getText();
	  }

	  public String getBigmoversAllText() throws Exception {
		  getBigmoversNavigation();
	    return getobject("bigmovers_all").getText();
	  }
	  public boolean getBigmoverdatanotavailable() throws Exception {
		  getBigmoversNavigation();
		  String xpath=prop.getProperty("bigmovers_not_availabledata");
		  String []value=xpath.split("##");
	      try{
	    	  driver.findElement(By.xpath(value[1]));
	    	  return true;
	      }
	      catch(NoSuchElementException e){
	          return false;
	      }
	  }
	  public String getBigmoversWinnnersText() throws Exception {
		  getBigmoversNavigation();
	    return getobject("bigmovers_winners").getText();
	  }
	  public String getBigmoversLoosersText() throws Exception {
		  getBigmoversNavigation();
		    return getobject("bigmovers_loosers").getText();
		  }
	  public String getBigmoversCategoriesText() throws Exception {
		  getBigmoversNavigation();
		    return getobject("bigmovers_categories").getText();
		  }
	  public String getBigmoversBrandsText() throws Exception {
		  getBigmoversNavigation();
		    return getobject("bigmovers_brands").getText();
		  }
	  public void getBigmoversNavigation() throws Exception {
		     getobject("bigmovers_navigation").click();
		  }
	  public void BigmoversCategoriesButton() throws Exception {
	  getBigmoversNavigation();
	     boolean b=getobject("bigmovers_categories_list").isEnabled();
	     if(b){
	     	getobject("bigmovers_categories_list").click();
	     	Thread.sleep(2000);
	     }
	  }
public void BigmoversBrandsButton() throws Exception {
	 getBigmoversNavigation();
	     boolean b= getobject("bigmovers_brands_list").isEnabled();
	     if(b){
	     	getobject("bigmovers_brands_list").click();
	     	Thread.sleep(2000);
	     }
	  }
public void BigmoversAllButton() throws Exception {
	 getBigmoversNavigation();
    boolean b=getobject("bigmovers_all_list").isEnabled();
    if(b){
    	getobject("bigmovers_all_list").click();
    	Thread.sleep(2000);
    }
 }
  public boolean getBigmoversLossersEmptyitemText() throws Exception{
	  getBigmoversNavigation();
	  loadfile();
	  String xpath=prop.getProperty("bigmovers_loosers_empty_list");
	  String []value=xpath.split("##");
	        try{
	        	driver.findElement(By.xpath(value[1]));
	            return true;
	        }
	        catch(NoSuchElementException e){
	            return false;
	        }
	    }
  public boolean getBigmoversWinnersEmptyitemText() throws Exception{
	  getBigmoversNavigation();
	  loadfile();
	  String xpath=prop.getProperty("bigmovers_winners_empty_list");
	  String []value=xpath.split("##");
      try{
    	  driver.findElement(By.xpath(value[1]));
    	  return true;
      }
      catch(NoSuchElementException e){
          return false;
      }
  }
  public List<WebElement> getBigmoversLoserslist() throws Exception {
	  getBigmoversNavigation();
	     List<WebElement> value2 =getobjects("bigmovers_loosers_list");
	     return value2;
  }
  public List<WebElement> getBigmoversLosersyoylist() throws Exception {
	  getBigmoversNavigation();
	     List<WebElement> list=getobjects("bigmovers_loosers_yoy_list");
	     return list;
  }
  public List<WebElement> getBigmoversLoserspvplist() throws Exception {
	  getBigmoversNavigation();
	     List<WebElement> list=getobjects("bigmovers_loosers_pvp_list");
	     return list;
  }
  public List<WebElement> getBigmoversWinnerslist() throws Exception {
	  getBigmoversNavigation();
	     List<WebElement> list=getobjects("bigmovers_winners_list");
	     return list;
}
public List<WebElement> getBigmoversWinnersyoylist() throws Exception {
	 getBigmoversNavigation();
	     List<WebElement> list=getobjects("bigmovers_winners_yoy_list");
	     return list;
}
public List<WebElement> getBigmoversWinnerspvplist() throws Exception {
	 getBigmoversNavigation();
	     List<WebElement> list=getobjects("bigmovers_winners_pvp_list");
	     return list;
}

public String getBigmoversTrianglegraphNormaltext() throws Exception {
	 getBigmoversNavigation();
	    return getobject("bigmovers_normal_description").getText();
}

public String getBigmoversTrianglegraphStrongtext() throws Exception {
	 getBigmoversNavigation();
	    return getobject("bigmovers_strong_description").getText();
}

public String getBigmoversTrianglegraphWeaktext() throws Exception {
	 getBigmoversNavigation();
	    return getobject("bigmovers_weak_description").getText();
}
public String getBigmoversTrianglegraphNormalValue() throws Exception {
	 getBigmoversNavigation();
	    return getobject("bigmovers_normal_value").getText();
}

public String getBigmoversTrianglegraphStrongValue() throws Exception {
	 getBigmoversNavigation();
	    return getobject("bigmovers_strong_value").getText();
}

public String getBigmoversTrianglegraphWeakValue() throws Exception {
	 getBigmoversNavigation();
	    return getobject("bigmovers_weak_value").getText();
}
}
