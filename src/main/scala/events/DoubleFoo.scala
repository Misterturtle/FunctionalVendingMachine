package events

import tree.nodes.Node

case object DoubleFoo extends Event {
  override def run(node: Node): Node = ???
}
