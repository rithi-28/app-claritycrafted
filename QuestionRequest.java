import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionRequest extends HttpServlet{
	 public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		 resp.setContentType("text/html");
		 String question = req.getParameter("quest");
		 JSONResponse jresp = new JSONResponse();
		 String qresult = jresp.jsonResponseHandler("question", question);
//		 resp.getWriter().print(qresult);
		 if (qresult.equalsIgnoreCase("failed")){
			 //redirect
		 }
		 else {
			DBHandler demo = new DBHandler("jdbc:postgresql://localhost:5432/vectordocsrepository","rithika","password@1");
			demo.getConnection();
			CosineCalc calc = new CosineCalc();
//			resp.getWriter().print(demo.getEntry());
			List<String> lst = calc.processor(demo.getEntry(), qresult);
			resp.getWriter().println("<h1>"+lst.get(0)+"</h1><p>"+lst.get(1)+"</p>");
//			resp.getWriter().println("கட்டுரை");
			 fdcd
		 }
	 }
}
