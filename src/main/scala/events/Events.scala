package events

import EventSystem.{ErrorEvent, Event, OutputEvent}
import domain_models._
import top_layer.Dependencies.CoinHolderDependency
import utils.{Constants, MoneyUtils}




case object NoItemError extends ErrorEvent {
  override def run(): Unit = println("These are not the items you're looking for.")}

case object NoChangeError extends ErrorEvent{
  override def run(): Unit = println("These are not the coins you're looking for.")
}

trait VendingMachineEvent extends Event

trait CoinReturnEvent extends VendingMachineEvent {
  def run(coinReturn: CoinReturn): CoinReturn
}

trait CoinHolderEvent extends VendingMachineEvent {
  def run(coinHolder: CoinHolder): CoinHolder
}

trait ChangeTrayEvent extends  VendingMachineEvent {
  def run(changeTray: ChangeTray): ChangeTray
}

trait DisplayEvent extends VendingMachineEvent {
  def run(display: Display): Display
}

trait DispenserEvent extends VendingMachineEvent {
  def run(dispenser: Dispenser): Dispenser
}

trait ItemHolderEvent extends VendingMachineEvent {
  def run(itemHolder: ItemHolder): ItemHolder
}


case class CoinInserted(coin:Coin) extends CoinHolderEvent with DisplayEvent {

  override def run(coinHolder: CoinHolder): CoinHolder = {
    coin match {
      case Nickel => coinHolder.copy(nickels = coinHolder.nickels + 1)
      case Dime => coinHolder.copy(dimes = coinHolder.dimes + 1)
      case Quarter => coinHolder.copy(quarters = coinHolder.quarters + 1)
    }
  }

  override def run(display: Display): Display = {
    coin match {
      case Nickel => display.copy(message = MoneyUtils.addMoneyStrings(List(display.message, Constants.NICKEL_CURRENCY)))
      case Dime => display.copy(message = MoneyUtils.addMoneyStrings(List(display.message, Constants.DIME_CURRENCY)))
      case Quarter => display.copy(message = MoneyUtils.addMoneyStrings(List(display.message, Constants.QUARTER_CURRENCY)))
    }
  }
}


case class ItemTaken(item:Item) extends DispenserEvent with OutputEvent {
  override def run(dispenser: Dispenser): Dispenser = dispenser.copy(item = None)

  override def run(): Unit = println(s"You now have ${item.toString}")
}

case class CoinsTaken(amountTaken:BigDecimal) extends ChangeTrayEvent with OutputEvent {
  override def run(changeTray: ChangeTray): ChangeTray = ChangeTray.EMPTY

  override def run(): Unit = println(s"You are $amountTaken richer!")
}

case object ResetDisplay extends DisplayEvent {
  override def run(display: Display): Display = {
    display.copy(message = Constants.INSERT_COIN)
  }
}

case class CoinHolderMoneyReturned(nickels:Int, dimes:Int, quarters:Int) extends ChangeTrayEvent with CoinHolderEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = coinHolder.removeNickels(nickels).removeDimes(dimes).removeQuarters(quarters)
  override def run(changeTray: ChangeTray): ChangeTray = changeTray.addNickels(nickels).addDimes(dimes).addQuarters(quarters)
}

case class CoinReturnMoneyReturned(nickels:Int, dimes:Int, quarters:Int) extends ChangeTrayEvent with CoinReturnEvent {
  override def run(coinReturn: CoinReturn): CoinReturn = coinReturn.removeNickels(nickels).removeDimes(dimes).removeQuarters(quarters)
  override def run(changeTray: ChangeTray): ChangeTray = changeTray.addNickels(nickels).addDimes(dimes).addQuarters(quarters)
}

case object AcceptCoinHolder extends CoinHolderEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = coinHolder.addCoinsToCoinReturn()
}

case class CannotMakeChange(returnedNickels:Int, returnedDimes:Int, returnedQuarters:Int) extends CoinHolderEvent with ChangeTrayEvent with DisplayEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = {
    coinHolder.copy(nickels = 0, dimes = 0, quarters = 0)
  }

  override def run(changeTray: ChangeTray): ChangeTray = {
    changeTray.copy(nickels = changeTray.nickels + returnedNickels, dimes = changeTray.dimes + returnedDimes, changeTray.quarters + returnedQuarters)
  }

  override def run(display: Display): Display = {
    display.copy(message = Constants.CANNOT_MAKE_CHANGE)
  }
}

case class OutOfStock(item: Item) extends DisplayEvent {
  override def run(display: Display): Display = {
    display.copy(message = Constants.SOLD_OUT)
  }
}

case class ItemSelected(item: Item) extends DisplayEvent with DispenserEvent with ItemHolderEvent {
  override def run(display: Display): Display = {
    display.copy(message = Constants.THANK_YOU)
  }

  override def run(dispenser: Dispenser): Dispenser = dispenser.copy(item = Some(item))

  override def run(itemHolder: ItemHolder): ItemHolder = {
    item match {
      case Chips => itemHolder.copy(chips = itemHolder.chips - 1)
      case Cola => itemHolder.copy(colas = itemHolder.colas - 1)
      case Candy => itemHolder.copy(candies = itemHolder.candies - 1)
    }
  }
}

case class InvalidAmount(item: Item) extends DisplayEvent {
  override def run(display: Display): Display = {
    display.copy(message = "PRICE - " + MoneyUtils.currencyOf(item.price))
  }
}

case class DispenserChecked(maybeItem:Option[Item]) extends OutputEvent {
  override def run(): Unit = {
    maybeItem match {
      case Some(item) => println(s"You're mouth waters as you stare at your $item for no reason.")
      case None => println("These are not the items you're looking for.")
    }
  }
}

case class CoinReturnChecked(coinReturn:CoinReturn) extends OutputEvent {
  override def run(): Unit = {
    if(coinReturn.amount == 0){
      println("Are you really checking if someone else left change? Lew.")}
    else {
      println(s"What are you wait for? Take your ${coinReturn.nickels} Nickels, ${coinReturn.dimes} Dimes, and ${coinReturn.quarters} Quarters Already!!")
    }
  }
}

case class DisplayChecked(coinHolderAmount: BigDecimal) extends DisplayEvent {
  override def run(display: Display): Display = {
    println(display.message)

    val invalidPriceError = """PRICE \- .*""".r
    display.message match {
      case changeError if changeError == Constants.CANNOT_MAKE_CHANGE => display.copy(message = Constants.INSERT_COIN)
      case message if message == Constants.THANK_YOU => display.copy(message = Constants.INSERT_COIN)
      case soldOutError if soldOutError == Constants.SOLD_OUT && coinHolderAmount == 0 => display.copy(message = Constants.INSERT_COIN)
      case soldOutError if soldOutError == Constants.SOLD_OUT && coinHolderAmount > 0 => display.copy(MoneyUtils.currencyOf(coinHolderAmount))
      case invalidPriceError() if coinHolderAmount == 0 => display.copy(message = Constants.INSERT_COIN)
      case invalidPriceError() => display.copy(message = MoneyUtils.currencyOf(coinHolderAmount))
      case _ => display
    }
  }
}


