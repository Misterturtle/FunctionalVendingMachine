package top_layer

import domain_models.{CoinHolder, Dispenser, Display, VendingMachine}
import events._

import scala.collection.mutable.ListBuffer

object SideEffectGraph {

  private val _history = ListBuffer[String]()

  def history = _history

  // Actual Mutation of the game
  // Only one event gets fired during this phase
  // The result of this is passed back into the dependency graph before the next event.


  def updateVendingMachine(origVendMach: VendingMachine, vendingMachineEvent: VendingMachineEvent): VendingMachine = {
    vendingMachineEvent match {
      case coinHolderEvent: CoinHolderEvent => origVendMach.copy(coinHolder = updateCoinHolder(origVendMach.coinHolder, coinHolderEvent)
      case displayEvent: DisplayEvent => origVendMach.copy(display = updateDisplay(origVendMach.display, displayEvent)
      case dispenserEvent: DispenserEvent => origVendMach.copy(dispenser = updateDispenser(origVendMach.dispenser, dispenserEvent)
    }
  }

  def updateDispenser(dispenser: Dispenser, dispenserEvent: DispenserEvent): Dispenser = dispenserEvent.run(dispenser)

  def updateCoinHolder(origCoinHolder: CoinHolder, sideEffect: CoinHolderEvent): CoinHolder = sideEffect.run(origCoinHolder)

  def updateDisplay(display: Display, event: DisplayEvent): Display = event.run(display)

  def update[A](currentState: A, story: Story[A]): A = {
    story.eventStack.foldLeft(currentState) { case (newState, eventFn) =>

      //Mutable local variable allows us to continue modifications as the events are processed.
      //Area of much question due to the order of execution between events.
      val injectionGraph = newState match {
        case vendMach: VendingMachine => updateVendingMachine(vendMach, eventFn.asInstanceOf[VendingMachine => VendingMachineEvent](vendMach))
      }

      injectionGraph.asInstanceOf[A]
    }
  }
}