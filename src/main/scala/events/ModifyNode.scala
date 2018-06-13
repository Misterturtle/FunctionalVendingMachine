package events

import tree.EventTree
import tree.nodes.{BarLeaf, FooLeaf, Leaf}

case object ModifyNode extends Event {
  override def run(node:Leaf): Leaf = {
    node match {
      case fooNode:FooLeaf => fooNode.copy(something = fooNode.something + 1)
      case barNode:BarLeaf => barNode.copy(something = barNode.something + 1)
    }
  }
}
