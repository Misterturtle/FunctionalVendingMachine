import events.{DoubleBar, DoubleFoo, Event, ModifyNode}
import org.scalatest.{FreeSpec, Matchers}
import story.Story
import tree.EventTree
import tree.nodes.{BarLeaf, FooLeaf, Leaf}

class EventTest extends FreeSpec with Matchers {

  "I want an event" in {
    val event = ModifyNode
    event shouldNot be (null)
  }

  "Events are single units of execution" in {
    val node = FooLeaf(5)
    val event = ModifyNode


    event.run(node).something shouldBe 6
  }

  "Events modify a node on the EventTree" in {
    val node = FooLeaf(1)
    val event = ModifyNode

    event.run(node).something shouldBe 2
  }

  "Events can modify multiple types of nodes" in {
    val fooNode = FooLeaf(5)
    val barNode = BarLeaf(8)

    val event = ModifyNode

    event.run(fooNode).something shouldBe 6
    event.run(barNode).something shouldBe 9
  }

  "Events can modify the properties of the subclass of the node" in {
    val fooNode = FooLeaf(5, "foo")
    val barNode = BarLeaf(8, "bar")

    val event = ModifyNode

    event.run(fooNode).asInstanceOf[FooLeaf].someString shouldBe "foo"
    event.run(barNode).asInstanceOf[BarLeaf].someString shouldBe "bar"
  }

  "Events are collected in a story" in {
    val story = Story()
      .first(DoubleBar)
      .andThen(DoubleFoo)

    story.eventLog shouldBe List(DoubleFoo, DoubleBar)
  }
}
