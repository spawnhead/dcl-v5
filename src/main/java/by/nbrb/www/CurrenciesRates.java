package by.nbrb.www;

import net.sam.dcl.log.Log;
import org.apache.axis.message.MessageElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesRates
{
  static private String currentDate = "";
  static private List<NBRBRate> rateCurrencies = new ArrayList<NBRBRate>();

  private CurrenciesRates()
  {
  }

  static public double getRateForCurrency(String date, String code) throws Exception
  {
    exRatesDaily(date);

    for (NBRBRate rateNBRB : rateCurrencies)
    {
      if ( rateNBRB.getCode().equals(code) )
      {
        return rateNBRB.getRate();
      }
    }

    return 0.0;
  }

  //берет из текущего списка, если список пуст - Exception - нельзя так применять
  static public double getRateForCurrency(String code) throws Exception
  {
    if ( rateCurrencies.size() == 0 )
    {
      throw new Exception("Сначала получите список курсов из банка за дату");
    }

    for (NBRBRate rateNBRB : rateCurrencies)
    {
      if ( rateNBRB.getCode().equals(code) )
      {
        return rateNBRB.getRate();
      }
    }

    return 0.0;
  }

  static public List<NBRBRate> getRatesOnDate(String date) throws Exception
  {
    exRatesDaily(date);

    return rateCurrencies;
  }

  static private void exRatesDaily(String date) throws Exception
  {
    if ( currentDate.equals(date) && rateCurrencies.size() != 0 )
    {
      return;
    }

    currentDate = date;
    rateCurrencies.clear();
    try
    {
      ExRatesLocator locator = new ExRatesLocator();
      ExRatesDailyResponseExRatesDailyResult result = locator.getExRatesSoap().exRatesDaily(date);
      if ( null == result )
      {
        throw new RatesException("Нет данных на заданную дату.");
      }
      MessageElement[] listCurrenciesElement = result.get_any(); //get the result
      /*start parsing*/
      for (MessageElement m : listCurrenciesElement)
      {
        Document document = m.getAsDocument();
        NodeList ratesOnDateList = document.getElementsByTagName(NBRBRate.dailyExRatesTagName);
        for(int i = 0; i < ratesOnDateList.getLength(); i++)
        {
          Node currencyNode = ratesOnDateList.item(i);
          NodeList currencyFields = currencyNode.getChildNodes();
          if ( currencyFields.getLength() <= 4 )
          {
            throw new Exception("Сервис вернул некорректное количество полей в строке. Скорее всего некорректна дата в запросе.");
          }

          NBRBRate nbrbRate = new NBRBRate();
          for(int j = 0; j < currencyFields.getLength(); j++)
          {
            Node currencyNodeField = currencyFields.item(j);
            if ( NBRBRate.nameTagName.equals(currencyNodeField.getLocalName()) )
            {
              nbrbRate.setName(currencyNodeField.getTextContent());
            }
            if ( NBRBRate.scaleTagName.equals(currencyNodeField.getLocalName()) )
            {
              nbrbRate.setScale(currencyNodeField.getTextContent());
            }
            if ( NBRBRate.rateTagName.equals(currencyNodeField.getLocalName()) )
            {
              nbrbRate.setRate(new Double(currencyNodeField.getTextContent()).doubleValue());
            }
            if ( NBRBRate.numberCodeTagName.equals(currencyNodeField.getLocalName()) )
            {
              nbrbRate.setNumberCode(currencyNodeField.getTextContent());
            }
            if ( NBRBRate.codeTagName.equals(currencyNodeField.getLocalName()) )
            {
              nbrbRate.setCode(currencyNodeField.getTextContent());
            }
          }

          rateCurrencies.add(nbrbRate);
        }
      }
    }
    catch (Exception e)
    {
      Log.info(e.getMessage());
      throw e;
    }
  }
}
