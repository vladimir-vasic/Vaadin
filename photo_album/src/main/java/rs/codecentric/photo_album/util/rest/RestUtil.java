package rs.codecentric.photo_album.util.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import rs.codecentric.photo_album.exception.BusinessErrorCode;
import rs.codecentric.photo_album.exception.BusinessException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * @author vladimir.vasic@codecentric.de
 * 
 */
public final class RestUtil {
	
	final static Logger log = LoggerFactory.getLogger(RestUtil.class);

	private static final String codecentricCompanyCode = "CODECENTRIC";
	
	public static String toJSon(Object obj) {
		if (obj == null) {
			return "";
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static Response getResponseJSon(String resource, Status status, Object result) {
		if (resource != null && resource.trim().length() > 0) {
			return Response.status(status).entity(addResourceNameToJson(resource, toJSon(result))).build();
		} else {
			return Response.status(status).entity(toJSon(result)).build();
		}
	}

	public static String addResourceNameToJson(String resource, String jsonText) {
		String jsonContent;
		if (jsonText == null) {
			jsonContent = "[]";
		} else {
			if (jsonText.trim().length() < 1) {
				jsonContent = "[]";
			} else {
				jsonContent = jsonText;
			}
		}
		return "{\"" + resource + "\":" + jsonContent + "}";
	}

	public static Response getResponseOkJSon(String resource, Object result) {
		return getResponseJSon(resource, Response.Status.OK, result);
	}

	public static List<String> jsonContainsFields(Map<Object, Object> jsonMap, String[] fields) {
		List<String> retVal = new ArrayList<String>();
		for (String field : fields) {
			if (!jsonMap.containsKey(field)) {
				retVal.add(field);
			}
		}
		return retVal;
	}

	/**
	 * This method returns the list of json members that are missing in json
	 * string from the <code>members</code> list.
	 * 
	 * @param jsonTxt
	 *            json string.
	 * @param members
	 *            members to check.
	 * @return missing members, or empty list if nonthing is missing.
	 * @throws JsonSyntaxException
	 *             is thrown if <code>jsonTxt</code> parsing fails.
	 */
	public static List<String> getMissingMembers(String jsonTxt, String[] members) throws JsonSyntaxException {
		List<String> retVal = new ArrayList<String>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonTxt).getAsJsonObject();
		for (String member : members) {
			if (jsonObject.get(member) == null) {
				retVal.add(member);
			}
		}
		return retVal;
	}

	/**
	 * This method returns the list of members that are missing in json object
	 * from the <code>members</code> list.
	 * 
	 * @param jsonObject
	 *            json object that needs to be checked.
	 * @param members
	 *            members to check.
	 * @return missing members.
	 */
	public static List<String> getMissingMembers(JsonObject jsonObject, String[] members) {
		List<String> retVal = new ArrayList<String>();
		for (String member : members) {
			if (!jsonObject.has(member)) {
				retVal.add(member);
			}
		}
		return retVal;
	}

//	/**
//	 * This method checks whether all <code>members</code> are present in
//	 * <code>jsonString</code>, and if all are present null is returned,
//	 * otherwise response with status {@link Response.Status#BAD_REQUEST} with
//	 * the string containing missing members, or invalid json string message.
//	 * 
//	 * @param jsonString
//	 *            json string to check.
//	 * @param members
//	 *            array of mandatory members.
//	 * @return <code>null</code> if all members are present, otherwise response
//	 *         with status {@link Response.Status#BAD_REQUEST} and list of
//	 *         missing members, or invalid json string message.
//	 */
//	public static Response checkJsonMembers(String jsonString, String[] members) {
//		try {
//			List<String> missing = getMissingMembers(jsonString, members);
//			if (missing.isEmpty()) {
//				return null;
//			}
//			String message = MessageFormatter.format("{}", missing).getMessage();
//			return Response.status(Response.Status.BAD_REQUEST).entity(getJsonDesktopError(BusinessErrorCode.API_ERROR_MISSING_MANDATORY_MEMBERS, message)).build();
//		} catch (JsonSyntaxException jse) {
//			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json string.").build();
//		}
//	}
	
	public static boolean companyExists(String companyCode) throws BusinessException {
        try {
        	log.info(MessageFormatter.format("COMPANY CODE: {} ", companyCode).getMessage());
			return companyCode.equals(codecentricCompanyCode);
        } catch (Exception exc) {
            log.error(MessageFormatter.format("Error loading company with company code: {}", companyCode).getMessage(), exc);
            throw new BusinessException(BusinessErrorCode.API_ERROR_LOADING_COMPANY, exc, companyCode);
        }
    }
	
	public static byte[] readImageByteStream(String imageName) {
		byte[] retVal = null;
		log.info("Reading in binary file named : {}", imageName);
		File file = new File("D:\\Documents\\Images\\".concat(imageName));
		log.info("File size: {}", file.length());
		retVal = new byte[(int) file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < retVal.length) {
					int bytesRemaining = retVal.length - totalBytesRead;
					// input.read() returns -1, 0, or more :
					int bytesRead = input.read(retVal, totalBytesRead, bytesRemaining);
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				log.info("Num bytes read: {}", totalBytesRead);
			} finally {
				log.info("Closing input stream.");
				input.close();
			}
		} catch (FileNotFoundException ex) {
			log.error("File not found.");
		} catch (IOException ex) {
			log.error(ex.getLocalizedMessage());
		}
		String tmp = null;
		tmp = Base64.encodeBytes(retVal);
		return tmp.getBytes();
	}
	
//	public static String pictureListToJson(List<Picture> pictureList) {  
//	    GsonBuilder gsonBuilder = new GsonBuilder();
//	    Gson gson = gsonBuilder.registerTypeAdapter(Picture.class, new PictureAdapter()).create();
//	    return gson.toJson(pictureList);
//	}
}
