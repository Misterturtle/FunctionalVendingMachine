package events

import tree.nodes.Leaf

case object DoubleBar extends Event {
  override def run(node: Leaf): Leaf = ???
}
