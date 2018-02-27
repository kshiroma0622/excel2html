package kshiroma0622.excel2html.web.servlet

import java.io.{ByteArrayOutputStream, IOException, InputStream}
import javax.servlet.ServletException
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64
import com.google.common.io.Files
import kshiroma0622.excel2html.Excel2HtmlUtil


class Excel2HtmlServlet extends HttpServlet {


  @throws[IOException]
  @throws[ServletException]
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = post(req, resp)


  protected def post(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val m = req.getParameter("file-base64");
    val data = Base64.decodeBase64(m.toString.split(",",2)(1))
    resp.setCharacterEncoding("UTF-8")
    resp.setContentType("text/html")
    Excel2HtmlUtil.toHtml(data,resp.getOutputStream)
  }


  def toByteArray(stream: InputStream): Array[Byte] = {
    val baos = new ByteArrayOutputStream
    val buffer = new Array[Byte](4096)
    var read = 0
    while ( {
      read != -1
    }) {
      read = stream.read(buffer)
      if (read > 0) baos.write(buffer, 0, read)
    }
    baos.toByteArray
  }

  def main(args: Array[String]): Unit = {

  }


}
