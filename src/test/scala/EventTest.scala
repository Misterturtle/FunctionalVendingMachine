import events.{Event, ModifyNode}
import org.scalatest.{FreeSpec, Matchers}
import tree.EventTree
import tree.nodes.{BarNode, FooNode, Node}

class EventTest extends FreeSpec with Matchers {

  "I want an event" in {
    val event = ModifyNode
    event shouldNot be (null)
  }

  "Events are single units of execution" in {
    val node = FooNode(5)
    val event = ModifyNode


    event.run(node).something shouldBe 6
  }

  "Events modify a node on the EventTree" in {
    val node = FooNode(1)
    val event = ModifyNode

    event.run(node).something shouldBe 2
  }

  "Events can modify multiple types of nodes" in {
    val fooNode = FooNode(5)
    val barNode = BarNode(8)

    val event = ModifyNode

    event.run(fooNode).something shouldBe 6
    event.run(barNode).something shouldBe 9
  }
}
