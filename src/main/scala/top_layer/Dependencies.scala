package top_layer

import domain_models.{CoinHolder, VendingMachine}
import events.Event

object Dependencies {

  trait TopObject

  def injectAllDependencies(topObject: TopObject, event: (TopObject) => Event): Event = {
      topObject match {
        case vendMach: VendingMachine => injectVendingMachine(vendMach, event(vendMach))
      }
  }

  def injectVendingMachine(origVM: VendingMachine, event: Event): Event = {
    event match {
      case vm: VendingMachineDependency => vm.inject(origVM)
      case coinHolder: CoinHolderDependency => coinHolder.inject(origVM.coinHolder)
      case _ =>
    }

    event
  }


  trait Dependency[A] {
    protected var _dependency: Option[A] = None

    def inject(dependency: A): Unit = {
      _dependency = Some(dependency)
    }
  }

  trait VendingMachineDependency extends Dependency[VendingMachine] {
    def vendingMachineDependency = _dependency.get
  }

  trait CoinHolderDependency extends Dependency[CoinHolder] {
    def coinHolderDependency = _dependency.get
  }


}