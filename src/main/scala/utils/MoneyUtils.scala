package utils

import java.text.NumberFormat

object MoneyUtils {

  def moneyToBigDec(money: String): BigDecimal = {
    BigDecimal(NumberFormat.getCurrencyInstance.parse(money).byteValue())
//    BigDecimal(money.replace("$", ""))
  }

  def addMoneyStrings(moneyValues: List[String]): String = {
    val moneyRegex = """\$(-?\d*\.\d\d$)""".r

    "$" +
      moneyValues
        .flatMap {
          case moneyRegex(amount) => Some(BigDecimal(amount))
          case _ => None
        }
        .sum
        .toString
  }


  def currencyOf(amount: BigDecimal): String = {
    NumberFormat.getCurrencyInstance().format(amount)
  }

}
