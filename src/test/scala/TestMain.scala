object TestMain extends App {


  import scala.collection.mutable.ListBuffer

  var classTypes = ListBuffer[Foo]()
  var characterTypes = ListBuffer[Character]()


  trait Character {
    if(!characterTypes.exists(_.getClass.getName == this.getClass.getName)){
      characterTypes.append(this)
    }
  }

  trait Foo {
    if(!classTypes.exists(_.getClass.getName == this.getClass.getName)){
      classTypes.append(this)
    }
  }

  case class Bazoo() extends Foo

  case object Bar extends Foo

  class Phart() extends Foo

  class Turtle() extends Phart

  class Ryan extends Character

  class Rob extends Character

  val aa = new Rob()
  val bb = new Ryan()
  val cc = new Ryan()

  val a = Bazoo()
  val b = Bar
  val z = Bar
  val c = Bazoo()
  val d = new Phart()
  val e = new Phart()
  val f = Bazoo()
  val n = new Turtle()

  val func: Unit => Unit = { _ =>
    classTypes.foreach {
      case Bar => doBar()
      case phart: Phart => doPhart()
      case Bazoo() => doBazoo()
      case turtle: Turtle => println("Turtle")
    }

    def doBar(): Unit = {
      println("Bar")
    }

    def doBazoo(): Unit = {
      println("Bazoo")
    }

    def doPhart(): Unit = {
      println("Phart")
    }

  }


  println(classTypes)
  println(characterTypes)
}

