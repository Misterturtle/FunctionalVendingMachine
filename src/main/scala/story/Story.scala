package story

import events.Event
import tree.nodes.Leaf

case object Story{
  def apply(): Story = Story(Nil)
}
case class Story(eventLog:List[Event]){
  def run(leaf:Leaf): Leaf = eventLog.reverse.foldLeft(leaf){ case (newLeaf, event) =>
      event.run(newLeaf)
  }

  def first(event:Event): Story = copy(event :: eventLog)
  def andThen(event:Event): Story = copy(event :: eventLog)


}