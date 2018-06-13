package tree.nodes

case object BarNode{
  def apply(something:Int):BarNode = BarNode(something, "")
}
case class BarNode(something:Int, someString:String) extends Node


