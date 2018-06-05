package animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AnimalFactory {
	
	public static Animal newInstance(String animalKind, String name) {
		Class<Animal> clazz = null;
		try {
			clazz = (Class<Animal>)Class.forName("plugins."+animalKind);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Constructor<?> ctr = null;
		try {
			ctr = clazz.getConstructor(String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		Animal animal = null;
		try {
			animal = (Animal)ctr.newInstance(name);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return animal;
	}
}
