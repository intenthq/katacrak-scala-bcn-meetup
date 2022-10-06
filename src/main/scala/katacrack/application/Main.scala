package katacrack.application

import java.net.URI

object Main extends App {
  println(Katacrack.avgSalaryByGender(new URI(args(0))))
  println(Katacrack.avgSalaryByProvinceAndGender(new URI(args(0))))
}
