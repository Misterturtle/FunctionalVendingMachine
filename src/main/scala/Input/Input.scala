package Input

import domain_models.{Coin, Item}

trait Input {

}

case class InsertCoin(coin:Coin) extends Input

case class SelectItem(item:Item) extends Input

case object CheckDisplay extends Input

case object CheckDispenser extends Input

case object CheckCoinReturn extends Input

case object TakeItem extends Input

case object TakeCoins extends Input

case object Reset extends Input
