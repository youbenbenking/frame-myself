package com.frame;

import java.util.ArrayList;
import java.util.List;

/**
 * 在servlet开发中，会在 web.xml 中通过 <servlet></servlet> 和 <servlet-mapping></servlet-mapping> 来进行指定哪个 URL 交给哪个 servlet 进行处理。
 * @author youben
 *
 */
public class ServletMappingConfig {

	public static List<ServletMapping>  servletMappingList = new ArrayList<ServletMapping>();
	
	static {
		servletMappingList.add(new ServletMapping("findGirl", "/girl", "myTomcat.FindGirlServlet"));
	}

}
