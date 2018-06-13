package events

import tree.nodes.Leaf

case object DoubleFoo extends Event {
  override def run(leaf: Leaf): Leaf = {
    leaf.something += 1
    leaf
  }
}
