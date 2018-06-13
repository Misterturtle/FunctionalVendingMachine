package story

import events.{DoubleBar, DoubleFoo, Event}
import org.scalatest.{FreeSpec, FunSuite, Matchers}
import tree.EventTree
import tree.nodes.Leaf

class StoryTest extends FreeSpec with Matchers {

  "A story can collect events" in {
    val story = Story()
      .first(DoubleBar)
      .andThen(DoubleFoo)

    story.eventLog shouldBe List(DoubleFoo, DoubleBar)
  }

  "A story can provide the event with a leaf in order to run" in {
    val leaf = new Leaf {
      override var something: Int = 1
    }

    val story = Story()
      .first(DoubleFoo)

    story.run(leaf).something shouldBe 2
  }

}
