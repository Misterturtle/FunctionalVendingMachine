package top_layer

import domain_models.VendingMachine
import events.Event

import scala.collection.mutable.ListBuffer

case object Story{
  def apply[A] = new Story[A](Nil)


  def runStory[A](story:Story[A], character: A) : A = SideEffectGraph.update(character, story)


}

case class Story[A](eventStack:List[(A) => Event]){
  val eventsNames: List[String] = eventStack.map(_.getClass.toString)

  def first[B](eventFn: A => Event) : Story[A] = copy(eventStack = eventFn :: eventStack)

  def first[B](eventFns: (A) => List[Event]) : Story[A] = andThen(eventFns)

  def andThen[B](eventFn: A => Event) : Story[A] = copy(eventStack = eventFn :: eventStack)

  def andThen[B](eventFns: A => List[Event]): Story[A] =
}