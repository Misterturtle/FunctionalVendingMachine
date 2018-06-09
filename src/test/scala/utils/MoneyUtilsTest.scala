package utils

import org.scalatest.{FreeSpec, FunSuite, Matchers}

class MoneyUtilsTest extends FreeSpec with Matchers {

  "addMoneyStrings can handle" - {

    "A positive string without dollars" in {

      val strangs = List("$.05", "$.10")

      MoneyUtils.addMoneyStrings(strangs) shouldBe "$0.15"
    }

    "A positive string with dollars" in {
      val strangs = List("$1.05", "$2.10")

      MoneyUtils.addMoneyStrings(strangs) shouldBe "$3.15"
    }

    "A negative string without dollars" in {
      val strangs = List("$-.05", "$-.10")

      MoneyUtils.addMoneyStrings(strangs) shouldBe "$-0.15"
    }

    "A negative string with dollars" in {
      val strangs = List("$-2.05", "$-1.10")

      MoneyUtils.addMoneyStrings(strangs) shouldBe "$-3.15"
    }
  }
}
