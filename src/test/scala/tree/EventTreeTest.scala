package tree

import org.scalatest.{FreeSpec, FunSuite, Matchers}

class EventTreeTest extends FreeSpec with Matchers {

  "A tree can be modified" in {
    val tree = EventTree()

    tree.modify()

    tree.counter shouldBe 1
  }

  "A tree has nodes" in {
    val node = Node()
    val tree = EventTree(List(node))

    tree.nodes shouldBe List(node)
  }

}
