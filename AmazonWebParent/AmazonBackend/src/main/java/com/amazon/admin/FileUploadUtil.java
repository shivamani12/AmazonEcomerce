package com.amazon.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	// we are just trying to save the image file into directory which is coming from user as an request
	public static void saveFile(String uploadDir,String fileName, MultipartFile multiPartFile) throws IOException {
		
		// uploadDir -> directory name ( "user-photos");
		// fileName -> Alfred.png
		// multipartFIle -> for access metadata from file for process
		
		// path object that contains the directories name and string representation of the directory
		// we can specify the multiples directories name into path object
		Path uploadPath=Paths.get(uploadDir);  //define base directory it will contains the files in future right now its just define the path user-photo/id
		
		
		if(!Files.exists(uploadPath)) {  // if directory is not exist
			// it will create the directories which is contain by the path object
			Files.createDirectories(uploadPath);  // it will create real directory user-photos/id
		}
			
		// the method will retrieve the raw data of the uploaded file as an input stream
		// we get the input stream so it can process over the network
		try(InputStream inputStream = multiPartFile.getInputStream()){
			
			// resolve method provide by the path object combine the base-directory path and file name into a single path
			// it will be like =>   user-photos/id/Alfred.png ( it is just a name not real image
			Path filePath= uploadPath.resolve(fileName);     
//			now we need to add image data(raw data) into that path 
			Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING); // user-photo/id/Alfred.png(it will be real image)
		}catch(IOException e) {
			throw new IOException("Could not save file: " + fileName,e);
		}	
	}
	
	
	// now we are trying to clean directory in case user wants to change the photo so we need to delete old photos
	public static void cleanDir(String dir) {
		
		Path dirPath=Paths.get(dir);   // user-photos/id
		
		try {
			Files.list(dirPath).forEach(file->{
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
						System.out.println("Could not delete file" + file);
					}
				}
			});
		} catch (IOException e) {
			System.out.println(" Could not list directory: " + dirPath );
		}
	}
	
	// then we update the handler method to clean the user ID directory, before saving the file here.. we call FileUploadUtil.clean
}
