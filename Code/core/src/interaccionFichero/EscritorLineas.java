package interaccionFichero;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EscritorLineas {
	
	private String ent = "";
	LectorLineas l = new LectorLineas(ent);
	
	/**
	 * Constructor de la clase escritor
	 * @param fichero
	 * : Es el fichero sobre el que queremos escribir o su directorio dentro del proyecto "ruta_hasta_fichero/fichero.txt"
	 */
	public EscritorLineas(String fichero)
	{
		ent = fichero;
		l.setNombreFichero(fichero);
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
		l.setNombreFichero(fichero);
	}
	
	/**
	 * Es un metodo privado que copia en un txt temporal el nuevo resultado
	 * del fichero, es obligatorio por que no puedes leer y escribir sobre un mismo
	 * fichero en un solo metodo.
	 * @param numeroDeLinea
	 * : Es la linea en la que vamos a modificar
	 * @param texto
	 * : El texto que vamos a introducir en la linea indicada
	 */
	private void escribirLineaDummy(int numeroDeLinea,String texto)
	{
		String textBuffer="";

		BufferedWriter bw = null;
		try {
			File dummy = new File("temp_cpy.txt");
			if(!dummy.exists())
			{
				dummy.createNewFile();
			}
			FileWriter writer = new FileWriter(dummy);
			bw = new BufferedWriter(writer);
	    	
			String line	= "";
			int nLinea=1;
			
			while ((line = l.leerLinea(nLinea)) != null)
			{
			    if(nLinea==numeroDeLinea)
			    {
			        line=texto;
			    }
			    textBuffer = textBuffer + line + System.lineSeparator();
			    ++nLinea;
			}
			bw.write(textBuffer);
			//Aqui hemos generado la copia del fichero para ahora copiarla en el original
			
		}
		catch (IOException e) {
			System.out.println("Lectura bufferTexto rota");
		}
		finally
		{
			try
			{
				bw.close();
			}
			catch(IOException e)
			{
				System.out.println("Error cerrando editor");
			}
		}
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
		escribirLineaDummy(numeroDeLinea,texto);
		File fichero = new File(ent);
		BufferedWriter bwCOPY = null;
		try
		{
			LectorLineas cpy = new LectorLineas("temp_cpy.txt");
			FileWriter writerCOPY = new FileWriter(fichero);
			bwCOPY = new BufferedWriter(writerCOPY);
			
			String textBufferCOPY = "";
			String line	= "";
			int nLinea=1;
			while ((line = cpy.leerLinea(nLinea)) != null)
			{
				if(cpy.leerLinea(nLinea+1)==null)
				{
				    textBufferCOPY = textBufferCOPY + line;
				}
				else
				{
				    textBufferCOPY = textBufferCOPY + line + System.lineSeparator();
				}
			    ++nLinea;
			}
			bwCOPY.write(textBufferCOPY);
			bwCOPY.close();
			File eliminar = new File("temp_cpy.txt");
			if (eliminar.exists()) 
			{
				eliminar.delete();
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getLocalizedMessage());
		}
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
