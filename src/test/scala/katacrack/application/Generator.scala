package katacrack.application

import java.io.FileWriter
import java.nio.file.{Files, Path}

import cats.effect.IO
import cats.effect.kernel.Resource
import cats.effect.unsafe.implicits.global

import enumeratum._
import kantan.csv.enumeratum._
import kantan.csv.generic._
import kantan.csv.ops._
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

object Generator extends App {

  def createFile(numRows: Int) =
    for {
      path <- Resource.make(IO(Files.createTempFile("test-katacrack", ".csv")))(p => IO(Files.delete(p)))
      _ <- fillFile(path, numRows)
    } yield path

  private def fillFile(path: Path, numRows: Int): Resource[IO, Unit] =
    for {
      fileWriter <- Resource.fromAutoCloseable(IO(new FileWriter(path.toFile)))
      csvWriter <- Resource.fromAutoCloseable(IO(fileWriter.asCsvWriter[PersonSalary](kantan.csv.rfc)))
      it <- Resource.pure(
        Gen
          .infiniteStream(PersonSalary.gen)
          .pureApply(Gen.Parameters.default, Seed.apply(1234567890L))
          .take(numRows)
          .iterator
      )
      _ <- Resource.eval(IO(csvWriter.write(it)))
      _ <- Resource.eval(IO(fileWriter.flush()))
    } yield ()

  createFile(1 * 1000 * 1000).use(file => IO.println(s"file $file ${Files.size(file) / 1024 / 1024}")).unsafeRunSync()

}

sealed trait Gender extends EnumEntry
object Gender extends Enum[Gender] {
  val values = findValues

  case object M extends Gender
  case object F extends Gender
  case object O extends Gender

  val gen = Gen.frequency(
    23222953 -> M,
    24162154 -> F,
    473851 -> O
  )
}

sealed trait Province extends EnumEntry
object Province extends Enum[Province] {
  val values = findValues

  case object `A Coruña` extends Province
  case object `Álava` extends Province
  case object `Albacete` extends Province
  case object `Alicante` extends Province
  case object `Almería` extends Province
  case object `Asturias` extends Province
  case object `Ávila` extends Province
  case object `Badajoz` extends Province
  case object `Baleares` extends Province
  case object `Barcelona` extends Province
  case object `Burgos` extends Province
  case object `Cáceres` extends Province
  case object `Cádiz` extends Province
  case object `Cantabria` extends Province
  case object `Castellón` extends Province
  case object `Ceuta` extends Province
  case object `Ciudad Real` extends Province
  case object `Córdoba` extends Province
  case object `Cuenca` extends Province
  case object `Girona` extends Province
  case object `Granada` extends Province
  case object `Guadalajara` extends Province
  case object `Gipuzkoa` extends Province
  case object `Huelva` extends Province
  case object `Huesca` extends Province
  case object `Jaén` extends Province
  case object `La Rioja` extends Province
  case object `Las Palmas` extends Province
  case object `León` extends Province
  case object `Lérida` extends Province
  case object `Lugo` extends Province
  case object `Madrid` extends Province
  case object `Málaga` extends Province
  case object `Melilla` extends Province
  case object `Murcia` extends Province
  case object `Navarra` extends Province
  case object `Ourense` extends Province
  case object `Palencia` extends Province
  case object `Pontevedra` extends Province
  case object `Salamanca` extends Province
  case object `Segovia` extends Province
  case object `Sevilla` extends Province
  case object `Soria` extends Province
  case object `Tarragona` extends Province
  case object `Santa Cruz de Tenerife` extends Province
  case object `Teruel` extends Province
  case object `Toledo` extends Province
  case object `Valencia` extends Province
  case object `Valladolid` extends Province
  case object `Vizcaya` extends Province
  case object `Zamora` extends Province
  case object `Zaragoza` extends Province

  // https://www.ine.es/jaxiT3/Tabla.htm?t=2852
  val gen: Gen[Province] = Gen.frequency(
    386464 -> `Albacete`,
    1881762 -> `Alicante`,
    731792 -> `Almería`,
    333626 -> `Álava`,
    1011792 -> `Asturias`,
    158421 -> `Ávila`,
    669943 -> `Badajoz`,
    1173008 -> `Baleares`,
    5714730 -> `Barcelona`,
    1154334 -> `Vizcaya`,
    356055 -> `Burgos`,
    389558 -> `Cáceres`,
    1245960 -> `Cádiz`,
    584507 -> `Cantabria`,
    587064 -> `Castellón`,
    492591 -> `Ciudad Real`,
    776789 -> `Córdoba`,
    1120134 -> `A Coruña`,
    195516 -> `Cuenca`,
    726033 -> `Gipuzkoa`,
    786596 -> `Girona`,
    921338 -> `Granada`,
    265588 -> `Guadalajara`,
    525835 -> `Huelva`,
    224264 -> `Huesca`,
    627190 -> `Jaén`,
    451706 -> `León`,
    439727 -> `Lérida`,
    326013 -> `Lugo`,
    6751251 -> `Madrid`,
    1695651 -> `Málaga`,
    1518486 -> `Murcia`,
    661537 -> `Navarra`,
    305223 -> `Ourense`,
    159123 -> `Palencia`,
    1128539 -> `Las Palmas`,
    944275 -> `Pontevedra`,
    319796 -> `La Rioja`,
    327338 -> `Salamanca`,
    1044405 -> `Santa Cruz de Tenerife`,
    153663 -> `Segovia`,
    1947852 -> `Sevilla`,
    88747 -> `Soria`,
    822309 -> `Tarragona`,
    134545 -> `Teruel`,
    709403 -> `Toledo`,
    2589312 -> `Valencia`,
    519361 -> `Valladolid`,
    168725 -> `Zamora`,
    967452 -> `Zaragoza`,
    83517 -> `Ceuta`,
    86261 -> `Melilla`
  )
}

case class Salary(value: Int) extends AnyVal
object Salary {
  // https://www.ine.es/jaxiT3/Datos.htm?t=10882
  def genFromGender(gender: Gender): Gen[Salary] = (gender match {
    case Gender.M => Gen.gaussian(26934.38, 15000)
    case Gender.F => Gen.gaussian(21682.02, 12000)
    case Gender.O => Gen.gaussian(19055.84, 10000)
  }).retryUntil(_ >= 14000).map(_.toInt).map(Salary(_))
}

case class PersonSalary(province: Province, gender: Gender, salary: Salary)

object PersonSalary {
  val gen: Gen[PersonSalary] = for {
    province <- Province.gen
    gender <- Gender.gen
    salary <- Salary.genFromGender(gender)
  } yield PersonSalary(province, gender, salary)
}
