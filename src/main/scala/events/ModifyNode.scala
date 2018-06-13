package events

import tree.{EventTree, Node}

case object ModifyNode extends Event {
  override def run(node:Node): Node = node.copy(something = node.something + 1)

}
