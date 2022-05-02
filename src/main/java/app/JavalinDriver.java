package app;

import io.javalin.Javalin;
public class JavalinDriver {
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(7070);
	}

}
