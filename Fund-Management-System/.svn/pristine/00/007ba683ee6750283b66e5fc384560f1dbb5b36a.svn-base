package edu.fjut.fundsys.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import com.nimbusds.jose.JOSEException;

import net.minidev.json.JSONObject;
import edu.fjut.fundsys.jwt.Jwt;
import edu.fjut.fundsys.jwt.TokenState;

public class TokenFilter implements Filter {
	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("hello");
		HttpServletRequest request = (HttpServletRequest) argo;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setHeader("Access-Control-Allow-Origin", "*");
		if (request.getRequestURI().endsWith("/FMS/admin/adminLogin.action")
				|| request.getRequestURI().endsWith(
						"/FMS/client/clientLogin.action")
				|| request.getRequestURI().endsWith(
						"/FMS/client/checkEmail.action")
				|| request.getRequestURI().endsWith(
						"/FMS/client/registCilentUser.action")
				|| request.getRequestURI().endsWith(
						"/FMS/admin/adminLoadFund.action")
				|| request.getRequestURI().endsWith(
						"/FMS/admin/adminLoadFeature.action")
				|| request.getRequestURI().endsWith(
						"/FMS/fundtrans/loadRateRecord.action")
				|| request.getRequestURI().endsWith(
						"/FMS/admin/toGetoneFund.action")
				|| request.getRequestURI().endsWith(
						"/FMS/admin/adminGetFundType.action")
				|| request.getRequestURI().endsWith(
						"/FMS/fundtrans/yestodayEarn.action")
				|| request.getRequestURI().endsWith(
						"/FMS/admin/rateChange.action")
				|| request.getRequestURI().endsWith(
						"/FMS/admin/lookFundByHelper.action")
				|| request.getRequestURI().endsWith(".jpg")) {
			// 登陆接口不校验token，直接放行 chain.doFilter(request, response);
			System.out.println("是登录，放行");
			chain.doFilter(request, response);
			return;
		}
		// 其他API接口 校验token
		System.out.println("校验token");
		// 从请求头中获取token
		String token = (String) request.getParameter("token");
		// System.out.println(token);
		String tokenuid = Jwt.getUid(token);
		String requestuid = request.getParameter("adminId");
		if (requestuid == null) {
			requestuid = request.getParameter("clientId");
		}
		if (tokenuid.equals(requestuid)) {
			Map<String, Object> resultMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) resultMap
					.get("state"));
			switch (state) {
			case VALID:
				// 取出payload放入到request作用域中
				request.setAttribute("data", resultMap.get("data"));
				System.out.println("身份正确，放行");
				// 放行
				chain.doFilter(request, response);
				break;
			case EXPIRED:
			case INVALID:
				System.out.println("无效token");
				// token过期或无效，则输出错误信息返回给ajax
				JSONObject outputMSg = new JSONObject();
				outputMSg.put("resultcode", -2);
				outputMSg.put("message", "您的token不合法或者过期了，请重新登陆");
				output(outputMSg.toJSONString(), response);
				break;
			}
		} else {
			JSONObject outputMSg = new JSONObject();
			outputMSg.put("resultcode", -2);
			outputMSg.put("message", "您的token身份不正确，请重新登陆");
			output(outputMSg.toJSONString(), response);
		}
	}

	public void output(String jsonStr, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("token过滤器初始化");
	}

	@Override
	public void destroy() {

	}
}
