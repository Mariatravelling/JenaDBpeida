package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFileExample {

		 public static void main(String[] args) throws IOException {

     	 String content = "This is the text content;;Eye for an";
	     PrintWriter pw = new PrintWriter( new FileWriter( "/Users/wangzehui/Desktop/Semantic Web Tech/DBData/"+"test2"+".txt" ) );
	     pw.print( content );
	     pw.close( );
				  
	}
}
