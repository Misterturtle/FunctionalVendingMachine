package utils

import domain_models._
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class CoinReturnTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "Given exact change, When selecting an item, it should accept the coinHolder coins" in {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Chips))

    Story.runStory(s1, emptyVM).coinHolder.coinReturn shouldBe CoinReturn(0,0,2)
  }

  "Given the coinHolder cannot make change"  - {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Candy))

    "Given the coinReturn can make change with the current inventory, the coinReturn should receive the coinHolder amount and dispense the changeTray amount" in {
      val enoughChange = VendingMachine(coinHolder = CoinHolder.EMPTY.copy(coinReturn = CoinReturn(3,2,3)))

      val result = Story.runStory(s1, enoughChange)

      result.coinHolder.coinReturn shouldBe CoinReturn(3,1,6)
    }
  }

  "Given the machine cannot make change for an item selected, the coinReturn should not change" in {
    val emptyCoinHolder = VendingMachine(coinHolder = CoinHolder.EMPTY.copy(coinReturn = CoinReturn(0, 0, 2)))
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Candy))

    val result = Story.runStory(s1, emptyCoinHolder)

    result.coinHolder.coinReturn shouldBe CoinReturn(nickels = 0, dimes = 0, quarters = 2)
  }
}
