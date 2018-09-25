package com.easyLink.easyLink;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.derby.tools.sysinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EasyLinkController {

	@Autowired
	private LinkManager linkManager;

	@RequestMapping(value = "/", produces = "text/html;charset=UTF-8")
	public String sayHi(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "URL", required = false) String URL, HttpServletResponse response) {
		
		StringBuilder sb = new StringBuilder();
		
		if (id == null && URL == null) {
			
			sb.append("Hi, welcome in EasyLink\n");
			sb.append("<form action=''>\n");
			sb.append("Your link name: <input type='text' name='id' value=''><br/>\n");
			sb.append("Your URL:<input type='text' name='URL' value=''><br/>\n");
			sb.append("<input type='submit' value='Insert My URL'></form><br/>\n");
			
			// Following is also redundant because status is OK by default:
			response.setStatus(HttpServletResponse.SC_OK);
			return sb.toString();
			
		}else if (id.trim().isEmpty() || URL.trim().isEmpty()) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			sb.append("Wrong entry! Try again<br/>\n");
			sb.append("<a href='/'>Back</a>\n");

			return sb.toString();

		}else {

			linkManager = new LinkManager();

			linkManager.addLink(id, URL);
			response.setStatus(HttpServletResponse.SC_OK);
			sb.append("Done! You can find your link at: localhost:8080/"+id+" <br/>\n");
			sb.append("<a href='/links'>Back</a>\n");

			return sb.toString();
		}
		
	}

	@RequestMapping("/links")
	public List<Link> getAllLinks() {
		return linkManager.getAllLinks();
	}

//	@RequestMapping("/links/{id}")
//	public Link getLinkID(@PathVariable String id) {
//		return linkManager.getLink(id);
//	}

	@RequestMapping(method = RequestMethod.GET, value = "/links/{id}")
	public String getLink(@PathVariable String id, HttpServletResponse httpServletResponse) {
		
		if(linkManager.getLink(id).getURL()==null) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("Wrong entry! Try again<br/>\n");
			sb.append("<a href='/'>Back</a>\n");
			
			return sb.toString();
		}else {

			 httpServletResponse.setStatus(302); 
			httpServletResponse.setHeader("Location", "http://"+linkManager.getLink(id).getURL());
			
			return "redirect:" + linkManager.getLink(id).getURL();
		}
		
	}

	/*
	 * redirekts 2 varianti First:
	 * 
	 * @RequestMapping(value = "/redirect", method = RequestMethod.GET) public void
	 * method(HttpServletResponse httpServletResponse) {
	 * httpServletResponse.setHeader("Location", projectUrl);
	 * httpServletResponse.setStatus(302); }
	 * 
	 * Second:
	 * 
	 * @RequestMapping(value = "/redirect", method = RequestMethod.GET) public
	 * ModelAndView method() { return new ModelAndView("redirect:" + projectUrl);
	 * 
	 * }
	 */

	// @RequestMapping(method=RequestMethod.POST, value="/links")
	// public void addLink(@RequestBody Link link) {
	// linkManager.addLink(link);

	@RequestMapping(method = RequestMethod.POST, value = "/links")
//	@ResponseBody
	public String addLink(@RequestBody Link link) {
//	(@RequestParam(value = "id", required = false) String id,
//			@RequestParam(value = "URL", required = false) String URL, HttpServletResponse response) {
//		
//		if (id == null && URL == null) {
//
//			StringBuilder sb = new StringBuilder();
//			sb.append("<form action=''>\n");
//			sb.append("Name: <input type='text' name='name' value=''><br/>\n");
//			sb.append("Surname:<input type='text' name='surname' value=''><br/>\n");
//			sb.append("<input type='submit' value='Insert'></form><br/>\n");
//			sb.append("<a href='/'>Back</a>\n");
//
//			response.setStatus(HttpServletResponse.SC_OK);
//
//			return sb.toString();
//
//		} else if (id.trim().isEmpty() || URL.trim().isEmpty()) {
//
//			StringBuilder sb = new StringBuilder();
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//
//			sb.append("false<br/>\n");
//			sb.append("<a href='/'>Back</a>\n");
//
//			return sb.toString();
//
//		} else {

			linkManager = new LinkManager();
			StringBuilder sb = new StringBuilder();
			linkManager.addLink(link);
//			linkManager.addLink(id, URL);
//			response.setStatus(HttpServletResponse.SC_OK);
			sb.append("true<br/>\n");
			sb.append("<a href='/'>Back</a>\n");

			return sb.toString();
//		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/links/{id}")
	public void updateLink(@RequestBody Link link, @PathVariable String id) {
		linkManager.updateLink(id, link);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/links/{id}")
	public void deleteLink(@PathVariable String id) {
		linkManager.deleteLink(id);
	}

	
}