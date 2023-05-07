package interaccionFichero;

import com.badlogic.gdx.Gdx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LineWriter {
	
	private String ent = "";
	LineReader l;
	
	/**
	 * Constructor de la clase escritor
	 * @param fichero
	 * : Es el fichero sobre el que queremos escribir o su directorio dentro del proyecto "ruta_hasta_fichero/fichero.txt"
	 */
	public LineWriter(String fichero)
	{
		ent = fichero;
		l = new LineReader(fichero);
	}
	
	/**
	 * Getter de la clase escritor
	 * @return
	 * Devuelve el nombre del fichero que estamos modificando
	 */
	public String getNombreFichero()
	{
		return ent;
	}
	
	/**
	 * Setter de la clase escritor, cambia el fichero sobre el que estamos trabajando
	 * @param fichero
	 * : Es el nombre del nombre del nuevo fichero o su directorio incluyendo el archivo "ruta_hasta_fichero/fichero.txt"(se asume que va a ser correcto)
	 */
	public void setNombreFichero(String fichero)
	{
		ent = fichero;
		l.setFileName(fichero);
	}

	
	/**
	 * Metodo principal que nos permite escribir en la linea indicada el texto
	 * que pasemos por parametro.
	 * @param numeroDeLinea
	 * : Es la linea en la que vamos a modificar
	 * @param texto
	 * : El texto que vamos a introducir en la linea indicada
	 */
	public void escribirLinea(int numeroDeLinea,String texto)
	{
		String modifiedText = l.readSection(1,numeroDeLinea - 1) + texto;
		if(!l.readSection(numeroDeLinea + 1 , l.getLinesNumber()).equals("")) {
			modifiedText += System.lineSeparator() + l.readSection(numeroDeLinea + 1,l.getLinesNumber());
		}
		Gdx.files.local(ent).writeString(modifiedText, false);
		l.update();

	}
	
	/**
	 * Escribe un entero en la linea indicada.
	 * @param numeroDeLinea
	 * : Es la linea en la que vamos a modificar
	 * @param num
	 * : Numero que vamos a escribir, en este caso es un entero
	 */
	public void escribirLineaINT(int numeroDeLinea,int num)
	{
		String texto = String.valueOf(num);
		escribirLinea(numeroDeLinea,texto);
	}
	
	/**
	 * Escribe un double en la linea indicada.
	 * @param numeroDeLinea
	 * : Es la linea en la que vamos a modificar
	 * @param num
	 * : Numero que vamos a escribir, en este caso es un double
	 */
	public void escribirLineaDOUBLE(int numeroDeLinea,double num)
	{
		String texto = String.valueOf(num);
		escribirLinea(numeroDeLinea,texto);
	}
	
	/**
	 * Escribe un boolean en la linea indicada.
	 * @param numeroDeLinea
	 * : Es la linea en la que vamos a modificar
	 * @param bool
	 * : Es el valor que vamos a darle al bool, en este caso al escribir
	 * va a imprimir en el fichero directamente "true" o "false"
	 */
	public void escribirLineaBOOLEAN(int numeroDeLinea,boolean bool)
	{
		String texto = String.valueOf(bool);
		escribirLinea(numeroDeLinea,texto);
	}
}
