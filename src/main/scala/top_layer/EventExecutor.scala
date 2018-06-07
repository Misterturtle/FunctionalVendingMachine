package top_layer

import domain_models.{CoinHolder, Display, VendingMachine}
import events.{CoinHolderEvent, DisplayEvent, Event}

object EventExecutor {

  def updateDisplay(machine: VendingMachine, display: Display): VendingMachine = machine.copy(display = display)

  def updateCoinHolder(vendingMachine: VendingMachine, coinHolder: CoinHolder): VendingMachine = vendingMachine.copy(coinHolder = coinHolder)

  def update(origMachine: VendingMachine, events: List[Event]): VendingMachine = {
    events.reverse.foldLeft(origMachine) { case (vm,e) =>

      var mMachine = vm

      if(e.isInstanceOf[CoinHolderEvent]){
        mMachine = updateCoinHolder(mMachine, e.asInstanceOf[CoinHolderEvent].run(vm.coinHolder))
      }

      if(e.isInstanceOf[DisplayEvent]){
        mMachine = updateDisplay(mMachine, e.asInstanceOf[DisplayEvent].run(vm.display))
      }

      mMachine
    }
  }
}