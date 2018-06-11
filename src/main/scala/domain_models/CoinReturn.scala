package domain_models

import utils.Constants

case object CoinReturn{
  val EMPTY = new CoinReturn(0,0,0)
  val FIVE_EACH = new CoinReturn(5,5,5)
}
case class CoinReturn(nickels:Int, dimes:Int, quarters:Int){
  def makeChange(amountToReturn:BigDecimal): Option[(Int, Int, Int)] = {
    var _quarters = quarters
    var _dimes = dimes
    var _nickels = nickels

    var _returnedNickels = 0
    var _returnedDimes = 0
    var _returnedQuarters = 0

    def coinHolderAmount(): BigDecimal = (_quarters * Constants.VALUE_OF_QUARTER) + (_dimes * Constants.VALUE_OF_DIME) + (_nickels * Constants.VALUE_OF_NICKEL)

    var outOfOptions = false
    var amountNeeded = amountToReturn


    while (amountNeeded != 0 && !outOfOptions) {
      if (_quarters > 0 && amountNeeded >= 0.25) {
        _quarters -= 1
        _returnedQuarters += 1
        amountNeeded -= Constants.VALUE_OF_QUARTER
      } else if (_dimes > 0 && amountNeeded >= 0.10) {
        _dimes -= 1
        _returnedDimes += 1
        amountNeeded -= Constants.VALUE_OF_DIME
      } else if (_nickels > 0 && amountNeeded >= 0.05) {
        _nickels -= 1
        _returnedNickels += 1
        amountNeeded -= Constants.VALUE_OF_NICKEL
      } else {
        outOfOptions = true
      }
    }

    if (outOfOptions) {
      None
    } else {
      Some((_returnedNickels, _returnedDimes, _returnedQuarters))
    }
  }

  def amount = (nickels * 0.05) + (dimes * 0.10) + (quarters * 0.25)
  def removeNickels(amount:Int) = copy(nickels = nickels - amount)
  def removeDimes(amount:Int) = copy(dimes = dimes - amount)
  def removeQuarters(amount:Int) = copy(quarters = quarters - amount)
  def addNickels(amount:Int) = copy(nickels = nickels + amount)
  def addDimes(amount:Int) = copy(dimes = dimes + amount)
  def addQuarters(amount:Int) = copy(quarters = quarters + amount)
}
