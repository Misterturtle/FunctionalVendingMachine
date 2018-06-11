package top_layer

import domain_models._
import events._
import top_layer.Dependencies.TopObject

object SideEffectGraph {

  // Actual Mutation of the game
  // Only one event gets fired during this phase
  // The result of this is passed back into the dependency graph before the next event.

  def updateTopObject(currentState: TopObject, event: Event): TopObject = {
    def runError(error:ErrorEvent) : TopObject = {
      error.run()
      currentState
    }

    def runOutput(outputEvent: OutputEvent) :TopObject = {
      outputEvent.run()
      currentState
    }

    if(event.isInstanceOf[SideEffect]){
      event.asInstanceOf[SideEffect].run()
    }

    if(event.isInstanceOf[ErrorEvent]){
      event.asInstanceOf[ErrorEvent].run()
    }

    if(event.isInstanceOf[VendingMachineEvent]){
      updateVendingMachine(currentState.asInstanceOf[VendingMachine], event.asInstanceOf[VendingMachineEvent])
    } else {
      currentState
    }
  }

  def updateVendingMachine(origVendMach: VendingMachine, vendingMachineEvent: VendingMachineEvent): VendingMachine = {

    var mMachine = origVendMach

    if(vendingMachineEvent.isInstanceOf[CoinHolderEvent]){
      mMachine = mMachine.copy(coinHolder = updateCoinHolder(origVendMach.coinHolder, vendingMachineEvent.asInstanceOf[CoinHolderEvent]))
    }

    if(vendingMachineEvent.isInstanceOf[DisplayEvent]){
      mMachine = mMachine.copy(display = updateDisplay(origVendMach.display, vendingMachineEvent.asInstanceOf[DisplayEvent]))
    }

    if(vendingMachineEvent.isInstanceOf[DispenserEvent]){
      mMachine = mMachine.copy(dispenser = updateDispenser(mMachine.dispenser, vendingMachineEvent.asInstanceOf[DispenserEvent]))
    }

    if(vendingMachineEvent.isInstanceOf[CoinReturnEvent]){
      mMachine = mMachine.copy(coinHolder =
        mMachine.coinHolder.copy(coinReturn =
          updateCoinReturn(mMachine.coinHolder.coinReturn, vendingMachineEvent.asInstanceOf[CoinReturnEvent])))
    }

    if(vendingMachineEvent.isInstanceOf[ItemHolderEvent]){
      mMachine = mMachine.copy(itemHolder = updateItemHolder(mMachine.itemHolder, vendingMachineEvent.asInstanceOf[ItemHolderEvent]))
    }

    if(vendingMachineEvent.isInstanceOf[ChangeTrayEvent]){
      mMachine = mMachine.copy(changeTray = updateChangeTray(mMachine.changeTray, vendingMachineEvent.asInstanceOf[ChangeTrayEvent]))
    }

    if(vendingMachineEvent.isInstanceOf[ErrorEvent]){
      vendingMachineEvent.asInstanceOf[ErrorEvent].run()
    }

    mMachine
  }

  def updateDispenser(dispenser: Dispenser, dispenserEvent: DispenserEvent): Dispenser = dispenserEvent.run(dispenser)

  def updateCoinHolder(origCoinHolder: CoinHolder, sideEffect: CoinHolderEvent): CoinHolder = sideEffect.run(origCoinHolder)

  def updateChangeTray(changeTray: ChangeTray, event: ChangeTrayEvent): ChangeTray = event.run(changeTray)

  def updateDisplay(display: Display, event: DisplayEvent): Display = event.run(display)

  def updateCoinReturn(coinReturn: CoinReturn, event: CoinReturnEvent) : CoinReturn = event.run(coinReturn)

  def updateItemHolder(itemHolder: ItemHolder, event: ItemHolderEvent) : ItemHolder = event.run(itemHolder)

}