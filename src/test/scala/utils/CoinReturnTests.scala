package utils

import domain_models.VendingMachine
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class CoinReturnTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "More money than needed in coinHolder after item selection" in {
    val s1 = Story[VendingMachine]
      .first(insertQuarter)
      .andThen(insertQuarter)
      .andThen(insertQuarter)
      .andThen(selectChips)

    Story.runStory(s1, emptyVM).coinReturn.amount shouldBe 0.25
  }


}
