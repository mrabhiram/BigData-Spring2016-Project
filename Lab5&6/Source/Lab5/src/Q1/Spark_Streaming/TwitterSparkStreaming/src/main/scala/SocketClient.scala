import java.io.{DataInputStream, PrintStream, IOException}
import java.net.{Socket, InetAddress}

/**
  * Created by Mayanka on 10-Sep-15.
  */
object SocketClient {

  def findIpAdd():String =
  {
    val localhost = InetAddress.getLocalHost
    val localIpAddress = localhost.getHostAddress

    return localIpAddress
  }
  def sendCommandToRobot(string: String)
  {
    // Simple server

    try {

      lazy val address: Array[Byte] = Array(192.toByte, 168.toByte, 1.toByte, 221.toByte)
      val ia = InetAddress.getByAddress(address)
      val socket = new Socket(ia, 1234)
      val out = new PrintStream(socket.getOutputStream)
      val in = new DataInputStream(socket.getInputStream())

      out.print(string)
      out.flush()

      out.close()
      //in.close()
      socket.close()
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
    }
  }


}
