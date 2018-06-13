package story

import events.Event

case object Story{
  def apply(): Story = Story(Nil)
}
case class Story(eventLog:List[Event]){
  def first(event:Event): Story = copy(event :: eventLog)
  def andThen(event:Event): Story = copy(event :: eventLog)
}