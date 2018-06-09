import domain_models.VendingMachine
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story

class DisplayTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "When inserting a nickel" - {

    val s1 = Story[VendingMachine].first(insertNickel)

    "The display shows $0.05" in {
      val result = Story.runStory(s1, emptyVM)

      result.display.message shouldBe "$0.05"
    }
  }

  "When inserting a dime the display shows $0.10" in {

    val s1 = Story[VendingMachine].first(insertDime)
    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.10"
  }

  "When inserting a quarter the display shows $0.25" in {
    val s1 = Story[VendingMachine].first(insertQuarter)
    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.25"
  }

  "When inserting 3 dimes the display shows $0.30" in {
    val s1 = Story[VendingMachine]
      .first(insertDime)
      .andThen(insertDime)
      .andThen(insertDime)

    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.30"
  }


  "Checking the display before anything should show INSERT COIN" in {
    emptyVM.display.message shouldBe "INSERT COIN"
  }

  "Given there is not enough money in the vendingMachine, when selecting chips" - {

    "Given no money has been inserted" - {
        val s1 = Story[VendingMachine]
          .first(selectChips)
      "Selecting chips should display PRICE - <price of item>" in {

        Story.runStory(s1, emptyVM).display.message shouldBe "PRICE - $0.50"

      }

      "Checking the display after selecting chips should display insert coin" in {
        val localStory = s1.andThen(checkDisplay)

        Story.runStory(localStory , emptyVM).display.message shouldBe "INSERT COIN"
      }
    }

    "Given some money has been inserted" - {
      val s1 = Story[VendingMachine]
        .first(insertQuarter)
        .andThen(selectChips)

      "Selecting chips should display PRICE - <price of item>" in {
        Story.runStory(s1, emptyVM).display.message shouldBe "PRICE - $0.50"
      }

      "Checking the display after selecting chips display the coinHolder amount" in {
        val localStory = s1.andThen(checkDisplay)

        Story.runStory(localStory , emptyVM).display.message shouldBe "$0.25"
      }
    }
  }

  "Given the exact amount has been inserted for chips, when selecting chips" - {
    val s1 = Story[VendingMachine]
      .first(insertQuarter)
      .andThen(insertQuarter)
      .andThen(selectChips)


    "Display should show THANK YOU" in {
      Story.runStory(s1, emptyVM).display.message shouldBe "THANK YOU"
    }

    "After checking the display, the display should be INSERT COIN" in {
      val localStory = s1.andThen(checkDisplay)

      val result = Story.runStory(localStory, emptyVM)

      result.display.message shouldBe "INSERT COIN"
    }
  }
}
