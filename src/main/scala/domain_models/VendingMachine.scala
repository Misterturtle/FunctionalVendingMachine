package domain_models

import events._


case object VendingMachine{
  private val STARTING_DISPLAY = "INSERT COIN"

  def insertNickel(vendingMachine: VendingMachine): Event = NickelInserted

  def insertDime(vendingMachine: VendingMachine): Event = DimeInserted

  def insertQuarter(vendingMachine: VendingMachine): Event = QuarterInserted

  def checkDisplay(vendingMachine: VendingMachine): Event = DisplayChecked

  def apply(): VendingMachine = VendingMachine(CoinHolder(0), Display(STARTING_DISPLAY))
}

case class VendingMachine(coinHolder: CoinHolder, display: Display)

