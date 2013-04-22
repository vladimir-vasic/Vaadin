package rs.codecentric.photo_album.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.codecentric.photo_album.dao.IUserAdminDAO;
import rs.codecentric.photo_album.entity.User;
import rs.codecentric.photo_album.exception.BusinessException;
import rs.codecentric.photo_album.rest.api.IUserAPI;
import rs.codecentric.photo_album.util.rest.RestUtil;

/**
 * @author vladimir.vasic@codecentric.de
 *
 */
@Controller
@RequestMapping("/rest")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IUserAPI userAPI;
	
	@Autowired
	IUserAdminDAO userAdminDAO;
	
	@RequestMapping("/helloMVC")
	public String helloMVC(ModelMap model) {
		log.info("Spring 3 MVC Hello World (JSP)");
		model.addAttribute("message", "Spring 3 MVC Hello World");
	    return "hello";
	}
	
	@RequestMapping("/helloJSON/{name}")
	public String helloJSON(@PathVariable String name, ModelMap model) {
		log.info("Hello to {} (JSON)", name);
		model.addAttribute("message", name);
	    return name;
	}
	
//	@RequestMapping(headers = "Accept=application/json", value = "/user", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<String> loadUserByUsernameAndPassword(
//			@RequestHeader(value = "Authorization", required = false) String serverUsername,
//			@RequestBody String jsonText) {
//		try {
//			log.info("*** SERVER AUTHORIZATION: Basic Q09ERUNFTlRSSUM6");
//			log.info("*** serverUsername: ".concat(serverUsername));
////			if (RestUtil.companyExists(getCompanyCode())) {
//				log.info("Trying to load user...");
//				
//				JsonParser parser = new JsonParser();
//				List<String> missingMembers;
//				try {
//					missingMembers = RestUtil.getMissingMembers(jsonText, new String[] { "userName", "userPassword" });
//				} catch (JsonSyntaxException jse) {
//					log.error("Invalid json string for loading user.", jse);
//					return new ResponseEntity<String>("Invalid json string for loading user !!!", HttpStatus.BAD_REQUEST);
//				}
//				if (!missingMembers.isEmpty()) {
//					return new ResponseEntity<String>(MessageFormatter.arrayFormat("Invalid Json object. Missing object params are: {}", missingMembers.toArray()).getMessage(), HttpStatus.BAD_REQUEST);
//				}
//				JsonObject params;
//				try {
//					params = parser.parse(jsonText).getAsJsonObject();
//				} catch (JsonSyntaxException jse) {
//					log.error("Invalid json string for creating picture.", jse);
//					return new ResponseEntity<String>("Invalid json string for creating picture !!!", HttpStatus.BAD_REQUEST);
//				}
//				String userName = params.get("userName").getAsString();
//				String userPassword = params.get("userPassword").getAsString();
//				
//				User user = userAPI.loadUserByUsernameAndPassword(userName, userPassword);
//				
////				HttpHeaders responseHeaders = new HttpHeaders();
////				responseHeaders.set("MyResponseHeader", "MyValue");
//				return new ResponseEntity<String>(RestUtil.toJSon(user), HttpStatus.OK);
////			}
////			else {
////				return new ResponseEntity<String>("Authorization required!", HttpStatus.UNAUTHORIZED);
////			}
//		} catch (BusinessException exc) {
//			log.error("COULD NOT LOAD USER !!!", exc);
//			return new ResponseEntity<String>("Unexpected error while loading user !!!", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@RequestMapping("/getAllUsers")
	public String getAllUsers() {
		try {
			User user = userAPI.loadUserByUsernameAndPassword("johndoe", "johndoe");
			if (user != null) {
				return RestUtil.toJSon(user);
			} 
			List<User> userList = userAdminDAO.getAllUsers();
			if (!CollectionUtils.isEmpty(userList)) {
				return RestUtil.toJSon(userList);
			} else {
				return "";
			}
		} catch (BusinessException exc) {
			log.error("COULD NOT LOAD ALL USERS !!!", exc);
		    return "COULD NOT LOAD ALL USERS !!!";
		}
	}
	
}
