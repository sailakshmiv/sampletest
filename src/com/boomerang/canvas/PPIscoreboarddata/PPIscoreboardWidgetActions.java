package com.boomerang.canvas.PPIscoreboarddata;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.boomerang.canvas.testbase.Testbase;

public class PPIscoreboardWidgetActions extends Testbase {


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
}