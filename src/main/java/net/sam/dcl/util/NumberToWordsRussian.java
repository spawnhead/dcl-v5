package net.sam.dcl.util;

import java.math.BigDecimal;

public class NumberToWordsRussian
{
  public final static int DG_POWER = 10;

  private final static String[][] a_power = new String[][]{
          {"0", "", "", ""},  // 1
          {"1", "тысяча ", "тысячи ", "тысяч "},  // 2
          {"0", "миллион ", "миллиона ", "миллионов "},  // 3
          {"0", "миллиард ", "миллиарда ", "миллиардов "},  // 4
          {"0", "триллион ", "триллиона ", "триллионов "},  // 5
          {"0", "квадриллион ", "квадриллиона ", "квадриллионов "},  // 6
          {"0", "квинтиллион ", "квинтиллиона ", "квинтиллионов "},  // 7
          {"0", "секстиллион ", "секстиллиона ", "секстиллионов "},  // 8
          {"0", "септиллион ", "септиллиона ", "септиллионов "},  // 9
          {"0", "октиллион ", "октиллиона ", "октиллионов "},  // 10
  };

  private final static String[][] digit = new String[][]{
          {"", "", "десять ", "", ""},
          {"один ", "одна ", "одиннадцать ", "десять ", "сто "},
          {"два ", "две ", "двенадцать ", "двадцать ", "двести "},
          {"три ", "три ", "тринадцать ", "тридцать ", "триста "},
          {"четыре ", "четыре ", "четырнадцать ", "сорок ", "четыреста "},
          {"пять ", "пять ", "пятнадцать ", "пятьдесят ", "пятьсот "},
          {"шесть ", "шесть ", "шестнадцать ", "шестьдесят ", "шестьсот "},
          {"семь ", "семь ", "семнадцать ", "семьдесят ", "семьсот "},
          {"восемь ", "восемь ", "восемнадцать ", "восемьдесят ", "восемьсот "},
          {"девять ", "девять ", "девятнадцать ", "девяносто ", "девятьсот "}
  };

  public static String toWords(BigDecimal sum)
  {
    BigDecimal TAUSEND = new BigDecimal(1000);
    int i, mny;
    StringBuffer result = new StringBuffer("");
    BigDecimal divisor; // делитель
    BigDecimal psum = sum;

    int one = 1;
    int four = 2;
    int many = 3;

    int hun = 4;
    int dec = 3;
    int dec2 = 2;

    if (sum.equals(BigDecimal.ZERO))
      return "ноль ";
    if (sum.compareTo(BigDecimal.ZERO) < 0)
    {
      result.append("минус ");
      psum = psum.negate();
    }

    for (i = 0, divisor = BigDecimal.ONE; i < DG_POWER - 1; i++)
    {
      divisor = divisor.multiply(TAUSEND);
      if (sum.compareTo(divisor) < 0)
      {
        i++;
        break; // no need to go further
      }
    }
    // start from previous value
    for (; i >= 0; i--)
    {
      mny = psum.divide(divisor).intValue();
      psum = psum.remainder(divisor);
      // str="";
      if (mny == 0)
      {
        // if(i>0) continue;
        if (i == 0)
        {
          result.append(a_power[i][one]);
        }
      }
      else
      {
        if (mny >= 100)
        {
          result.append(digit[mny / 100][hun]);
          mny %= 100;
        }
        if (mny >= 20)
        {
          result.append(digit[mny / 10][dec]);
          mny %= 10;
        }
        if (mny >= 10)
        {
          result.append(digit[mny - 10][dec2]);
        }
        else
        {
          if (mny >= 1)
            result.append(digit[mny]["0".equals(a_power[i][0]) ? 0
                    : 1]);
        }
        switch (mny)
        {
          case 1:
            result.append(a_power[i][one]);
            break;
          case 2:
          case 3:
          case 4:
            result.append(a_power[i][four]);
            break;
          default:
            result.append(a_power[i][many]);
            break;
        }
      }
      divisor = divisor.divide(TAUSEND);
    }
    return result.toString().trim();
  }
}
