/**
 *  I N F 2 7 1 0 - 5 0
 *
 * 	Application de convertion de document au format mini-markdown en document au format html. 
 *	Seul un sous-ensemble du format markdown sera à réaliser
 *
 *	@author : Jude Seide (SEIJ04019006)
 *	@author : Guillaume Senou (SENT10019203)
 *
 *	senou.towede_guillaume@courrier.uqam.ca
 *	seide.jude_odilon_kenley@courrier.uqam.ca	
 *
 */

import java.io.IOException;

public class Md2html{
	
	//constante du saut de ligne
	public static final char SAUT_DE_LIGNE = '\n';
   
	//constante du retour-chariot 
	public static final char RETOUR_CHARIOT = '\r';
	
	//constante qui exprime la fin de lecture ou un titre
	public static final char DIESE = '#';
	
	//constante qui marque chaque element d'une liste
	public static final char SCORE = '-';
	
	//variable globale permettant d'arreter le programme au bon moment
	public static boolean fin = true;
	
	/**
	 * Cette methode cree un titre en html ou met fin au programme selon
	 * le type DIESE qui l'a appele
	 * 
	 * @param carac : le caractere lu qui a proveque l'appel de la methode
	 * @return html : un titre  en html avec les balises requis
	 * */
	public static String titre(char carac){
		boolean lecture = true; // condition pour continuer a lire
		char car = 0; 			// caractere tempon sur le precedent lu
		int h = 0; 				// le nombre de DIESE lu pour une balise <h +int h>
		int temp = 0;			// un tempon sur le nombre de DIESE lu
		String lu = "";			// recupere la concatenation des caracteres a conserver
		String html = "";		// recupere la concatenation de la transformation en html
		
		while(lecture){
        	try { 
        		//recupere les caracteres tout en icrementant les occurences corrects de DIESE
        		if(carac == DIESE){
        			car = carac;
        			++temp;
        		}else if(carac != SAUT_DE_LIGNE){
                    car = 0;
                    h = temp;
                    lu += caracSpec(carac);
        		}else if(carac == SAUT_DE_LIGNE && lu != ""){
        			temp = 0;
        			html +="<h"+h+">"+lu+"</h"+h+">"+ carac;
        			car = carac;
        			lu = "";
        		}
        		//condition d'arret de lecture 
        		if(carac == SAUT_DE_LIGNE && car == DIESE){
  				  	lecture = false;
  				  	fin = false;
  				}else if (car == SAUT_DE_LIGNE && carac != DIESE){
  					lecture = false;
  				}
        		//evite une lecture non-souhaitee
        		if(lecture){
        			carac = (char) System.in.read();
        		}        		
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }		
		return html;
	}
	
	/**
	 * Cette methode cree un paragraphe en html
	 * 
	 *  @param carac : le caractere qui a provoque l'appel de la methode
	 *  @return html : la transformation du paragraphe en html avec les balises requis
	 * */
	public static String paragraphe(char carac){        
        boolean lecture = true;  // condition pour continuer a lire
        String lu = "";			 // recupere et concatene les caracteres a conserver
        char car = carac;		 // caractere tempon sur le precedent lu
        String html = "";		 // recupere la concatenation de la transformation en html
        String eol = "";		 // recupere les sauts de ligne entre paragraphe pour les reajustements requis
        
        //recupere un caractere potentiellement a inclure
        if(carac != SAUT_DE_LIGNE){
        	html += caracSpec(carac);
        }else{
        	eol += carac;
        }
        
        while(lecture){
                try{ 
                		carac = (char) System.in.read();
                		//condition d'arret de lecture
                		if(carac == DIESE){
                        	lecture = false;
                        }else{
                        	//recupere des caracteres et de la structure correct du paragraphe
                        	if(carac != SAUT_DE_LIGNE){                        		
                        		lu += caracSpec(carac);
                        	}else if(carac == SAUT_DE_LIGNE && car != SAUT_DE_LIGNE){
                        		html += lu + carac;
                        		lu = "";
                        	}else if(carac == SAUT_DE_LIGNE && car == SAUT_DE_LIGNE){
                        		html = "<p>\n" + html + "</p>" + car + carac;
                        		lecture = false;
                        	}
                        	car = carac;
                        }                    	                    	
                }catch(IOException e){
                        e.printStackTrace();
                }                                
        }
        //condition d'arret du paragraphe lorsqu'on lit un titre ou la fin du flot
        if(carac == DIESE){
        	//recupere le paragraphe lu et lui met les balises et SAUT_DE_LIGNE appropries
        	if(car == SAUT_DE_LIGNE){
        		html = eol + "<p>\n" + html + "</p>" + car;
        	}
        	//appel de la methode titre pour les traitemens requis selon le cas
        	//fin du flot ou creation de titre
        	html += titre(carac);
        }
        return html;
	}
	
	/**
	 * Cette methode cree une liste en html
	 * 
	 * @param carac : le caractere qui a provoque l'appel de la methode
	 * @return html : une liste en html avec les balises requis
	 * */
	public static String liste(char carac){
        boolean lecture = true;			// condition pour continuer a lire
        char car = carac;       		// caractere tempon sur le precedent lu
        String lu = "";					// recupere la concatenation des caracteres a conserver
        String html = "";				// recupere la concatenation de la transformation en html
        
        while(lecture){
                try{
                	carac = (char) System.in.read();
                	//condition de fin de lecture
                	if(carac == DIESE){
                    	lecture = false;
                    }else{
                    	//recupere des caracteres et de la structure correct de la liste
                    	if(carac != SAUT_DE_LIGNE){                    		
                    		if(carac == SCORE && car != SAUT_DE_LIGNE){
                    			lu += carac;
                    		}else if(carac == SCORE && car == SAUT_DE_LIGNE && lu != ""){
                    			html += "<li>"+ lu + "</li>" + car;
                    			lu = "";
                    		}else if(carac != SCORE && car == SAUT_DE_LIGNE){
                    			lu += car ;
                    		}
                    		if(carac != SCORE){
                    			lu += caracSpec(carac) ;
                    		}
                    	}
                    	//condition d'arret de lecture et recuperation du 
                    	//bloc de liste avec les balises ul requis                    	
                    	if(carac == SAUT_DE_LIGNE && car == SAUT_DE_LIGNE){
                    		html = "<ul>\n" + html + lu + car + "</ul>" ;
                    		lecture = false;
                    	} 
                    }  
                	car = carac;
                }catch(IOException e){
                        e.printStackTrace();
                }
        }
        //condition d'arret de lecture
        if(carac == DIESE){
        	//recuperation d'une liste eventuel lu avec les balises requis selon le cas
        	if(lu != ""){
        		html = "<ul>\n" + html + "<li>"+ lu + "</li>" + "\n</ul>\n";
        	}
        	html += titre(carac);
        }
		return html;
	}
	
	/**
	 * Cette methode evalue lors de son appel si carac est un caractere special
	 * soient <,>,&
	 * si oui elle retourne une chaine de son equivalent
	 * si non elle retourne une chaine contenant seulement carac
	 * 
	 * @param carac : le caractere a evalue
	 * @return car : la chaine selon l'evaluation
	 * */
	public static String caracSpec(char carac){
		String car = ""; // Recupere la concatenation de carac ou de son equivalent html selon le cas
		car += carac;
		if(carac == '<'){
			car = "&lt;";
		}else if(carac == '>'){
			car = "&gt;";
		}else if(carac == '&'){
			car = "&amp;";
		}
		return car;
	}
        
		   
	/**
	 * Lit le flot d'entree caractere par caractere et effectue les appels
	 * correspondant puis affiche le resultat avant de terminer l'execution
	 * @param args
     */
	public static void main(String[] args) {
		char carac = 0;			// caractere pour recuperer le caractere lu
		
		String html = "";		// recupere la concatenation des transformations effectues
        while(fin){
        	
        	try {
        		//recupere le SAUT_DE_LIGNE
        		do{
        			carac = (char) System.in.read();
        			if(carac == SAUT_DE_LIGNE)
        			html+="\n";
        		}while(carac == SAUT_DE_LIGNE);        		
        		
        		//appelle les methodes appropries selon le cas
        		if(carac == DIESE){
        			html += titre(carac);
        		}else if(carac != DIESE && carac != SCORE){
        			html += paragraphe(carac);
        		}else if (carac == SCORE) {
                    html += liste(carac);
        		}    		
        		        		
            } catch (IOException e) {
                 e.printStackTrace();
            }
                       
        }
        //affichage du resultat en html
        System.out.print(html);
	}

}
