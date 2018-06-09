import domain_models.{Chips, Cola, VendingMachine}
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class DispenserTests extends FreeSpec with Matchers {

  import domain_models.VendingMachine._

  val vm = VendingMachine()

  "When enough money has been inserted" - {

    val parentStory =
      Story[VendingMachine]
        .first(insertQuarter)
      .andThen(insertQuarter)
      .andThen(insertQuarter)
      .andThen(insertQuarter)

    "Selecting chips should put chips in the dispenser" in {
      val localStory = parentStory.andThen(selectChips)

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe Some(Chips)
    }

    "Selecting cola should put cola in the dispenser" in {
      val localStory = parentStory.andThen(selectCola)

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe Some(Cola)
    }

    "Selecting cola should put candy in the dispenser" in {
      val localStory = parentStory.andThen(selectCola)

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe Some(Cola)
    }
  }

  "When not enough money has been inserted" - {
    "Selecting chips should not add anything to the dispenser" in {
      val localStory = Story[VendingMachine].first(selectChips)

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe None
    }

    "Selecting cola should not add anything to the dispenser" in {
      val localStory = Story[VendingMachine].first(selectCola)

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe None
    }

    "Selecting candy should not add anything to the dispenser" in {
      val localStory = Story[VendingMachine].first(selectCandy)

      val result = Story.runStory(localStory, vm)

      result.dispenser.item shouldBe None
    }
  }

}
