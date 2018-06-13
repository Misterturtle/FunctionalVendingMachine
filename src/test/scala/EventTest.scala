import events.{Event, ModifyNode}
import org.scalatest.{FreeSpec, Matchers}
import tree.{EventTree, Node}

class EventTest extends FreeSpec with Matchers {

  "I want an event" in {
    val event = ModifyNode
    event shouldNot be (null)
  }

  "Events are single units of execution" in {
    val node = Node(5)
    val event = ModifyNode


    event.run(node).something shouldBe 6
  }

  "Events modify a node on the EventTree" in {
    val node = Node(1)
    val event = ModifyNode

    event.run(node).something shouldBe Node(2)
  }
}
