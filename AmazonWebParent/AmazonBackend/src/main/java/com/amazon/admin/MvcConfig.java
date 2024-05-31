package com.amazon.admin;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

// we are trying to retreive image so user can use the picture on the front end
	
	{
		System.out.println(" IB block of MvcConfig*************************************");
	}
	static{
		System.out.println("static block of MvcConfig*************************************");
	}
	
	
		@Override
		public void addResourceHandlers (ResourceHandlerRegistry registry) {
				System.out.println("Called addResourceHandlers*************************************");
				String dirName="user-photos";
				Path userPhotosDir=Paths.get(dirName);
				System.out.println(userPhotosDir);
				// absolute path
				String userPhotosPath=userPhotosDir.toFile().getAbsolutePath(); 
/*				// hamein jis directory ka absolute path chahiye wo hum .toFile ke through uska content and getAbsolutePath ke through path le sakte hai
// you will get root to target path => D: \AmazonEcom\AmazonProject\AmazonWebParent\AmazonBackend\ user-photos ;
 */
				System.out.println(userPhotosPath);
				
				registry.addResourceHandler("/" + dirName + "/**")   // MVC will handle if the request for /user-photos/+123/profile.jpg".
				.addResourceLocations("file:/" + userPhotosPath + "/"); // MVC will retrieve the requested resources (Content) from the absolute path and genrate the response to the client
			
				// MVC need the two thing for handle the request 
				// first it we have to  map the request pattern 
				// second MVC need the location so it can find the resource 
				
//				The ResourceHttpRequestHandler locates the requested resource file in the specified directory on the server's file system.
//				It reads the contents of the file and sends them back to the client as the response.
		}
		@Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/AmazonAdmin/api/categories")
	                .allowedOrigins("http://127.0.0.1:5500");
	    }
}
