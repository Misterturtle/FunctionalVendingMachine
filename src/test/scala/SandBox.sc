import scala.collection.mutable.ListBuffer

var classTypes = ListBuffer[Foo]()

trait Foo {
  def isContains = {
    !classTypes.contains(this) && !classTypes.exists(_.getClass.getName == this.getClass.getName)
    classTypes.contains()
  }

  if(!classTypes.contains(this)){
    classTypes.append(this)
  }
}
case class Bazoo() extends Foo
case object Bar extends Foo
class Phart() extends Foo


val a = Bazoo()
val b = Bar
val z = Bar
val c = Bazoo()
val d = new Phart()
val e = new Phart()
val f = Bazoo()

println(b.getClass.getName)
println(z.getClass)
val func: Unit => Unit = { _ =>
  classTypes.foreach{
    case Bar => doBar()
    case phart:Phart => doPhart()
    case Bazoo() => doBazoo()
  }



  def doBar(): Unit ={
    println("Bar")
  }

  def doBazoo(): Unit ={
    println("Bazoo")
  }

  def doPhart(): Unit ={
    println("Phart")
  }

}

func()