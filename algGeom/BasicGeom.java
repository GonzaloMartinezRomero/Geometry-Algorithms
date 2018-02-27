package algGeom;


import java.util.Random;

abstract public class BasicGeom {

  public static final double CERO = 0.00001; /** evita problemas con difentes valores de 0.0*/
  public static final double INFINITO = 99e99; /** para manejar infinito */
  public static int RANGO = 100;  /** define el rango de trabajo; 2*RANGO es el ancho total*/
  public static double PI = 3.14159265359;

  static public boolean iguales (double a, double b){
      return (Math.abs(a-b)<CERO);
  }

  static Random semillaAleatoria = new Random ();
  static int semilla = semillaAleatoria.nextInt();
  
  public static double min3 (double a, double b, double c){
     return (a<b ? (a<c?a:c ) : (b<c ? b : c));
  }

  public static double max3 (double a, double b, double c){
     return (a>b ? (a>c?a:c ) : (b>c ? b : c));
  }
  

  public static double determinante2x2(double a, double b, double c, double d) {
        return (a * c - b * d);
    }

    public static double determinante3x3(double a, double b, double c, 
            double d, double e, double f,
            double g, double h, double i) {
        return (a * e * i + g * b * f + c * d * i - c * e * g - i * d * b - a * h * f);
    }

}