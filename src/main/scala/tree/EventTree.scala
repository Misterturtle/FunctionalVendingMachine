package tree

import tree.nodes.Leaf

object EventTree{
  def apply(): EventTree = new EventTree(Nil)
}

case class EventTree(nodes:List[Leaf]) {

  var counter = 0

  def modify(): Unit ={
    counter = 1
  }

}
