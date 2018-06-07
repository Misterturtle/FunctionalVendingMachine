import domain_models.VendingMachine
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class DisplayTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "When inserting a nickel" - {

    val s1 = Story().first(insertNickel(emptyVM))

    "The display shows $0.05" in {
      val result = Story.runStory(s1, emptyVM)

      result.display.message shouldBe "$0.05"
    }
  }

  "When inserting a dime The display shows $0.10" in {

    val s1 = Story().first(insertDime(emptyVM))
    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.10"
  }

  "When inserting a quarter The display shows $0.25" in {
    val s1 = Story().first(insertQuarter(emptyVM))
    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.25"
  }

  "When inserting 3 dimes The display shows $0.30" in {
    val s1 = Story()
      .first(insertDime(emptyVM))
      .andThen(insertDime(emptyVM))
      .andThen(insertDime(emptyVM))

    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.30"
  }


  "Checking the display before anything should show INSERT COIN" in {
    emptyVM.display.message shouldBe "INSERT COIN"
  }

}
