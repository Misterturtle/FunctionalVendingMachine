package tree.nodes

case object BarLeaf{
  def apply(something:Int):BarLeaf = BarLeaf(something, "")
}
case class BarLeaf(var something:Int, someString:String) extends Leaf


