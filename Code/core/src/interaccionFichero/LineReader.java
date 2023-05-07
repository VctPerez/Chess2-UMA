package interaccionFichero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LineReader 
{	
	private String ent;
	private FileHandle file;
	private String[] lines;

	/**
	 * Crea el lector de lineas para el fichero con el nombre que especificamos
	 * @param fileName
	 * : Es el nombre del fichero que queremos abrir o 
	 * su directorio dentro del proyecto con el formato "ruta_hasta_fichero/fichero.txt"
	 */
	public LineReader(String fileName)
	{
		setFileName(fileName);
	}

	public FileHandle getFile() {
		return file;
	}
	public int getLinesNumber(){
		return lines.length;
	}
	/**
	 * Devuelve el nombre del fichero sobre el que estamos trabajando
	 * @return
	 */
	public String getFileName()
	{
		return ent;
	}
	
	/**
	 * Cambia el fichero en el que estemos trabajando por el que le damos por parametro
	 * @param fileName
	 * : Es el nombre del fichero al que queremos cambiar o directorio del fichero dentro del proyecto
	 * con el formato "ruta_hasta_fichero/fichero.txt"
	 */
	public void setFileName(String fileName)
	{
		ent = fileName;
		file = Gdx.files.internal(ent);
		lines = file.readString().split("\\r?\\n");
		//lines = file.readString().split(System.lineSeparator());
	}

	/**
	 * Se encarga de actualizar el Lector por si se ha hecho algun cambio
	 */
	public void update(){
		file = Gdx.files.internal(ent);
		lines = file.readString().split("\\r?\\n");
	}
	
	/**
	 * Lee una linea completa en concreto del fichero txt, la linea 1 es la primera
	 * linea del fichero completo, se considera nueva linea por cada uso de un "enter"
	 *
	 * @param lineNumber : Es el numero de la linea que vamos a leer del fichero
	 * @return Devuelve la linea leida en forma de String
	 */
	public String readLine(int lineNumber)
	{
		setFileName(ent);
		if(lineNumber <= lines.length) return lines[lineNumber - 1];
		else return null;

	}
	
	/**
	 * Lee un conjunto de lineas del mismo fichero
	 * @param firstLine
	 * : Es la primera linea que lee y la incluye en el texto
	 * @param lastLine
	 * : Es la ultima linea que lee y la incluye en el texto
	 * @return
	 * Devuelve un string con todas las lineas seguidas una tras otra, es decir, 
	 * el string contiene el texto sin saltos de linea 
	 */
	public String readSection(int firstLine, int lastLine) //Se incluyen la primera y la ultima linea
	{
		String text = "";
		for(int i=firstLine; i<=lastLine; ++i)
		{
			text += readLine(i) + "\n";
		}
		return text;
	}
	
	/**
	 * La funcion saca un entero de una linea, para que funcione en la linea 
	 * no puede haber algo que no sea el entero 
	 * @param numeroDeLinea
	 * : Numero de linea donde se encuentra nuestro dato
	 * @return
	 * Devuelve el entero leido del fichero
	 */
	public int leerINTLinea(int numeroDeLinea) //Para funcionar necesita que en la linea solo este el numero
	{
		String n = readLine(numeroDeLinea);
		return (int) Integer.parseInt(n);
	}
	
	/**
	 * La funcion saca un double de una linea, para que funcione en la linea
	 * no puede haber algo que no sea el double
	 * @param numeroDeLinea
	 * : Numero de linea donde se encuentra nuestro dato
	 * @return
	 * Devuelve el double leido del fichero
	 */
	public double leerDOUBLELinea(int numeroDeLinea) //Para funcionar necesita que en la linea solo este el numero
	{
		String n = readLine(numeroDeLinea);
		return (double) Double.parseDouble(n);
	}
	
	/**
	 * La funcion saca un float de una linea, para que funcione en la linea
	 * no puede haber algo que no sea el float
	 * @param numeroDeLinea
	 * : Numero de linea donde se encuentra nuestro dato
	 * @return
	 * Devuelve el float leido del fichero
	 */
	public float leerFLOATLinea(int numeroDeLinea) //Para funcionar necesita que en la linea solo este el numero
	{
		String n = readLine(numeroDeLinea);
		return (float) Float.parseFloat(n);
	}
	
	/**
	 * La funcion saca un booleano de una linea, para que funcione en la linea
	 * no puede haber algo que no sea el estado del booleano. Para que devuelva
	 * True el fichero debe leer necesariamente "true" sin importar mayusculas,
	 * si no lee "true" entonces se asume False.
	 * @param numeroDeLinea
	 * : Numero de linea donde se encuentra nuestro dato
	 * @return
	 * Devuelve el double leido del fichero
	 */
	public boolean leerBOOLEANLinea(int numeroDeLinea) //Para funcionar necesita que en la linea solo este el boolean ("true" para que devuelva true; "loQueSea" para que devuelva false)
	{
		String n = readLine(numeroDeLinea);
		return (boolean) Boolean.parseBoolean(n);
	}
}
