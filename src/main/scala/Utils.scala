object MoneyUtils {


  def moneyToBigDec(money:String): BigDecimal = BigDecimal(money.replace("$", ""))

  def addMoneyStrings(moneyValues:List[String]): String = {

    "$" +
    moneyValues
      .filterNot(_ == "")
      .map(_.replace("$", ""))
      .map(BigDecimal(_))
      .sum
      .toString()
  }

}
