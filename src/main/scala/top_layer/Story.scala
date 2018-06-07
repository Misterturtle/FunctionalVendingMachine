package top_layer

import domain_models.VendingMachine
import events.Event

import scala.collection.mutable.ListBuffer

case object Story{
  def runStory(story:Story, vendingMachine: VendingMachine) : VendingMachine = EventExecutor.update(vendingMachine, story.getEvents())
}

case class Story(){
  def getEvents(): List[Event] = _events.toList

  private val _events = ListBuffer[Event]()

  def first[A](event: Event) : Story = andThen(event)

  def first[A](events: List[Event]) : Story = andThen(events)

  def andThen[A](event: Event): Story = andThen(List(event))

  def andThen[A](events: List[Event]): Story = {
    events.foreach(_events.prepend(_))
    this
  }

  def +(story:Story):Story = {
    story.getEvents().foreach(_events.prepend(_))
    this
  }
}