package tree

import org.scalatest.{FreeSpec, FunSuite, Matchers}

class EventTreeTest extends FreeSpec with Matchers {

  "A tree can be modified" in {
    val tree = new EventTree()

    tree.modify()

    tree.counter shouldBe 1
  }


}
