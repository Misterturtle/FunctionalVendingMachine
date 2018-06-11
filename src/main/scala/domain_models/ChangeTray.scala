package domain_models

case object ChangeTray{
  def EMPTY = new ChangeTray(0,0,0)

}
case class ChangeTray(nickels: Int, dimes: Int, quarters: Int) {
  def amount = (nickels * 0.05) + (dimes * 0.10) + (quarters * 0.25)

  def removeNickels(amount: Int) = copy(nickels = nickels - amount)

  def removeDimes(amount: Int) = copy(dimes = dimes - amount)

  def removeQuarters(amount: Int) = copy(quarters = quarters - amount)

  def addNickels(amount: Int) = copy(nickels = nickels + amount)

  def addDimes(amount: Int) = copy(dimes = dimes + amount)

  def addQuarters(amount: Int) = copy(quarters = quarters + amount)
}
