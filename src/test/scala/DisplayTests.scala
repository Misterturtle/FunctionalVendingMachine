import domain_models._
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story
import utils.Constants

class DisplayTests extends FreeSpec with Matchers {

  import VendingMachine._

  val emptyVM = VendingMachine()

  "When inserting a nickel" - {

    val s1 = Story().first(insertCoin(Nickel))

    "The display shows $0.05" in {
      val result = Story.runStory(s1, emptyVM)

      result.display.message shouldBe "$0.05"
    }
  }

  "When inserting a dime the display shows $0.10" in {

    val s1 = Story().first(insertCoin(Dime))
    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.10"
  }

  "When inserting a quarter the display shows $0.25" in {
    val s1 = Story().first(insertCoin(Quarter))
    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.25"
  }

  "When inserting 3 dimes the display shows $0.30" in {
    val s1 = Story()
      .first(insertCoin(Dime))
      .andThen(insertCoin(Dime))
      .andThen(insertCoin(Dime))

    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe "$0.30"
  }


  "Checking the display" - {
    "When balance is 0 should show INSERT COIN" in {
      emptyVM.display.message shouldBe "INSERT COIN"
    }

    "When balance is more than 0 should show the amount" in {
      val s1 = Story().first(insertCoin(Nickel))
      val s2 = Story().first(insertCoin(Dime))
      val s3 = Story().first(insertCoin(Quarter))

      val nickelResult = Story.runStory(s1, emptyVM)
      val dimeResult = Story.runStory(s2, emptyVM)
      val quarterResult = Story.runStory(s3, emptyVM)

      nickelResult.display.message shouldBe "$0.05"
      dimeResult.display.message shouldBe "$0.10"
      quarterResult.display.message shouldBe "$0.25"
    }
  }

  "Given there is not enough money in the vendingMachine, when selecting chips" - {

    "Given no money has been inserted" - {
      val s1 = Story()
        .first(selectItem(Chips))
      "Selecting chips should display PRICE - <price of item>" in {

        Story.runStory(s1, emptyVM).display.message shouldBe "PRICE - $0.50"

      }

      "Checking the display after selecting chips should display insert coin" in {
        val localStory = s1.andThen(checkDisplay)

        Story.runStory(localStory, emptyVM).display.message shouldBe "INSERT COIN"
      }
    }

    "Given some money has been inserted" - {
      val s1 = Story()
        .first(insertCoin(Quarter))
        .andThen(selectItem(Chips))

      "Selecting chips should display PRICE - <price of item>" in {
        Story.runStory(s1, emptyVM).display.message shouldBe "PRICE - $0.50"
      }

      "Checking the display after selecting chips displays the coinHolder amount" in {
        val localStory = s1.andThen(checkDisplay)

        Story.runStory(localStory, emptyVM).display.message shouldBe "$0.25"
      }
    }
  }

  "Given the exact amount has been inserted for chips, when selecting chips" - {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Chips))


    "Display should show THANK YOU" in {
      Story.runStory(s1, emptyVM).display.message shouldBe "THANK YOU"
    }

    "After checking the display, the display should be INSERT COIN" in {
      val localStory = s1.andThen(checkDisplay)

      val result = Story.runStory(localStory, emptyVM)

      result.display.message shouldBe "INSERT COIN"
    }
  }


  "Given the user requested coins returned, the display should show INSERT COIN" in {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(returnCoins)

    val result = Story.runStory(s1, emptyVM)

    result.display.message shouldBe Constants.INSERT_COIN
  }

  "Given the machine cannot make change for the item selection, the display should" - {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Candy))


    "show CANNOT MAKE CHANGE" in {
      val result = Story.runStory(s1, emptyVM)

      result.display.message shouldBe Constants.CANNOT_MAKE_CHANGE
    }

    "After checking display, show INSERT COIN" in {
      val localStory = s1.andThen(checkDisplay)

      val result = Story.runStory(localStory, emptyVM)

      result.display.message shouldBe Constants.INSERT_COIN
    }
  }


  "Given the item holder is out of stock of an item" - {
    val outOfStock = VendingMachine(itemHolder = ItemHolder.EMPTY)
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Chips))

    "When selecting that item, the display shows SOLD OUT" in {
      val result = Story.runStory(s1, outOfStock)

      result.display.message shouldBe Constants.SOLD_OUT
    }

    "After checking display, given there is money in the machine, it should show the amount of money" in {
      val localStory = s1.andThen(checkDisplay)

      val result = Story.runStory(localStory, outOfStock)

      result.display.message shouldBe "$0.50"
    }

    "After checking display, given there is no money in the machine, it should show INSERT COIN" in {
      val localStory = s1.andThen(returnCoins).andThen(checkDisplay)

      val result = Story.runStory(localStory, outOfStock)

      result.display.message shouldBe "INSERT COIN"
    }
  }
}
