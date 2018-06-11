package utils

object Constants {

  val PRICE_OF_CHIPS = 0.5
  val PRICE_OF_COLA = 1.0
  val PRICE_OF_CANDY = 0.65

  val INSERT_COIN = "INSERT COIN"
  val THANK_YOU = "THANK YOU"
  val CANNOT_MAKE_CHANGE = "CANNOT MAKE CHANGE"
  val NICKEL_CURRENCY = "$0.05"
  val DIME_CURRENCY = "$0.10"
  val QUARTER_CURRENCY = "$0.25"
  val PRICE_ERROR: (String) => String = (itemAmount:String) => s"PRICE - $itemAmount"


  val SOLD_OUT = "SOLD OUT"

  val MAX_ITEM_COUNT = 20

  val VALUE_OF_NICKEL = 0.05
  val VALUE_OF_DIME = 0.10
  val VALUE_OF_QUARTER = 0.25

}
