package events

import tree.nodes.Node

case object DoubleBar extends Event {
  override def run(node: Node): Node = ???
}
