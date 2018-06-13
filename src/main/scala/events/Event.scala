package events

import tree.EventTree
import tree.nodes.Leaf

object Event {
}
trait Event {
  def run(leaf: Leaf):Leaf
}
