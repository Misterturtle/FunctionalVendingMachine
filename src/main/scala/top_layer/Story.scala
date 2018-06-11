package top_layer

import domain_models.VendingMachine
import events.Event
import top_layer.Dependencies.TopObject

case object Story {
  def apply() = new Story(Nil)

  def runStory(story: Story, topObject: TopObject): VendingMachine = {
    story.eventStack.reverse.asInstanceOf[List[TopObject => List[Event]]].foldLeft(topObject) {
      case (newState, eventFn) => {
        eventFn(newState).foldLeft(newState){
//        val injectedEvent = Dependencies.injectAllDependencies(newState, eventFn)
          case (newerState, event) => SideEffectGraph.updateTopObject(newerState, event)
        }
      }
    }.asInstanceOf[VendingMachine]
  }
}

case class Story(eventStack: List[VendingMachine => List[Event]]) {

  def first(eventFn: VendingMachine => Event): Story = {copy(eventStack = { vendingMachine: VendingMachine => List(eventFn(vendingMachine))} :: eventStack)}

  def first(eventFns: VendingMachine => List[Event], jvmRequirement: Int = 0): Story = andThen(eventFns)

  def andThen(eventFn: VendingMachine => Event): Story = copy(eventStack = {vm:VendingMachine => List(eventFn(vm))} :: eventStack)

  def andThen(eventFns: VendingMachine => List[Event], jvmRequirement: Int = 0): Story = copy(eventStack = {
    eventFns :: eventStack
  })
}