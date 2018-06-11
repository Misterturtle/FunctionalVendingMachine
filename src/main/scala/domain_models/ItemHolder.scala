package domain_models


case object ItemHolder {
  val FULL = new ItemHolder(20,20,20)

  val EMPTY = new ItemHolder(0,0,0)

}
case class ItemHolder(chips:Int, colas:Int, candies: Int)
