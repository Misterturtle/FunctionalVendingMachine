package domain_models

trait Item{
  val price: BigDecimal
}

case object Chips extends Item{
  val price:BigDecimal = 0.50
}

case object Cola extends Item{
  val price:BigDecimal = 1.00
}

case object Candy extends Item{
  val price: BigDecimal = 0.65
}
