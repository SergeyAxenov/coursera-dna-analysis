import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.scalatest.fixture

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

  test("calculate 3 + 5 = 8") { f =>
    f.setInput("3 5")
    Main.main(Array())
    val actual = f.getOutput()
    assert(actual == "8")
  }

  test("calculate 4 + 7 = 11") { f =>
    f.setInput("3 5")
    Main.main(Array())
    val actual = f.getOutput()
    assert(actual === "8")
  }
}
