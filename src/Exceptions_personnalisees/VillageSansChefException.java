package Exceptions_personnalisees;

public class VillageSansChefException extends NullPointerException{
	public VillageSansChefException() {
		
	}
	
	public VillageSansChefException(String message) {
		super(message);
	}
	
}
