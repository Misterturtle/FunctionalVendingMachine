package tree

case object Node{
  def apply():Node = Node(0)
}
case class Node(something:Int)
