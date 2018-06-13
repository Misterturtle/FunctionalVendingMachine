package events

import tree.EventTree
import tree.nodes.{BarNode, FooNode, Node}

case object ModifyNode extends Event {
  override def run(node:Node): Node = {
    node match {
      case fooNode:FooNode => fooNode.copy(something = fooNode.something + 1)
      case barNode:BarNode => barNode.copy(something = barNode.something + 1)
    }
  }
}
