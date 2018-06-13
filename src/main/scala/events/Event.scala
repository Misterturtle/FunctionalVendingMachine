package events

import tree.EventTree

object Event {
}
trait Event {
  def run(eventTree: EventTree):EventTree
}
