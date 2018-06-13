package tree.nodes

case object FooLeaf{
  def apply(something:Int): FooLeaf = FooLeaf(something, "")
}
case class FooLeaf(var something:Int, someString:String) extends Leaf
