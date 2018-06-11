package domain_models

import Input.TakeItem
import events._
import top_layer.Dependencies.TopObject
import utils.Constants


case object VendingMachine {
  def insertCoin(coin: Coin): VendingMachine => Event = { vendingMachine => CoinInserted(coin) }

  val returnCoins: VendingMachine => List[Event] = { vendingMachine =>

    val nickels = vendingMachine.coinHolder.nickels
    val dimes = vendingMachine.coinHolder.dimes
    val quarters = vendingMachine.coinHolder.quarters
    List(CoinHolderMoneyReturned(nickels, dimes, quarters), ResetDisplay)
  }

  val takeItem: VendingMachine => Event = { vendingMachine =>
    if (vendingMachine.dispenser.item.nonEmpty) {
      ItemTaken(vendingMachine.dispenser.item.get)
    } else {
      NoItemError
    }
  }

  val takeCoins: VendingMachine => Event = { vendingMachine =>
    if (vendingMachine.changeTray.amount != 0) {
      CoinsTaken(vendingMachine.changeTray.amount)
    } else {
      NoChangeError
    }
  }


  def selectItem(item: Item): VendingMachine => List[Event] = {
    vendingMachine: VendingMachine =>
      val coinHolder = vendingMachine.coinHolder
      val coinHolderAmount = coinHolder.amount

      def coinReturnMakeChange(): List[Event] = {
        val amountToReturn = coinHolder.amount - item.price
        coinHolder.addCoinsToCoinReturn().coinReturn.makeChange(amountToReturn) match {
          case Some(coins) => {
            List(ItemSelected(item), CoinReturnMoneyReturned(coins._1, coins._2, coins._3), AcceptCoinHolder)
          }
          case None => List(CannotMakeChange(coinHolder.nickels, coinHolder.dimes, coinHolder.quarters))
        }
      }

      def coinHolderMakeChange(): List[Event] = {
        vendingMachine.coinHolder.makeChange(item) match {
          case Some(coins) => List(ItemSelected(item), CoinHolderMoneyReturned(coins._1, coins._2, coins._3), AcceptCoinHolder)
          case None => coinReturnMakeChange()
        }
      }

      def getEvents(item: Item, price: BigDecimal, stock: Int): List[Event] = {
        val isStocked = stock != 0
        if (isStocked) {
          coinHolderAmount match {
            case _ if coinHolderAmount > price => coinHolderMakeChange()
            case _ if coinHolderAmount == price => List(ItemSelected(item))
            case _ if coinHolderAmount < price => List(InvalidAmount(item))
          }
        } else {
          List(OutOfStock(item))
        }
      }

      item match {
        case Chips => getEvents(Chips, Constants.PRICE_OF_CHIPS, vendingMachine.itemHolder.chips)
        case Cola => getEvents(Cola, Constants.PRICE_OF_COLA, vendingMachine.itemHolder.colas)
        case Candy => getEvents(Candy, Constants.PRICE_OF_CANDY, vendingMachine.itemHolder.candies)
      }

  }

  val checkDisplay: VendingMachine => Event = { vendingMachine =>
    DisplayChecked(vendingMachine.coinHolder.amount)
  }

  val checkDispenser: VendingMachine => Event = { vendingMachine =>
    DispenserChecked(vendingMachine.dispenser.item)
  }

  val checkCoinReturn: VendingMachine => Event = { vendingMachine =>
    CoinReturnChecked(vendingMachine.coinHolder.coinReturn)
  }

  def apply(itemHolder: ItemHolder): VendingMachine = apply().copy(itemHolder = itemHolder)

  def apply(coinHolder: CoinHolder): VendingMachine = apply().copy(coinHolder = coinHolder)

  def apply(): VendingMachine = VendingMachine(coinHolder = CoinHolder.EMPTY, display = Display(message = Constants.INSERT_COIN), dispenser = Dispenser(None), itemHolder = ItemHolder.FULL, changeTray = ChangeTray.EMPTY)

}

case class VendingMachine(coinHolder: CoinHolder, display: Display, dispenser: Dispenser, itemHolder: ItemHolder, changeTray: ChangeTray) extends TopObject

