import domain_models.{Quarter, VendingMachine}
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class ChangeTrayTests extends FreeSpec with Matchers {

  import domain_models.VendingMachine._

  val emptyVM = VendingMachine()
  "When there is money in the coinHolder" - {
    val s1 = Story()
      .first(insertCoin(Quarter))

    "When a customers requests a return" - {
      val localStory = s1.andThen(returnCoins)

      "CoinHolder should be empty" in {
        val result = Story.runStory(localStory, emptyVM)

        result.coinHolder.amount shouldBe 0
      }

      "Change tray should have the coinHolder coins" in {
        val result = Story.runStory(localStory, emptyVM)

        result.changeTray.amount shouldBe 0.25
      }
    }
  }
}