package tree

object EventTree{
  def apply(): EventTree = new EventTree()
}
class EventTree() {

  var counter = 0

  def modify(): Unit ={
    counter = 1
  }

}
