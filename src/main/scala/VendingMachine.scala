import scala.collection.mutable.ListBuffer

object EventExecutor {

  def updateDisplay(machine: VendingMachine, display: Display): VendingMachine = machine.copy(display = display)

  def updateCoinHolder(vendingMachine: VendingMachine, coinHolder: CoinHolder): VendingMachine = vendingMachine.copy(coinHolder = coinHolder)

  def update(origMachine: VendingMachine, events: List[Event]): VendingMachine = {


    events.reverse.foldLeft(origMachine) { case (vm,e) =>

      var mMachine = vm

      if(e.isInstanceOf[CoinHolderEvent]){
        mMachine = updateCoinHolder(mMachine, e.asInstanceOf[CoinHolderEvent].run(vm.coinHolder))
      }

      if(e.isInstanceOf[DisplayEvent]){
        mMachine = updateDisplay(mMachine, e.asInstanceOf[DisplayEvent].run(vm.display))
      }

      mMachine
    }
  }
}




object VendingMachineAPI {

  def insertNickel(vendingMachine: VendingMachine): Event = NickelInserted

  def checkDisplay(vendingMachine: VendingMachine): Event = DisplayChecked
  
}


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

trait Event

trait CoinHolderEvent extends Event {
  def run(coinHolder: CoinHolder): CoinHolder
}

trait DisplayEvent extends Event {
  def run(display: Display): Display
}


case class CoinHolder(amount: BigDecimal)

case class Display(message: String)

case object NickelInserted extends CoinHolderEvent with DisplayEvent {
  override def run(coinHolder: CoinHolder): CoinHolder = coinHolder.copy(coinHolder.amount + .05)

  override def run(display: Display): Display = {
    val newMessage = MoneyUtils.addMoneyStrings(List(display.message, "$.05"))
    display.copy(message = newMessage)
  }
}

case object DisplayChecked extends DisplayEvent {
  override def run(display: Display): Display = {
    println(display.message)
    display
  }
}


case object VendingMachine{
  def apply(): VendingMachine = VendingMachine(CoinHolder(0), Display(""))
}
case class VendingMachine(coinHolder: CoinHolder, display: Display)

