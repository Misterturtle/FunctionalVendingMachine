package top_layer

import Input._
import domain_models._
import top_layer.Dependencies.TopObject

object Main extends App{

  private var _topObject: TopObject = VendingMachine()
  val inputHandler = new InputHandler()

  def loop(topObject: TopObject): TopObject = {
    val input = inputHandler.getNextInput
    val inputStory = makeInputStory(topObject, input)
    inputStory match {
      case Some(story) => Story.runStory(story, topObject)
      case None => topObject
    }
  }

  def startProgram(): Unit = {
    val LOOP_DURATION = 100
    var startOfLoop:Long = 0

    while(startOfLoop - System.currentTimeMillis() < LOOP_DURATION){
      startOfLoop = System.currentTimeMillis()
      _topObject = loop(_topObject)
    }
  }

  def makeInputStory(topObject:TopObject, maybeInput: Option[Input]) :  Option[Story] = {
    maybeInput.map{
      case InsertCoin(coin:Coin) => Story().first(VendingMachine.insertCoin(coin))
      case SelectItem(item:Item) => Story().first(VendingMachine.selectItem(item))
      case CheckDisplay => Story().first(VendingMachine.checkDisplay)
      case CheckDispenser => Story().first(VendingMachine.checkDispenser)
      case CheckCoinReturn => Story().first(VendingMachine.checkCoinReturn)
      case TakeItem => Story().first(VendingMachine.takeItem)
      case TakeCoins => Story().first(VendingMachine.takeCoins)
      case Reset => Story().first(VendingMachine.returnCoins)
    }
  }





  startProgram()
}