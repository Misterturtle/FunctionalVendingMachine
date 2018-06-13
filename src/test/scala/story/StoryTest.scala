package story

import events.{DoubleBar, DoubleFoo, Event}
import org.scalatest.{FreeSpec, FunSuite, Matchers}
import tree.EventTree
import tree.nodes.Leaf

class StoryTest extends FreeSpec with Matchers {

  case class TestLeaf() extends Leaf {
    override var something: Int = 1
  }

  "A story can collect events" in {
    val story = Story()
      .first(DoubleBar)
      .andThen(DoubleFoo)

    story.eventLog shouldBe List(DoubleFoo, DoubleBar)
  }

  "A story can provide the event with a leaf" in {
    val leaf = TestLeaf()

    val story = Story()
      .first(DoubleFoo)

    story.run(leaf).something shouldBe 2
  }

  "A story can run events in order" in {
    var firstEventTime:Long = 0
    var secondEventTime: Long = 0

    case class FirstEvent() extends Event {
      override def run(leaf: Leaf): Leaf = {
        firstEventTime = System.currentTimeMillis()
        Thread.sleep(5)
        leaf
      }
    }

    case class SecondEvent() extends Event {
      override def run(leaf: Leaf): Leaf = {
        secondEventTime = System.currentTimeMillis()
        leaf
      }
    }

    val story = Story()
      .first(FirstEvent())
      .andThen(SecondEvent())

    story.run(TestLeaf())

    firstEventTime shouldNot be (0l)
    secondEventTime shouldNot be (0l)
    firstEventTime < secondEventTime shouldBe true
  }

}










