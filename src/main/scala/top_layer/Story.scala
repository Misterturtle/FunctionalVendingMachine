package top_layer

import EventSystem.{Event, SideEffectGraph, StoryCharacter}

case object Story {
  def apply() = new Story(Nil)

  def runStory[A](story: Story, character: StoryCharacter): A = {
    story.eventStack.reverse.foldLeft(character) {
      case (newState, eventFn) => {
        eventFn(newState).foldLeft(newState){
          case (newerState, event) => SideEffectGraph.updateTopObject(newerState, event)
        }
      }
    }
  }
}

case class Story(eventStack: List[Character => List[Event]]) {

  def first(eventFn: Character => Event): Story = {copy(eventStack = { character: Character => List(eventFn(character))} :: eventStack)}

  def first(eventFns: Character => List[Event], jvmRequirement: Int = 0): Story = andThen(eventFns)

  def andThen(eventFn: Character => Event): Story = copy(eventStack = {vm:Character => List(eventFn(vm))} :: eventStack)

  def andThen(eventFns: Character => List[Event], jvmRequirement: Int = 0): Story = copy(eventStack = {eventFns :: eventStack})
}