package Input

import java.io.{BufferedReader, InputStreamReader, Reader}

import domain_models._

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class InputHandler {

  def getNextInput: Option[Input] = {
    if (inputStream.ready()) {
      inputStream.readLine().toLowerCase match {
        case "insert nickel" => Some(InsertCoin(Nickel))
        case "insert dime" => Some(InsertCoin(Dime))
        case "insert quarter" => Some(InsertCoin(Quarter))
        case "take coins" => Some(TakeCoins)

        case "select chips" => Some(SelectItem(Chips))
        case "select cola" => Some(SelectItem(Cola))
        case "select candy" => Some(SelectItem(Candy))
        case "take item" => Some(TakeItem)

        case "check display" => Some(CheckDisplay)
        case "check dispenser" => Some(CheckDispenser)
        case "check coin return" => Some(CheckCoinReturn)

        case "reset" => Some(Reset)
        case _ => {
          println("Unknown command")
          None
        }
      }
    } else None
  }

  val inputStream = new BufferedReader(new InputStreamReader(System.in))
}
