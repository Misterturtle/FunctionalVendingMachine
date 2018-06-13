package events

import tree.EventTree
import tree.nodes.Node

object Event {
}
trait Event {
  def run(node: Node):Node
}
