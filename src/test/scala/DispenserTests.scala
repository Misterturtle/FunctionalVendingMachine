import domain_models._
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class DispenserTests extends FreeSpec with Matchers {

  import domain_models.VendingMachine._

  val vm = VendingMachine()

  "When enough money has been inserted" - {

    val parentStory = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))

    "Selecting chips should put chips in the dispenser" in {
      val localStory = parentStory.andThen(selectItem(Chips))

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe Some(Chips)
    }

    "Selecting cola should put cola in the dispenser" in {
      val localStory = parentStory.andThen(selectItem(Cola))

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe Some(Cola)
    }

    "Selecting cola should put candy in the dispenser" in {
      val localStory = parentStory.andThen(selectItem(Cola))

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe Some(Cola)
    }
  }

  "When not enough money has been inserted" - {
    "Selecting chips should not add anything to the dispenser" in {
      val localStory = Story().first(selectItem(Chips))

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe None
    }

    "Selecting cola should not add anything to the dispenser" in {
      val localStory = Story().first(selectItem(Cola))

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe None
    }

    "Selecting candy should not add anything to the dispenser" in {
      val localStory = Story().first(selectItem(Candy))

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe None
    }
  }

}
