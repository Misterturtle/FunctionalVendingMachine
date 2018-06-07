package events

import domain_models.{CoinHolder, Display}
import utils.MoneyUtils

trait Event

trait CoinHolderEvent extends Event {
  def run(coinHolder: CoinHolder): CoinHolder
}

trait DisplayEvent extends Event {
  def run(display: Display): Display
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

case object DisplayChecked extends DisplayEvent {
  override def run(display: Display): Display = {
    println(display.message)
    display
  }
}



