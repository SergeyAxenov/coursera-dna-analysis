import java.util.Scanner

object Main {
  def main(args: Array[String]) {

    val s = new Scanner(System.in)

    val v1 = s.nextInt()
    val v2 = s.nextInt()

    val result = v1 + v2
    System.out.println(result)
  }
}