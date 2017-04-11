package com.boomerang.canvas.scoreboard;

import com.boomerang.canvas.testbase.Testbase;

public class ScoreboardWidgetActions extends Testbase {


  public String MarginTextPresent() throws Exception {
    return getobject("margin_text").getText();
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

}
