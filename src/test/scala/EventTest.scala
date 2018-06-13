import events.{Event, ModifyEventTree}
import org.scalatest.{FreeSpec, Matchers}
import tree.EventTree

class EventTest extends FreeSpec with Matchers {

  "I want an event" in {
    val event = new Event() {
      override def run(eventTree: EventTree): EventTree = ???
    }
    event shouldNot be (null)
  }

  "Events are single units of execution" in {
    val eventTree = EventTree()
    val event = new Event{
      def run(eventTree: EventTree):EventTree = {
        eventTree.counter += 1
        eventTree
      }
    }

    event.run(eventTree).counter shouldBe 1
  }

  "Events modify an EventTree" in {
    val eventTree = EventTree()
    val event = ModifyEventTree

    event.run(eventTree).counter shouldBe 1
  }
}
