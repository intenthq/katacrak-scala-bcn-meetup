package katacrack.application

import cats.effect.IO

import weaver.SimpleIOSuite

object KatacrackBigTest extends SimpleIOSuite {

  test("100M rows?") {
    Generator.createFile(1 * 1000 * 1000).use { path =>
      IO(Katacrack.avgSalaryByGender(path.toUri)).map { result =>
        expect.same("All,M,32054\nAll,F,26959\nAll,O,24136", result)
      }
    }
  }

}
