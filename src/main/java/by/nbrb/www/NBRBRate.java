package by.nbrb.www;

public class NBRBRate
{
  static final public String dailyExRatesTagName = "DailyExRatesOnDate";

  static final public String nameTagName = "Cur_QuotName";
  static final public String scaleTagName = "Cur_Scale";
  static final public String rateTagName = "Cur_OfficialRate";
  static final public String numberCodeTagName = "Cur_Code";
  static final public String codeTagName = "Cur_Abbreviation";  

  String name;
  String scale;
  double rate;
  String numberCode;
  String code;

  public NBRBRate()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getScale()
  {
    return scale;
  }

  public void setScale(String scale)
  {
    this.scale = scale;
  }

  public double getRate()
  {
    return rate;
  }

  public void setRate(double rate)
  {
    this.rate = rate;
  }

  public String getNumberCode()
  {
    return numberCode;
  }

  public void setNumberCode(String numberCode)
  {
    this.numberCode = numberCode;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }
}
