package EventSystem

trait SideEffect extends Event{
  def run(): Unit
}

trait OutputEvent extends SideEffect{
  def run():Unit
}

trait ErrorEvent extends SideEffect {
  def run(): Unit
}

trait Event{
  val name: String = this.getClass.toString
}