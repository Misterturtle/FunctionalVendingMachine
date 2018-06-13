package tree.nodes

case object FooNode{
  def apply(something:Int): FooNode = FooNode(something, "")
}
case class FooNode(something:Int, someString:String) extends Node
