package events

import tree.{EventTree, Node}

object Event {
}
trait Event {
  def run(node: Node):Node
}
