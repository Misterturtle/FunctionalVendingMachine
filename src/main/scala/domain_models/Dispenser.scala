package domain_models

case object Dispenser{
  def apply: Dispenser = new Dispenser(None)
}

case class Dispenser(item:Option[Item])
