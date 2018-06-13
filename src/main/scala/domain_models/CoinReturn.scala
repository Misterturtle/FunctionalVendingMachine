package domain_models

import utils.Constants

case object CoinReturn {
  val EMPTY = new CoinReturn(0, 0, 0)
  val FIVE_EACH = new CoinReturn(5, 5, 5)
}

case class CoinReturn(nickels: Int, dimes: Int, quarters: Int) {
  def makeChange(amountNeeded: BigDecimal): Option[(Int, Int, Int)] = {

    def go(remaining: BigDecimal, coins: (Int, Int, Int) = (0, 0, 0)): (Int, Int, Int) = {
      import Constants._

      val remainingCoins = (this.nickels - coins._1, this.dimes - coins._2, this.quarters - coins._3)
      val quarterIsValid = remaining >= VALUE_OF_QUARTER && remainingCoins._3 != 0
      val dimeIsValid = remaining >= VALUE_OF_DIME && remainingCoins._2 != 0
      val nickelIsValid = remaining >= VALUE_OF_NICKEL && remainingCoins._1 != 0
      val cannotGiveNickel = remaining == 5 && remainingCoins._1 == 0
      val isDone = remaining == 0 || cannotGiveNickel

      remaining match {
        case _ if isDone => (coins._1, coins._2, coins._3)
        case _ if quarterIsValid => go(remaining - VALUE_OF_QUARTER, (coins._1, coins._2, coins._3 + 1))
        case _ if dimeIsValid => go(remaining - VALUE_OF_DIME, (coins._1, coins._2 + 1, coins._3))
        case _ if nickelIsValid => go(remaining - VALUE_OF_NICKEL, (coins._1 + 1, coins._2, coins._3))
      }
    }

    val (nickels, dimes, quarters) = go(amountNeeded)

    if ((nickels, dimes, quarters) == (0, 0, 0)) {
      None
    } else {
      Some((nickels, dimes, quarters))
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
