package domain_models

import utils.Constants

case object CoinHolder {
  val EMPTY = new CoinHolder(0, 0, 0, CoinReturn.EMPTY)
  val FIVE_EACH = new CoinHolder(0, 0, 0, CoinReturn.FIVE_EACH)

  def apply(nickels:Int, dimes:Int, quarters:Int):CoinHolder = new CoinHolder(nickels, dimes, quarters, CoinReturn.EMPTY)
}

case class CoinHolder(nickels: Int, dimes: Int, quarters: Int, coinReturn: CoinReturn) {
  def addCoinsToCoinReturn(): CoinHolder = copy(coinReturn = coinReturn.copy(nickels = coinReturn.nickels + nickels, dimes = coinReturn.dimes + dimes, quarters = coinReturn.quarters + quarters))


  def makeChange(item: Item): Option[(Int, Int, Int)] = {
    val price = item.price

    var _quarters = quarters
    var _dimes = dimes
    var _nickels = nickels

    def coinHolderAmount(): BigDecimal = (_quarters * Constants.VALUE_OF_QUARTER) + (_dimes * Constants.VALUE_OF_DIME) + (_nickels * Constants.VALUE_OF_NICKEL)

    var _addedQuarters = 0
    var _addedDimes = 0
    var _addedNickels = 0

    var outOfOptions = false

    def amountNeeded = coinHolderAmount - price


    while (coinHolderAmount() != price && !outOfOptions) {
      if (_quarters > 0 && amountNeeded >= 0.25) {
        _quarters -= 1
        _addedQuarters += 1
      } else if (_dimes > 0 && amountNeeded >= 0.10) {
        _dimes -= 1
        _addedDimes += 1
      } else if (_nickels > 0 && amountNeeded >= 0.05) {
        _nickels -= 1
        _addedNickels += 1
      } else {
        outOfOptions = true
      }
    }

    if (outOfOptions) {
      None
    } else {
      Some((_addedNickels, _addedDimes, _addedQuarters))
    }
  }
  def amount = (nickels * 0.05) + (dimes * 0.10) + (quarters * 0.25)

  def removeNickels(amount: Int) = copy(nickels = nickels - amount)

  def removeDimes(amount: Int) = copy(dimes = dimes - amount)

  def removeQuarters(amount: Int) = copy(quarters = quarters - amount)

  def addNickels(amount: Int) = copy(nickels = nickels + amount)

  def addDimes(amount: Int) = copy(dimes = dimes + amount)

  def addQuarters(amount: Int) = copy(quarters = quarters + amount)
}
