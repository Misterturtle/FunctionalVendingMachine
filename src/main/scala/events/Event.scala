package events

import tree.EventTree
import tree.nodes.Leaf

object Event {
}

trait Event[T <: Leaf] {
  def run(leaf: T):T
}
