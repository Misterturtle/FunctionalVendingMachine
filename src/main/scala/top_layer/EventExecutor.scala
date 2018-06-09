package top_layer

import domain_models.{CoinHolder, Dispenser, Display, VendingMachine}
import events._

import scala.collection.mutable.ListBuffer

object EventExecutor {

  private val _history = ListBuffer[String]()
  def history = _history

  def updateDisplay(machine: VendingMachine, display: Display): VendingMachine = machine.copy(display = display)

  def updateCoinHolder(vendingMachine: VendingMachine, coinHolder: CoinHolder): VendingMachine = vendingMachine.copy(coinHolder = coinHolder)

  def updateDispenser(vendingMachine: VendingMachine, dispenser:Dispenser) : VendingMachine = vendingMachine.copy(dispenser = dispenser)

  def updateVendingMachine(origVendMach: VendingMachine, eventFn: List[VendingMachine => Event]) : VendingMachine = {

    var mMachine = origVendMach

    val events = eventFn(mMachine)

    events.foreach{

    }

    _history.prepend(event.name.replace("class events.", ""))

    if(event.isInstanceOf[CoinHolderEvent]){
      mMachine = updateCoinHolder(mMachine, event.asInstanceOf[CoinHolderEvent].run(mMachine.coinHolder))
    }

    if(event.isInstanceOf[DisplayEvent]){
      mMachine = updateDisplay(mMachine, event.asInstanceOf[DisplayEvent].run(mMachine.display))
    }

    if(event.isInstanceOf[DispenserEvent]){
      mMachine = updateDispenser(mMachine, event.asInstanceOf[DispenserEvent].run(mMachine.dispenser))
    }

    mMachine
  }

  def update[A](currentState: A, story:Story[A]): A = {
    story.eventStack.foldLeft(currentState){ case (newState, eventFn) =>

      //Mutable local variable allows us to continue modifications as the events are processed.

      //Area of much question due to the order of execution between events.

      newState match {
        case vendMach:VendingMachine => updateVendingMachine(vendMach, eventFn.asInstanceOf[VendingMachine => List[Event]]).asInstanceOf[A]
      }
    }
  }
}