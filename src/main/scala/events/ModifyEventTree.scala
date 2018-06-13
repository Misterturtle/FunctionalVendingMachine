package events

import tree.EventTree

case object ModifyEventTree extends Event {
  override def run(eventTree:EventTree): EventTree = {
    eventTree.counter += 1
    eventTree
  }
}
