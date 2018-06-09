package events

import domain_models._
import utils.{Constants, MoneyUtils}

trait Event{
  val name: String = this.getClass.toString
}

case object NoEventNeeded extends Event

trait CoinReturnEvent extends Event {
  def run(coinReturn: CoinReturn): CoinReturn
}

trait CoinHolderEvent extends Event {
  def run(coinHolder: CoinHolder): CoinHolder
}

trait DisplayEvent extends Event {
  def run(display: Display): Display
}

trait DispenserEvent extends Event{
  def run(dispenser:Dispenser) : Dispenser
}


case object NickelInserted extends CoinHolderEvent with DisplayEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = coinHolder.copy(coinHolder.amount + .05)

  override def run(display: Display): Display = {
    val newMessage = MoneyUtils.addMoneyStrings(List(display.message, "$.05"))
    display.copy(message = newMessage)
  }
}

case object DimeInserted extends CoinHolderEvent with DisplayEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = coinHolder.copy(coinHolder.amount + .1)

  override def run(display: Display): Display = {
    val newMessage = MoneyUtils.addMoneyStrings(List(display.message, "$.10"))
    display.copy(message = newMessage)
  }
}

case object QuarterInserted extends CoinHolderEvent with DisplayEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = coinHolder.copy(coinHolder.amount + .25)

  override def run(display: Display): Display = {
    val newMessage = MoneyUtils.addMoneyStrings(List(display.message, "$.25"))
    display.copy(message = newMessage)
  }
}

case class MoneyReturned(amountReturned:BigDecimal) extends CoinReturnEvent with CoinHolderEvent {
  override def run(coinReturn: CoinReturn): CoinReturn = {
    coinReturn.copy(amount = coinReturn.amount + amountReturned)
  }

  override def run(coinHolder: CoinHolder): CoinHolder = {
    coinHolder.copy(amount = coinHolder.amount - amountReturned)
  }
}

case object ChipsSelected extends DisplayEvent with DispenserEvent {
  override def run(display: Display): Display = {
    display.copy(message = "THANK YOU")
  }

  override def run(dispenser: Dispenser): Dispenser = {
    dispenser.copy(item = Some(Chips))
  }
}

case object ColaSelected extends DispenserEvent {
  override def run(dispenser: Dispenser): Dispenser = dispenser.copy(item = Some(Cola))
}

case object CandySelected extends DispenserEvent {
  override def run(dispenser: Dispenser): Dispenser = dispenser.copy(item = Some(Candy))
}

case class InvalidAmount(item:Item) extends DisplayEvent {
  override def run(display: Display): Display = {
    display.copy(message = "PRICE - " + MoneyUtils.currencyOf(item.price))
  }
}

case class DisplayChecked(coinHolderAmount: BigDecimal) extends DisplayEvent {
  override def run(display: Display): Display = {
    println(display.message)

    val invalidPriceError = """PRICE \- .*""".r
    display.message match {
      case message if message == Constants.THANK_YOU  => display.copy("INSERT COIN")
      case invalidPriceError() if coinHolderAmount == 0 => display.copy(message = Constants.INSERT_COIN)
      case invalidPriceError() => display.copy(message = MoneyUtils.currencyOf(coinHolderAmount))
      case _ => display
    }
  }
}


