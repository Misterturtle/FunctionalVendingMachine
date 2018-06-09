package domain_models

import events._
import utils.Constants


case object VendingMachine{

  val insertNickel : VendingMachine => Event = (vendingMachine: VendingMachine) => NickelInserted

  val insertDime: VendingMachine => Event = { vendingMachine =>
    DimeInserted
  }

  val insertQuarter: VendingMachine => Event = { vendingMachine =>
    QuarterInserted
  }

  val selectChips: VendingMachine => List[Event] = { vendingMachine =>
    val coinHolderAmount = vendingMachine.coinHolder.amount
    coinHolderAmount match {
      case _ if coinHolderAmount > Constants.PRICE_OF_CHIPS => List(ChipsSelected, MoneyReturned(coinHolderAmount))
      case _ if coinHolderAmount >= Constants.PRICE_OF_CHIPS => List(ChipsSelected)
      case _ if coinHolderAmount < Constants.PRICE_OF_CHIPS => List(InvalidAmount(Chips))
    }
  }

  val selectCola: VendingMachine => Event = {vendingMachine =>
    val coinHolder = vendingMachine.coinHolder.amount
    coinHolder match {
      case _ if coinHolder >= Constants.PRICE_OF_COLA => ColaSelected
      case _ if coinHolder < Constants.PRICE_OF_COLA => InvalidAmount(Cola)
    }
  }

  val selectCandy: VendingMachine => Event = { vendingMachine =>
    val coinHolder = vendingMachine.coinHolder.amount
    coinHolder match {
      case _ if coinHolder >= Constants.PRICE_OF_CANDY => CandySelected
      case _ if coinHolder < Constants.PRICE_OF_COLA => InvalidAmount(Candy)
    }
  }

  val checkDisplay: VendingMachine => Event = { vendingMachine =>
    DisplayChecked(vendingMachine.coinHolder.amount)
  }

  def apply(): VendingMachine = VendingMachine(CoinHolder(0), Display(Constants.INSERT_COIN), Dispenser(None), CoinReturn(0))
}

case class VendingMachine(coinHolder: CoinHolder, display: Display, dispenser:Dispenser, coinReturn:CoinReturn)

