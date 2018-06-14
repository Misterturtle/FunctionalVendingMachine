import domain_models._
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

import scala.collection.mutable.ListBuffer


class CoinHolderTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "When inserting a nickel the coinHolder should have 1 nickel" in {
    val s1 = Story().first(insertCoin(Nickel))

    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.nickels shouldBe 1
  }

  "When inserting a dime the coinHolder should have 1 dime" in {
    val s1 = Story().first(insertCoin(Dime))
    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.dimes shouldBe 1
  }

  "When inserting a quarter the coinHolder should have 1 quarter" in {
    val s1 = Story().first(insertCoin(Quarter))
    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.quarters shouldBe 1
  }

  "When inserting 3 dimes the coinHolder should have 3 dimes" in {
    val s1 = Story()
      .first(insertCoin(Dime))
      .andThen(insertCoin(Dime))
      .andThen(insertCoin(Dime))

    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.dimes shouldBe 3
  }

  "Given enough money, when item is selected, then subtract items value" in {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Chips))

    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.quarters shouldBe 0
    result.coinHolder.coinReturn.quarters shouldBe 2
  }

  "Given $0.50 has been inserted, returning coins removes the amount from the coinHolder" in {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(returnCoins)

    val result = Story.runStory(s1, emptyVM)

    result.coinHolder shouldBe CoinHolder(0,0,0)
  }


  "Given the machine cannot make change for an item selected, the coinHolder should be empty" in {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Candy))

    val result = Story.runStory(s1, emptyVM)

    result.coinHolder shouldBe CoinHolder(nickels = 0, dimes = 0, quarters = 0)
  }
}
