package katacrack.application

import weaver.FunSuite

object KatacrackTest extends FunSuite {

  test("avgSalaryByGender should return the average salary for all genders in CSV, sorted by salary") {
    val testFile = getClass.getResource("/test-file.csv").toURI
    expect.same(Katacrack.avgSalaryByGender(testFile),
                """All,M,38000
                  |All,F,25000
                  |All,O,15000""".stripMargin
    )
  }

  test("avgSalaryByProvinceAndGender should return the average salary for all province and genders in CSV, sorted by city and salary") {
    val testFile = getClass.getResource("/test-file.csv").toURI
    expect.same(
      Katacrack.avgSalaryByGender(testFile),
      """Barcelona,M,38000
        |Barcelona,F,20000
        |Tarragona,F,30000
        |Girona,O,15000""".stripMargin
    )
  }

}
