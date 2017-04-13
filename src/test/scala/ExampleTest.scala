import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.scalatest.fixture

import scala.io.Source

/**
  * @see https://stepik.org/lesson/Overview-9293/step/9
  */
class ExampleTest extends fixture.FunSuite {

  class FixtureParam(outContent: ByteArrayOutputStream) {
    def setInput(input: String) = {
      System.setIn(new ByteArrayInputStream(input.getBytes))
    }

    def getOutput(): String = {
      outContent.toString.split("\r\n").mkString("\n")
    }
  }

  def withFixture(test: OneArgTest) = {
    val outContent = new ByteArrayOutputStream
    val errContent = new ByteArrayOutputStream
    val stdin = System.in
    val stdout = System.out
    val stderr = System.err
    System.setOut(new PrintStream(outContent))
    System.setErr(new PrintStream(errContent))
    try
      withFixture(test.toNoArgTest(new FixtureParam(outContent))) // "loan" the fixture to the test
    finally {
      // clean up the fixture
      System.setIn(stdin)
      System.setOut(stdout)
      System.setErr(stderr)
    }
  }

  test("sampleInput1 from file") { f =>
    val input = readTestInputFromResource("code00-example-sampleInput1.txt")
    f.setInput(input)
    Main.main(Array())
    val actual = f.getOutput()
    assert(actual == "8")
  }

  test("calculate 4 + 7 = 11 hard coded") { f =>
    f.setInput("4 7")
    Main.main(Array())
    val actual = f.getOutput()
    assert(actual === "11")
  }

  def readTestInputFromResource(inputFileName: String): String = {
    val source = Source.fromURL(getClass.getResource(s"/$inputFileName"))
    source.mkString
  }
}
