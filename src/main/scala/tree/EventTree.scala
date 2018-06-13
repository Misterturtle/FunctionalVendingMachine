package tree

import tree.nodes.Node

object EventTree{
  def apply(): EventTree = new EventTree(Nil)
}

case class EventTree(nodes:List[Node]) {

  var counter = 0

  def modify(): Unit ={
    counter = 1
  }

}
