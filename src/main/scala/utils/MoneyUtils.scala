package utils

object MoneyUtils {


  def moneyToBigDec(money: String): BigDecimal = BigDecimal(money.replace("$", ""))

  def addMoneyStrings(moneyValues: List[String]): String = {
    val moneyRegex = """\$(\d+)*\.(\d)(\d)""".r


    "$" +
      moneyValues
        .map {
          case moneyRegex(null, tenth, one) =>
            s".$tenth$one"
          case moneyRegex(dollars, tenth, one) =>
            s"$dollars.$tenth$one"
          case _ =>
            ""
        }
        .filterNot(_ == "")
        .map(BigDecimal(_))
        .sum
        .toString()
  }

}
