import org.scalatest.{FreeSpec, Matchers}

class EventTest extends FreeSpec with Matchers {

  "I want an event" in {
    val event = new Event()
    event shouldNot be (null)
  }

  


}
