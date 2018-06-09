import domain_models.VendingMachine
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

import scala.collection.mutable.ListBuffer


class CoinHolderTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "When inserting a nickel" - {


    val s1 = Story[VendingMachine].first(insertNickel)

    "The coinHolder should have 5 cents" in {
      val result = Story.runStory(s1, emptyVM)

      result.coinHolder.amount shouldBe .05
    }
  }

  "When inserting a dime The coinHolder should have 10 cents" in {

    val s1 = Story[VendingMachine].first(insertDime)
    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.amount shouldBe .1
  }

  "When inserting a quarter The coinHolder should have 25 cents" in {
    val s1 = Story[VendingMachine].first(insertQuarter)
    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.amount shouldBe .25
  }

  "When inserting 3 dimes The coinHolder should have 30 cents" in {
    val s1 = Story[VendingMachine]
      .first(insertDime)
      .andThen(insertDime)
      .andThen(insertDime)

    val result = Story.runStory(s1, emptyVM)

    result.coinHolder.amount shouldBe .3
  }
}
