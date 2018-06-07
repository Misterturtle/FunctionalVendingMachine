import org.scalatest.{FreeSpec, Matchers}

import scala.collection.mutable.ListBuffer


class VendingMachineTest extends FreeSpec with Matchers {

  import VendingMachineAPI._

  val emptyVM = VendingMachine()

  "When inserting a nickel" - {


      val s1 = Story().first(insertNickel(emptyVM))

    "The display shows $.05" in {
      val result = Story.runStory(s1, emptyVM)

      result.display.message shouldBe "$0.05"
    }

    "The coinHolder should have 5 cents" in {
      val result = Story.runStory(s1, emptyVM)

      result.coinHolder.amount shouldBe .05
    }
  }
}
