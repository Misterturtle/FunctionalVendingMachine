import domain_models.{Chips, ItemHolder, Quarter, VendingMachine}
import org.scalatest.{FreeSpec, Matchers}
import top_layer.Story
import utils.Constants

class ItemHolderTests extends FreeSpec with Matchers {

  import domain_models.VendingMachine._

  val emptyVM = VendingMachine()


  "Given the item is stocked, when an item is selected" - {
    val s1 = Story()
      .first(insertCoin(Quarter))
      .andThen(insertCoin(Quarter))
      .andThen(selectItem(Chips))

    "The item should be removed from the itemHolder" in {
      val result = Story.runStory(s1, emptyVM)

      result.itemHolder.chips shouldBe Constants.MAX_ITEM_COUNT - 1
    }


    "Given the item is sold out, when an item is selected, the count should not change" in {
      val soldOut = VendingMachine(itemHolder = ItemHolder.EMPTY)
      val result = Story.runStory(s1, soldOut)

      result.itemHolder.chips shouldBe 0
    }
  }
}
