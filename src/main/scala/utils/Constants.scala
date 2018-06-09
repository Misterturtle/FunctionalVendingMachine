package utils

object Constants {

  val PRICE_OF_CHIPS = 0.5
  val PRICE_OF_COLA = 1.0
  val PRICE_OF_CANDY = 0.65

  val INSERT_COIN = "INSERT COIN"
  val THANK_YOU = "THANK YOU"
  val PRICE_ERROR: (String) => String = (itemAmount:String) => s"PRICE - $itemAmount"


}
